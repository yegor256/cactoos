/**
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
package org.cactoos.func;

import org.cactoos.BiFunc;
import org.cactoos.Func;

/**
 * Partial application.
 * It refers to the process of fixing a number of arguments to a function,
 * producing another function of smaller arity.
 *
 * BiFunc&lt;Long, Long, Long&gt; mult = (first, second) -> first * second;
 * Func&lt;Long, Long&gt; mult6 = new PartApplyFunc<>(mult, 6L);
 * mult6.apply(2L);
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @param <X> Type of argument
 * @param <Y> Type of input
 * @param <Z> Type of output
 * @since 0.29
 */
public final class PartApplyFunc<X, Y, Z> implements Func<Y, Z> {

    /**
     * Origin function that accepts two arguments.
     */
    private final BiFunc<X, Y, Z> origin;

    /**
     * Argument.
     */
    private final X argument;

    /**
     * Ctor.
     * @param func Origin function
     * @param arg Argument
     */
    public PartApplyFunc(final BiFunc<X, Y, Z> func, final X arg) {
        this.origin = func;
        this.argument = arg;
    }

    @Override
    public Z apply(final Y input) throws Exception {
        return this.origin.apply(this.argument, input);
    }
}
