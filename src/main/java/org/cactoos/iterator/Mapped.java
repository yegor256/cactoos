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
package org.cactoos.iterator;

import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.func.UncheckedFunc;

/**
 * Mapped iterator.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of source item
 * @param <Y> Type of target item
 * @since 0.1
 */
public final class Mapped<X, Y> implements Iterator<Y> {

    /**
     * Iterator.
     */
    private final Iterator<X> origin;

    /**
     * Function.
     */
    private final Func<X, Y> fnc;

    /**
     * Ctor.
     * @param func Func
     * @param iterator Source iterator
     */
    public Mapped(final Func<X, Y> func, final Iterator<X> iterator) {
        this.origin = iterator;
        this.fnc = func;
    }

    @Override
    public boolean hasNext() {
        return this.origin.hasNext();
    }

    @Override
    public Y next() {
        return new UncheckedFunc<>(this.fnc).apply(this.origin.next());
    }

    @Override
    public void remove() {
        this.origin.remove();
    }

}
