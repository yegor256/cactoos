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
 * Iterator over the output of a function.
 *
 * @author Tim Hinkes (timmeey@timmeey.de)
 * @version $Id$
 * @param <X> The input type for the Function
 * @param <Y> The output Type of the Function
 * @since 0.7
 */
public final class FuncAsIterator<X, Y> implements Iterator<Y> {

    /**
     * The input providing iterator.
     */
    private final Iterator<X> input;

    /**
     * The function.
     */
    private final UncheckedFunc<X, Y> func;

    /**
     * Ctor.
     * @param input The input for the Function.
     * @param func The function.
     */
    public FuncAsIterator(final Iterator<X> input,
        final UncheckedFunc<X, Y> func) {
        this.input = input;
        this.func = func;
    }

    @Override
    public boolean hasNext() {
        return this.input.hasNext();
    }

    @Override
    public Y next() {
        return this.func.apply(this.input.next());
    }
}
