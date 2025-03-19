/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Iterator;
import java.util.Map;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Unchecked;

/**
 * Map difference.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <K> Type of key
 * @param <V> Type of value
 * @since 0.58.0
 */
public final class MapDiff<K, V> extends MapEnvelope<K, V> {

    /**
     * Ctor.
     * @param first First map
     * @param second Second map
     */
    public MapDiff(final Iterable<Map.Entry<K, V>> first,
        final Iterable<Map.Entry<K, V>> second) {
        super(
            computeDiff(
                mapFromEntries(first),
                mapFromEntries(second)
            )
        );
    }

    /**
     * Ctor.
     * @param first First iterator
     * @param second Second iterator
     */
    public MapDiff(final Iterator<Map.Entry<K, V>> first,
        final Iterator<Map.Entry<K, V>> second) {
        this(
            new IterableOf<>(first),
            new IterableOf<>(second)
        );
    }

    /**
     * Ctor.
     * @param first First iterable supplier
     * @param second Second iterable supplier
     */
    public MapDiff(
        final Scalar<Iterable<Map.Entry<K, V>>> first,
        final Scalar<Iterable<Map.Entry<K, V>>> second
    ) {
        this(
            new Unchecked<>(first).value(),
            new Unchecked<>(second).value()
        );
    }

    /**
     * Ctor.
     * @param first First map
     * @param second Second map
     */
    public MapDiff(final Map<K, V> first, final Map<K, V> second) {
        super(computeDiff(first, second));
    }

    /**
     * Compute the difference between two maps.
     * @param first The first map
     * @param second The second map
     * @param <K> Type of keys
     * @param <V> Type of values
     * @return The difference map (entries in first but not in second)
     */
    private static <K, V> MapOf<K, V> computeDiff(
        final Map<K, V> first,
        final Map<K, V> second
    ) {
        final MapOf<K, V> result = new MapOf<>(first);
        result.keySet().removeAll(second.keySet());
        return result;
    }

    /**
     * Create a map from entries.
     * @param entries The entries
     * @param <K> Type of keys
     * @param <V> Type of values
     * @return The map
     */
    private static <K, V> MapOf<K, V> mapFromEntries(
        final Iterable<Map.Entry<K, V>> entries
    ) {
        final MapOf<K, V> map = new MapOf<>();
        for (final Map.Entry<K, V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
