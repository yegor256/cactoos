/*
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
package org.cactoos.list;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Shuffled list.
 *
 * <p>Pay attention that shuffling will happen on each operation
 * with the collection. Every time you touch it, it will fetch the
 * entire list from the encapsulated object and sort it. If you
 * want to avoid that "side-effect", decorate it with
 * {@link StickyList}.</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <T> Element type
 * @see StickyList
 * @since 0.23
 */
public final class Shuffled<T> extends ListEnvelope<T> {

    /**
     * Ctor.
     * @param src The underlying collection
     */
    @SafeVarargs
    public Shuffled(final T... src) {
        this(new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param src The underlying collection
     */
    public Shuffled(final Iterator<T> src) {
        this(() -> src);
    }

    /**
     * Ctor.
     * @param src The underlying collection
     */
    @SuppressWarnings("unchecked")
    public Shuffled(final Iterable<T> src) {
        this(new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param src Source
     */
    public Shuffled(final Collection<T> src) {
        super(() -> {
            final List<T> items = new LinkedList<>();
            items.addAll(src);
            Collections.shuffle(items);
            return Collections.unmodifiableList(items);
        });
    }

}
