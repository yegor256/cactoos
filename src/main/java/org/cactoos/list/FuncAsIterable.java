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
package org.cactoos.list;

import java.util.Iterator;
import org.cactoos.func.UncheckedFunc;

/**
 * Iterable over the output of a function.
 *
 * @author Tim Hinkes (timmeey@timmeey.de)
 * @version $Id$
 * @param <X> The input type for the Function
 * @param <Y> The output Type of the Function
 * @since 0.7
 */
public final class FuncAsIterable<X, Y> implements Iterable<Y> {

    /**
     * The Function.
     */
    private final UncheckedFunc<X, Y> func;
    /**
     * The Iterable providing the Input for the Function.
     */
    private final Iterable<X> input;

    /**
     * Ctor.
     * @param func The Function.
     * @param input THe Input for the Function.
     */
    public FuncAsIterable(final UncheckedFunc<X, Y> func,
        final Iterable<X> input) {
        this.func = func;
        this.input = input;
    }

    @Override
    public Iterator<Y> iterator() {
        return new FuncAsIterator<>(this.input.iterator(), this.func);
    }
}
