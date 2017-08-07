/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.RetryFunc;

/**
 * Func that will try a few times before throwing an exception.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <T> Type of output
 * @since 0.9
 */
public final class RetryScalar<T> implements Scalar<T> {

    /**
     * Original scalar.
     */
    private final Scalar<T> origin;

    /**
     * Exit condition.
     */
    private final Func<Integer, Boolean> func;

    /**
     * Ctor.
     * @param scalar Scalar original
     */
    public RetryScalar(final Scalar<T> scalar) {
        // @checkstyle MagicNumberCheck (1 line)
        this(scalar, 3);
    }

    /**
     * Ctor.
     * @param scalar Scalar original
     * @param attempts Maximum number of attempts
     */
    public RetryScalar(final Scalar<T> scalar, final int attempts) {
        this(scalar, attempt -> attempt >= attempts);
    }

    /**
     * Ctor.
     * @param scalar Func original
     * @param exit Exit condition, returns TRUE if there is no reason to try
     */
    public RetryScalar(final Scalar<T> scalar,
        final Func<Integer, Boolean> exit) {
        this.origin = scalar;
        this.func = exit;
    }

    @Override
    public T value() throws Exception {
        return new RetryFunc<>(
            (Func<Boolean, T>) input -> this.origin.value(),
            this.func
        ).apply(true);
    }
}
