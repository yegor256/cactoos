/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Iterable as {@link Map}.
 *
 * <p>
 * This class groups objects from iterable by applying functions for keys and
 * values
 * </p>
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @param <K> Type of key
 * @param <V> Type of value
 * @param <T> Type of entry objects of functions
 * @since 0.30
 */
public final class Grouped<K, V, T> extends MapEnvelope<K, List<V>> {

    /**
     * Ctor.
     *
     * @param list Iterable which is used to retrieve data from
     * @param keys Function to get a key
     * @param values Function to get a value
     */
    public Grouped(
        final Iterable<? extends T> list,
        final Function<? super T, ? extends K> keys,
        final Function<? super T, ? extends V> values
    ) {
        super(new HashMap<K, List<V>>() {
            private static final long serialVersionUID = 1L;
            {
                for (final T item : list) {
                    this.computeIfAbsent(
                        keys.apply(item), k -> new ArrayList<>()
                    ).add(values.apply(item));
                }
            }
        });
    }
}
