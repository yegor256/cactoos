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

import java.io.IOException;
import org.cactoos.Func;

/**
 * Function is application of one function to the result of another.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @param <X> Type of input before func
 * @param <Y> Type of output before func and input after func
 * @param <Z> Type of output after func
 * @since 0.1
 */
public final class ComposedFunc<X, Y, Z> implements Func<X, Z> {

    /**
     * Before func.
     */
    private final Func<X, Y> before;

    /**
     * After func.
     */
    private final Func<Y, Z> after;

    /**
     * Ctor.
     *
     * @param before Func
     * @param after Func
     */
    public ComposedFunc(final Func<X, Y> before, final Func<Y, Z> after) {
        this.before = before;
        this.after = after;
    }

    @Override
    public Z apply(final X input) throws IOException {
        return this.after.apply(this.before.apply(input));
    }
}
