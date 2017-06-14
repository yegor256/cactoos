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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.cactoos.func.StickyScalar;
import org.cactoos.func.UncheckedScalar;

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
 */
public final class IterableAsMap<X, Y> implements Map<X, Y> {

    /**
     * The map.
     */
    private final UncheckedScalar<Map<X, Y>> map;

    /**
     * Ctor.
     * @param entries Entries for the map
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public IterableAsMap(final Map.Entry<X, Y>... entries) {
        this(new ArrayAsIterable<>(entries));
    }

    /**
     * Ctor.
     * @param entries Entries for the map
     */
    public IterableAsMap(final Iterable<Map.Entry<X, Y>> entries) {
        this.map = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final Map<X, Y> temp = new HashMap<>(0);
                    for (final Map.Entry<X, Y> entry : entries) {
                        temp.put(entry.getKey(), entry.getValue());
                    }
                    return temp;
                }
            )
        );
    }

    @Override
    public int size() {
        return this.map.asValue().size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.asValue().isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return this.map.asValue().containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return this.map.asValue().containsValue(value);
    }

    @Override
    public Y get(final Object key) {
        return this.map.asValue().get(key);
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
    public void putAll(final Map<? extends X, ? extends Y> entries) {
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
        return this.map.asValue().keySet();
    }

    @Override
    public Collection<Y> values() {
        return this.map.asValue().values();
    }

    @Override
    public Set<Map.Entry<X, Y>> entrySet() {
        return this.map.asValue().entrySet();
    }

}
