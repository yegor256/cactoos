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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.iterable.IterableOf;

/**
 * Reversed collection.
 *
 * <p>Pay attention that sorting will happen on each operation
 * with the collection. Every time you touch it, it will fetch the
 * entire collection from the encapsulated object and reverse it. If you
 * want to avoid that "side-effect", decorate it with
 * {@link StickyCollection}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @param <X> Type of source item
 * @since 1.16
 */
public final class Reversed<X> extends CollectionEnvelope<X> {

    /**
     * Ctor.
     * @param src Source collection
     * @since 0.23
     */
    @SafeVarargs
    public Reversed(final X... src) {
        this(new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source collection
     */
    public Reversed(final Iterable<X> src) {
        this(new CollectionOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source collection
     */
    public Reversed(final Collection<X> src) {
        super(() -> {
            final List<X> items = new LinkedList<>();
            items.addAll(src);
            Collections.reverse(items);
            return items;
        });
    }

}
