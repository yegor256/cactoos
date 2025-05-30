/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Collections;
import java.util.Map;
import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Map decorator that goes through the map only once.
 *
 * <p>
 * The map is read-only.
 * </p>
 *
 * <p>
 * Objects of this class are thread-safe.
 * </p>
 *
 * @param <X> Type of key
 * @param <Y> Type of value
 * @since 0.24
 */
public final class Synced<X, Y> extends MapEnvelope<X, Y> {

    /**
     * Ctor.
     * @param list List of entries
     */
    @SafeVarargs
    public Synced(final Map.Entry<X, Y>... list) {
        this(new IterableOf<>(list));
    }

    /**
     * Ctor.
     * @param map The map to extend
     * @param list List of entries
     */
    @SafeVarargs
    public Synced(final Map<X, Y> map, final Map.Entry<X, Y>... list) {
        this(map, new IterableOf<>(list));
    }

    /**
     * Ctor.
     * @param key Func to create key
     * @param value Func to create value
     * @param map The map to extend
     * @param list List of items
     * @param <Z> Type of items in the list
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public <Z> Synced(
        final Func<? super Z, ? extends X> key,
        final Func<? super Z, ? extends Y> value,
        final Map<? extends X, ? extends Y> map,
        final Iterable<? extends Z> list
    ) {
        this(
            item -> new MapEntry<>(key.apply(item), value.apply(item)),
            map, list
        );
    }

    /**
     * Ctor.
     * @param list List of items
     * @param key Func to create key
     * @param value Func to create value
     * @param <Z> Type of items in the list
     */
    public <Z> Synced(
        final Iterable<? extends Z> list,
        final Func<? super Z, ? extends X> key,
        final Func<? super Z, ? extends Y> value
    ) {
        this(item -> new MapEntry<>(key.apply(item), value.apply(item)), list);
    }

    /**
     * Ctor.
     * @param entry Func to create entry
     * @param list List of items
     * @param <Z> Type of items in the list
     */
    @SafeVarargs
    public <Z> Synced(
        final Func<? super Z, Map.Entry<? extends X, ? extends Y>> entry,
        final Z... list
    ) {
        this(new Mapped<>(entry, list));
    }

    /**
     * Ctor.
     * @param entry Func to create entry
     * @param list List of items
     * @param <Z> Type of items in the list
     */
    public <Z> Synced(
        final Func<Z, Map.Entry<? extends X, ? extends Y>> entry,
        final Iterable<Z> list
    ) {
        this(new Mapped<>(entry, list));
    }

    /**
     * Ctor.
     * @param entry Func to create entry
     * @param map The map to extend
     * @param list List of items
     * @param <Z> Type of items in the list
     */
    public <Z> Synced(
        final Func<? super Z, ? extends Map.Entry<? extends X, ? extends Y>> entry,
        final Map<? extends X, ? extends Y> map,
        final Iterable<? extends Z> list
    ) {
        this(map, new Mapped<>(entry, list));
    }

    /**
     * Ctor.
     * @param list Entries for the entries
     */
    public Synced(final Iterable<Map.Entry<? extends X, ? extends Y>> list) {
        this(new MapOf<>(list));
    }

    /**
     * Ctor.
     * @param map Pre-existing map we want to extend
     * @param list Entries for the entries
     */
    public Synced(
        final Map<? extends X, ? extends Y> map,
        final Iterable<Map.Entry<? extends X, ? extends Y>> list
    ) {
        this(new MapOf<>(map, list));
    }

    /**
     * Ctor.
     * @param map The map
     */
    @SuppressWarnings("unchecked")
    public Synced(final Map<? extends X, ? extends Y> map) {
        super((Map<X, Y>) Collections.synchronizedMap(map));
    }
}
