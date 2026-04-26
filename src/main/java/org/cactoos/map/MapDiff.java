/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.HashMap;
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
        this(
            new HashMap<K, V>() {
                private static final long serialVersionUID = 1L;
                {
                    for (final Map.Entry<K, V> entry : first) {
                        this.put(entry.getKey(), entry.getValue());
                    }
                }
            },
            new HashMap<K, V>() {
                private static final long serialVersionUID = 1L;
                {
                    for (final Map.Entry<K, V> entry : second) {
                        this.put(entry.getKey(), entry.getValue());
                    }
                }
            }
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
            new IterableOf<Map.Entry<K, V>>(
                () -> new Unchecked<>(first).value().iterator()
            ),
            new IterableOf<Map.Entry<K, V>>(
                () -> new Unchecked<>(second).value().iterator()
            )
        );
    }

    /**
     * Ctor.
     * @param first First map
     * @param second Second map
     */
    public MapDiff(final Map<K, V> first, final Map<K, V> second) {
        super(new HashMap<K, V>(first) {
            private static final long serialVersionUID = 1L;
            {
                this.keySet().removeAll(second.keySet());
            }
        });
    }
}
