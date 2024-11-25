/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
package org.cactoos.map;

import java.util.HashMap;
import java.util.Map;
import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;

/**
 * Implementation of {@link Map}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of key
 * @param <Y> Type of value
 * @since 0.4
 */
public final class MapOf<X, Y> extends MapEnvelope<X, Y> {

    /**
     * Ctor.
     * @param key The key
     * @param value The value
     */
    public MapOf(final X key, final Y value) {
        this(new MapEntry<>(key, value));
    }

    /**
     * Ctor.
     * @param list List of entries
     */
    @SafeVarargs
    public MapOf(final Map.Entry<? extends X, ? extends Y>... list) {
        this(new IterableOf<>(list));
    }

    /**
     * Ctor.
     * @param src Map to extend
     * @param key New key
     * @param value New value
     */
    public MapOf(
        final Map<? extends X, ? extends Y> src,
        final X key,
        final Y value
    ) {
        this(src, new MapEntry<>(key, value));
    }

    /**
     * Ctor.
     * @param src The map to extend
     * @param list List of entries
     * @since 0.12
     */
    @SafeVarargs
    public MapOf(
        final Map<? extends X, ? extends Y> src,
        final Map.Entry<? extends X, ? extends Y>... list
    ) {
        this(src, new IterableOf<>(list));
    }

    /**
     * Ctor.
     * @param key Func to create key
     * @param value Func to create value
     * @param list List of items
     * @param <Z> Type of items in the list
     * @since 0.11
     */
    public <Z> MapOf(
        final Func<? super Z, ? extends X> key,
        final Func<? super Z, ? extends Y> value,
        final Iterable<? extends Z> list
    ) {
        this(item -> new MapEntry<>(key.apply(item), value.apply(item)), list);
    }

    /**
     * Ctor.
     * @param key Func to create key
     * @param value Func to create value
     * @param src The map to extend
     * @param list List of items
     * @param <Z> Type of items in the list
     * @since 0.12
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public <Z> MapOf(
        final Func<? super Z, ? extends X> key,
        final Func<? super Z, ? extends Y> value,
        final Map<? extends X, ? extends Y> src,
        final Iterable<Z> list
    ) {
        this(
            item -> new MapEntry<>(key.apply(item), value.apply(item)),
            src, list
        );
    }

    /**
     * Ctor.
     * @param entry Func to create entry
     * @param list List of items
     * @param <Z> Type of items in the list
     * @since 0.11
     */
    public <Z> MapOf(
        final Func<? super Z, Map.Entry<? extends X, ? extends Y>> entry,
        final Iterable<? extends Z> list
    ) {
        this(new Mapped<>(entry, list));
    }

    /**
     * Ctor.
     * @param entry Func to create entry
     * @param src The map to extend
     * @param list List of items
     * @param <Z> Type of items in the list
     * @since 0.11
     */
    public <Z> MapOf(
        final Func<? super Z, Map.Entry<? extends X, ? extends Y>> entry,
        final Map<? extends X, ? extends Y> src,
        final Iterable<? extends Z> list
    ) {
        this(src, new Mapped<>(entry, list));
    }

    /**
     * Ctor.
     * @param src Map to extend
     * @param list List of the entries
     * @since 0.12
     */
    public MapOf(
        final Map<? extends X, ? extends Y> src,
        final Iterable<Map.Entry<? extends X, ? extends Y>> list
    ) {
        this(new Joined<Map.Entry<? extends X, ? extends Y>>(src.entrySet(), list));
    }

    /**
     * Ctor.
     * @param entries List of the entries
     */
    public MapOf(final Iterable<Map.Entry<? extends X, ? extends Y>> entries) {
        super(MapOf.make(entries));
    }

    /**
     * Ctor.
     * @param entries List of the entries
     * @param <X> Key type
     * @param <Y> Value type
     * @return Map created
     */
    private static <X, Y> Map<X, Y> make(
        final Iterable<Map.Entry<? extends X, ? extends Y>> entries) {
        int initialCapacity = (entries instanceof Collection) ? ((Collection<?>) entries).size() : 16;
        final Map<X, Y> map = new HashMap<>(initialCapacity);
        for (final Map.Entry<? extends X, ? extends Y> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
