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

import java.util.Collections;
import java.util.Iterator;
import org.cactoos.Func;

/**
 * Composed function.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @param <X> Type of input.
 * @param <Y> Intermediate type.
 * @param <Z> Type of output.
 * @since 0.7
 */
public final class ComposedFunc<X, Y, Z> implements Func<X, Z> {

    /**
     * Before function.
     */
    private final Func<X, Y> before;

    /**
     * Functions.
     */
    private final Iterable<Func<Y, Y>> funcs;

    /**
     * After function.
     */
    private final Func<Y, Z> after;

    /**
     * Ctor.
     * @param before Before function
     * @param funcs Functions
     * @param after After function
     */
    public ComposedFunc(
        final Func<X, Y> before,
        final Iterable<Func<Y, Y>> funcs,
        final Func<Y, Z> after
    ) {
        this.before = before;
        this.funcs = funcs;
        this.after = after;
    }

    /**
     * Ctor.
     * @param before Before function
     * @param after After function
     */
    public ComposedFunc(final Func<X, Y> before, final Func<Y, Z> after) {
        this(before, Collections.emptyList(), after);
    }

    @Override
    public Z apply(final X input) throws Exception {
        final Iterator<Func<Y, Y>> iterator = this.funcs.iterator();
        return this.after.apply(
            this.funcsApply(iterator, this.before.apply(input))
        );
    }

    /**
     * Composed functions in this.func.
     *
     * @param iterator Functions
     * @param input The input
     * @return Functions apply
     * @throws Exception If fails
     */
    private Y funcsApply(
        final Iterator<Func<Y, Y>> iterator,
        final Y input
    ) throws Exception {
        final Y result;
        if (iterator.hasNext()) {
            result = this.funcsApply(iterator, iterator.next().apply(input));
        } else {
            result = input;
        }
        return result;
    }
}
