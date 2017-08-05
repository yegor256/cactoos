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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.cactoos.Func;

/**
 * Iterable as {@link Map}.
 *
 * <p>This class should be used very carefully. You must understand that
 * it will fetch the entire content of the encapsulated {@link Map} on each
 * method call. It doesn't cache the data anyhow.</p>
 *
 * <p>If you don't need this {@link Map} to re-fresh its content on every call,
 * by doing round-trips to the encapsulated iterable, use
 * {@link StickyMap}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of key
 * @param <Y> Type of value
 * @see StickyMap
 * @since 0.4
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class MapOf<X, Y> implements Map<X, Y> {

    /**
     * The iterable.
     */
    private final Iterable<Map.Entry<X, Y>> entries;

    /**
     * Ctor.
     * @param list List of entries
     */
    @SafeVarargs
    public MapOf(final Map.Entry<X, Y>... list) {
        this(new IterableOf<>(list));
    }

    /**
     * Ctor.
     * @param map The map to extend
     * @param list List of entries
     * @since 0.12
     */
    @SafeVarargs
    public MapOf(final Map<X, Y> map, final Map.Entry<X, Y>... list) {
        this(map, new IterableOf<>(list));
    }

    /**
     * Ctor.
     * @param list List of items
     * @param key Func to create key
     * @param value Func to create value
     * @param <Z> Type of items in the list
     * @since 0.11
     */
    public <Z> MapOf(final Iterable<Z> list, final Func<Z, X> key,
        final Func<Z, Y> value) {
        this(list, item -> new MapEntry<>(key.apply(item), value.apply(item)));
    }

    /**
     * Ctor.
     * @param map The map to extend
     * @param list List of items
     * @param key Func to create key
     * @param value Func to create value
     * @param <Z> Type of items in the list
     * @since 0.12
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public <Z> MapOf(final Map<X, Y> map,
        final Iterable<Z> list, final Func<Z, X> key,
        final Func<Z, Y> value) {
        this(
            map, list,
            item -> new MapEntry<>(key.apply(item), value.apply(item))
        );
    }

    /**
     * Ctor.
     * @param list List of items
     * @param entry Func to create entry
     * @param <Z> Type of items in the list
     * @since 0.11
     */
    public <Z> MapOf(final Iterable<Z> list,
        final Func<Z, Map.Entry<X, Y>> entry) {
        this(new Mapped<>(list, entry));
    }

    /**
     * Ctor.
     * @param map The map to extend
     * @param list List of items
     * @param entry Func to create entry
     * @param <Z> Type of items in the list
     * @since 0.11
     */
    public <Z> MapOf(final Map<X, Y> map, final Iterable<Z> list,
        final Func<Z, Map.Entry<X, Y>> entry) {
        this(map, new Mapped<>(list, entry));
    }

    /**
     * Ctor.
     * @param map Map to extend
     * @param list List of the entries
     * @since 0.12
     */
    @SuppressWarnings("unchecked")
    public MapOf(final Map<X, Y> map,
        final Iterable<Map.Entry<X, Y>> list) {
        this(
            new Joined<>(
                map.entrySet(), list
            )
        );
    }

    /**
     * Ctor.
     * @param list List of the entries
     */
    public MapOf(final Iterable<Map.Entry<X, Y>> list) {
        this.entries = list;
    }

    @Override
    public int size() {
        return new LengthOf(this.entries).value().intValue();
    }

    @Override
    public boolean isEmpty() {
        return !this.entries.iterator().hasNext();
    }

    @Override
    public boolean containsKey(final Object key) {
        return this.map().containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return this.map().containsValue(value);
    }

    @Override
    public Y get(final Object key) {
        return this.map().get(key);
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
        return this.map().keySet();
    }

    @Override
    public Collection<Y> values() {
        return this.map().values();
    }

    @Override
    public Set<Map.Entry<X, Y>> entrySet() {
        return this.map().entrySet();
    }

    /**
     * Make a map.
     * @return Map
     */
    private Map<X, Y> map() {
        final Map<X, Y> temp = new HashMap<>(0);
        for (final Map.Entry<X, Y> entry : this.entries) {
            temp.put(entry.getKey(), entry.getValue());
        }
        return temp;
    }

}
