/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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

import org.cactoos.Scalar;

/**
 * Logical negative.
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use {@link UncheckedScalar} or {@link IoCheckedScalar} decorators.</p>
 *
 * @since 0.7
 */
public final class Not implements Scalar<Boolean> {

    /**
     * The origin scalar.
     */
    private final Scalar<Boolean> origin;

    /**
     * Ctor.
     * @param scalar The scalar
     */
    public Not(final Scalar<Boolean> scalar) {
        this.origin = scalar;
    }

    @Override
    public Boolean value() throws Exception {
        return !this.origin.value();
    }
}
