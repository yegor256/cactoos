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
package org.cactoos.iterable;

import java.util.Iterator;
import org.cactoos.Func;

/**
 * Mapped iterable.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of source item
 * @param <Y> Type of target item
 * @since 0.1
 */
public final class Mapped<X, Y> implements Iterable<Y> {

    /**
     * Iterable.
     */
    private final Iterable<X> iterable;

    /**
     * Function.
     */
    private final Func<X, Y> func;

    /**
     * Ctor.
     * @param src Source iterable
     * @param fnc Func
     */
    public Mapped(final Iterable<X> src, final Func<X, Y> fnc) {
        this.iterable = src;
        this.func = fnc;
    }

    @Override
    public Iterator<Y> iterator() {
        return new org.cactoos.iterator.Mapped<>(
            this.iterable.iterator(), this.func
        );
    }

}
