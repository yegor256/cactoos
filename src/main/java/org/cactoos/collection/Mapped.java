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
package org.cactoos.collection;

import java.util.Collection;
import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;

/**
 * Mapped collection.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of source item
 * @param <Y> Type of target item
 * @since 0.14
 */
public final class Mapped<X, Y> extends AbstractCollectionEnvelope<Y> {

    /**
     * Ctor.
     * @param src Source collection
     * @param fnc Func
     * @since 0.23
     */
    @SafeVarargs
    public Mapped(final Func<X, Y> fnc, final X... src) {
        this(fnc, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source collection
     * @param fnc Func
     * @since 0.23
     */
    public Mapped(final Func<X, Y> fnc, final Iterator<X> src) {
        this(fnc, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source collection
     * @param fnc Func
     */
    public Mapped(final Func<X, Y> fnc, final Iterable<X> src) {
        this(fnc, new CollectionOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source collection
     * @param fnc Func
     */
    public Mapped(final Func<X, Y> fnc, final Collection<X> src) {
        super(() -> new CollectionOf<Y>(
            new org.cactoos.iterable.Mapped<X, Y>(fnc, src)
        ));
    }

}
