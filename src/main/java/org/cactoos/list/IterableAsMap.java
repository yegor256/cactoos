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

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Iterable as {@link Map}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of key
 * @param <Y> Type of value
 * @since 0.4
 * @todo #180:30min This class is not implemented fully. Most methods
 *  are not yet supported. Let's implement them, each time with a use
 *  case specific method. And let's make sure they all are tested. This
 *  is very similar to what we have in IterableAsList.
 */
public final class IterableAsMap<X, Y> implements Map<X, Y> {

    /**
     * The iterable.
     */
    private final Iterable<Map.Entry<X, Y>> entries;

    /**
     * Ctor.
     * @param list List of entries
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public IterableAsMap(final Map.Entry<X, Y>... list) {
        this(new ArrayAsIterable<>(list));
    }

    /**
     * Ctor.
     * @param list Entries for the entries
     */
    public IterableAsMap(final Iterable<Map.Entry<X, Y>> list) {
        this.entries = list;
    }

    @Override
    public int size() {
        return new LengthOfIterable(this.entries).asValue();
    }

    @Override
    public boolean isEmpty() {
        return !this.entries.iterator().hasNext();
    }

    @Override
    public boolean containsKey(final Object key) {
        throw new UnsupportedOperationException(
            "#containsKey() not implemented yet"
        );
    }

    @Override
    public boolean containsValue(final Object value) {
        throw new UnsupportedOperationException(
            "#containsValue() not implemented yet"
        );
    }

    @Override
    public Y get(final Object key) {
        throw new UnsupportedOperationException(
            "#get() not implemented yet"
        );
    }

    @Override
    public Y put(final X key, final Y value) {
        throw new UnsupportedOperationException(
            "#put() is not supported"
        );
    }

    @Override
    public Y remove(final Object key) {
        throw new UnsupportedOperationException(
            "#remove() is not supported"
        );
    }

    @Override
    public void putAll(final Map<? extends X, ? extends Y> list) {
        throw new UnsupportedOperationException(
            "#putAll() is not supported"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
            "#clear() is not supported"
        );
    }

    @Override
    public Set<X> keySet() {
        throw new UnsupportedOperationException(
            "#keySet() not implemented yet"
        );
    }

    @Override
    public Collection<Y> values() {
        throw new UnsupportedOperationException(
            "#values() not implemented yet"
        );
    }

    @Override
    public Set<Map.Entry<X, Y>> entrySet() {
        return new HashSet<>(
            new IterableAsList<>(this.entries)
        );
    }

}
