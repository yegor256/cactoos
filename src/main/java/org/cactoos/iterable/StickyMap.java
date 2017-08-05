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
import org.cactoos.scalar.StickyScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Map decorator that goes through the map only once.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of key
 * @param <Y> Type of value
 * @since 0.8
 */
public final class StickyMap<X, Y> implements Map<X, Y> {

    /**
     * The gate.
     */
    private final UncheckedScalar<Map<X, Y>> gate;

    /**
     * Ctor.
     * @param list List of entries
     */
    @SafeVarargs
    public StickyMap(final Map.Entry<X, Y>... list) {
        this(new IterableOf<>(list));
    }

    /**
     * Ctor.
     * @param map The map to extend
     * @param list List of entries
     * @since 0.12
     */
    @SafeVarargs
    public StickyMap(final Map<X, Y> map, final Map.Entry<X, Y>... list) {
        this(map, new IterableOf<>(list));
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
    public <Z> StickyMap(final Map<X, Y> map,
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
     * @param key Func to create key
     * @param value Func to create value
     * @param <Z> Type of items in the list
     * @since 0.11
     */
    public <Z> StickyMap(final Iterable<Z> list, final Func<Z, X> key,
        final Func<Z, Y> value) {
        this(list, item -> new MapEntry<>(key.apply(item), value.apply(item)));
    }

    /**
     * Ctor.
     * @param list List of items
     * @param entry Func to create entry
     * @param <Z> Type of items in the list
     * @since 0.11
     */
    public <Z> StickyMap(final Iterable<Z> list,
        final Func<Z, Map.Entry<X, Y>> entry) {
        this(new Mapped<>(list, entry));
    }

    /**
     * Ctor.
     * @param map The map to extend
     * @param list List of items
     * @param entry Func to create entry
     * @param <Z> Type of items in the list
     * @since 0.12
     */
    public <Z> StickyMap(final Map<X, Y> map, final Iterable<Z> list,
        final Func<Z, Map.Entry<X, Y>> entry) {
        this(map, new Mapped<>(list, entry));
    }

    /**
     * Ctor.
     * @param list Entries for the entries
     */
    public StickyMap(final Iterable<Map.Entry<X, Y>> list) {
        this(new MapOf<>(list));
    }

    /**
     * Ctor.
     * @param map Pre-existing map we want to extend
     * @param list Entries for the entries
     * @since 0.12
     */
    public StickyMap(final Map<X, Y> map,
        final Iterable<Map.Entry<X, Y>> list) {
        this(new MapOf<>(map, list));
    }

    /**
     * Ctor.
     * @param map The map
     */
    public StickyMap(final Map<X, Y> map) {
        this.gate = new UncheckedScalar<>(
            new StickyScalar<>(
                () -> {
                    final Map<X, Y> temp = new HashMap<>(0);
                    temp.putAll(map);
                    return temp;
                }
            )
        );
    }

    @Override
    public int size() {
        return this.gate.value().size();
    }

    @Override
    public boolean isEmpty() {
        return this.gate.value().isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return this.gate.value().containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return this.gate.value().containsValue(value);
    }

    @Override
    public Y get(final Object key) {
        return this.gate.value().get(key);
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
    public void putAll(final Map<? extends X, ? extends Y> map) {
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
        return this.gate.value().keySet();
    }

    @Override
    public Collection<Y> values() {
        return this.gate.value().values();
    }

    @Override
    public Set<Map.Entry<X, Y>> entrySet() {
        return this.gate.value().entrySet();
    }

}
