/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
package org.cactoos.set;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.cactoos.iterable.IterableOf;

/**
 * Iterable as Sorted {@link Set} based on {@link TreeSet}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Set type
 * @since 1.0.0
 */
@SuppressWarnings("PMD.OnlyOneConstructorShouldDoInitialization")
public final class Sorted<T> extends SetEnvelope<T> implements SortedSet<T> {

    /**
     * The original Set this object delegates to.
     */
    private final SortedSet<T> origin;

    /**
     * Ctor.
     * @param cmp Comparator
     * @param array An array of some elements
     */
    @SafeVarargs
    public Sorted(final Comparator<T> cmp, final T... array) {
        this(cmp, new IterableOf<>(array));
    }

    /**
     * Ctor.
     * @param cmp Comparator
     * @param src An {@link Iterable}
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public Sorted(final Comparator<T> cmp, final Iterable<T> src) {
        this(new TreeSet<>(cmp));
        src.forEach(super::add);
    }

    /**
     * Primary ctor.
     * @param origin The original SortedSet to delegate to.
     */
    private Sorted(final SortedSet<T> origin) {
        super(origin);
        this.origin = origin;
    }

    @Override
    public Comparator<? super T> comparator() {
        return this.origin.comparator();
    }

    @Override
    public SortedSet<T> subSet(final T begin, final T end) {
        return this.origin.subSet(begin, end);
    }

    @Override
    public SortedSet<T> headSet(final T end) {
        return this.origin.headSet(end);
    }

    @Override
    public SortedSet<T> tailSet(final T from) {
        return this.origin.tailSet(from);
    }

    @Override
    public T first() {
        return this.origin.first();
    }

    @Override
    public T last() {
        return this.origin.last();
    }
}
