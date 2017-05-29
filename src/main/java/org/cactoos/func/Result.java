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
package org.cactoos.func;

import java.util.ArrayList;
import java.util.List;
import org.cactoos.Func;
import org.cactoos.Scalar;

/**
 * Result of function.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @param <X> Type of argument
 * @param <Y> Type of result
 * @since 0.1
 */
public final class Result<X, Y> implements Scalar<Y> {
    /**
     * The function.
     */
    private final Func<X, Y> func;
    /**
     * The argument.
     */
    private final X arg;
    /**
     * The result.
     */
    private final List<Y> value;

    /**
     * Ctor.
     * @param func The function
     * @param arg The argument
     */
    public Result(final Func<X, Y> func, final X arg) {
        this.func = func;
        this.arg = arg;
        this.value = new ArrayList<>(1);
    }

    @Override
    public Y asValue() throws Exception {
        if (this.value.isEmpty()) {
            this.value.add(
                this.func.apply(this.arg)
            );
        }
        return this.value.get(0);
    }
}
