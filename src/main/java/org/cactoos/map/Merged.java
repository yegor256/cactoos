/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Map;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;

/**
 * This class can be used to merge a few {@link Map}.
 * Repeatable keys will be overridden by next ones.
 *
 * @param <K> Key type
 * @param <V> Value type
 * @since 1.0
 */
public final class Merged<K, V> extends MapEnvelope<K, V> {
    /**
     * Ctor.
     * @param maps Maps to merge.
     */
    @SafeVarargs
    public Merged(final Map<? extends K, ? extends V>... maps) {
        this(new IterableOf<>(maps));
    }

    /**
     * Ctor.
     * @param maps Iterable of {@link Map}s to merge.
     */
    public Merged(final Iterable<? extends Map<? extends K, ? extends V>> maps) {
        super(
            new MapOf<>(
                new Joined<>(
                    new Mapped<>(
                        Map::entrySet,
                        maps
                    )
                )
            )
        );
    }
}
