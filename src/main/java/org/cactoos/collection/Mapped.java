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
import org.cactoos.text.TextOf;

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
@SuppressWarnings
    (
        {
            "PMD.TooManyMethods", "PMD.CyclomaticComplexity",
            "PMD.StdCyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity"
        }
    )
public final class Mapped<X, Y> extends CollectionEnvelope<Y> {

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
     * @checkstyle AnonInnerLengthCheck (140 lines)
     */
    public Mapped(final Func<X, Y> fnc, final Collection<X> src) {
        super(() -> new Collection<Y>() {
            @Override
            public int size() {
                return src.size();
            }
            @Override
            public boolean isEmpty() {
                return src.isEmpty();
            }
            @Override
            public boolean contains(final Object item) {
                return new CollectionOf<>(
                    new org.cactoos.iterator.Mapped<>(
                        fnc, src.iterator()
                    )
                ).contains(item);
            }
            @Override
            public Iterator<Y> iterator() {
                return new org.cactoos.iterator.Mapped<>(
                    fnc, src.iterator()
                );
            }
            @Override
            public Object[] toArray() {
                return new CollectionOf<>(
                    new org.cactoos.iterator.Mapped<>(
                        fnc, src.iterator()
                    )
                ).toArray();
            }
            @Override
            @SuppressWarnings("PMD.UseVarargs")
            public <T> T[] toArray(final T[] array) {
                return new CollectionOf<>(
                    new org.cactoos.iterator.Mapped<>(
                        fnc, src.iterator()
                    )
                ).toArray(array);
            }

            @Override
            public boolean add(final Y item) {
                throw new UnsupportedOperationException(
                    "Collection is read-only, can't #add()"
                );
            }
            @Override
            public boolean remove(final Object item) {
                throw new UnsupportedOperationException(
                    "Collection is read-only, can't #remove()"
                );
            }
            @Override
            public boolean containsAll(final Collection<?> items) {
                return new CollectionOf<>(
                    new org.cactoos.iterator.Mapped<>(
                        fnc, src.iterator()
                    )
                ).containsAll(items);
            }
            @Override
            public boolean addAll(final Collection<? extends Y> items) {
                throw new UnsupportedOperationException(
                    "Collection is read-only, can't #addAll()"
                );
            }
            @Override
            public boolean removeAll(final Collection<?> items) {
                throw new UnsupportedOperationException(
                    "Collection is read-only, can't #removeAll()"
                );
            }
            @Override
            public boolean retainAll(final Collection<?> items) {
                throw new UnsupportedOperationException(
                    "Collection is read-only, can't #retainAll()"
                );
            }
            @Override
            public void clear() {
                throw new UnsupportedOperationException(
                    "Collection is read-only, can't #clear()"
                );
            }
        });
    }

    @Override
    public String toString() {
        return new TextOf(this).toString();
    }
}
