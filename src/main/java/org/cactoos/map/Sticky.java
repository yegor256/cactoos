/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.HashMap;
import java.util.Map;
import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Map decorator that caches entries from the underlying map.
 *
 * <p>This decorator reads all entries from the underlying map once
 * and stores them in an internal HashMap, preventing re-evaluation
 * on subsequent accesses. This is useful when the underlying map
 * is backed by lazy iterables that may be re-evaluated on each access.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * Map<String, Integer> map = new Sticky<>(
 *     new MapOf<>(
 *         new Mapped<>(
 *             item -> new MapEntry<>(item, item.length()),
 *             items
 *         )
 *     )
 * );
 * }</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of key
 * @param <Y> Type of value
 * @since 0.56
 */
public final class Sticky<X, Y> extends MapEnvelope<X, Y> {

    /**
     * Ctor.
     * @param entries List of entries
     */
    @SafeVarargs
    public Sticky(final Map.Entry<? extends X, ? extends Y>... entries) {
        this(new IterableOf<>(entries));
    }

    /**
     * Ctor.
     * @param src Map to extend
     * @param entries List of entries
     */
    @SafeVarargs
    public Sticky(
        final Map<? extends X, ? extends Y> src,
        final Map.Entry<? extends X, ? extends Y>... entries
    ) {
        this(src, new IterableOf<>(entries));
    }

    /**
     * Ctor.
     * @param key Func to create key
     * @param value Func to create value
     * @param list List of items
     * @param <Z> Type of items in the list
     */
    public <Z> Sticky(
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
     * @param src Map to extend
     * @param list List of items
     * @param <Z> Type of items in the list
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public <Z> Sticky(
        final Func<? super Z, ? extends X> key,
        final Func<? super Z, ? extends Y> value,
        final Map<? extends X, ? extends Y> src,
        final Iterable<? extends Z> list
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
     */
    public <Z> Sticky(
        final Func<? super Z, Map.Entry<? extends X, ? extends Y>> entry,
        final Iterable<? extends Z> list
    ) {
        this(new Mapped<>(entry, list));
    }

    /**
     * Ctor.
     * @param entry Func to create entry
     * @param src Map to extend
     * @param list List of items
     * @param <Z> Type of items in the list
     */
    public <Z> Sticky(
        final Func<? super Z, Map.Entry<? extends X, ? extends Y>> entry,
        final Map<? extends X, ? extends Y> src,
        final Iterable<? extends Z> list
    ) {
        this(src, new Mapped<>(entry, list));
    }

    /**
     * Ctor.
     * @param entries List of entries
     */
    public Sticky(final Iterable<Map.Entry<? extends X, ? extends Y>> entries) {
        this(new MapOf<>(entries));
    }

    /**
     * Ctor.
     * @param src Map to extend
     * @param entries List of entries
     */
    public Sticky(
        final Map<? extends X, ? extends Y> src,
        final Iterable<Map.Entry<? extends X, ? extends Y>> entries
    ) {
        this(new MapOf<>(src, entries));
    }

    /**
     * Ctor.
     * @param map The map to cache
     */
    public Sticky(final Map<? extends X, ? extends Y> map) {
        super(Sticky.copy(map));
    }

    /**
     * Copy entries from source map to a new HashMap.
     * @param src Source map
     * @param <X> Key type
     * @param <Y> Value type
     * @return New HashMap with copied entries
     */
    private static <X, Y> Map<X, Y> copy(
        final Map<? extends X, ? extends Y> src
    ) {
        final Map<X, Y> target = new HashMap<>(src.size());
        for (final Map.Entry<? extends X, ? extends Y> entry : src.entrySet()) {
            target.put(entry.getKey(), entry.getValue());
        }
        return target;
    }
}
