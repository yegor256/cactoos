/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        super(
            StreamSupport.stream(
                list.spliterator(), false
            ).collect(
                Collectors.groupingBy(
                    keys,
                    Collectors.mapping(values, Collectors.toList())
                )
            )
        );
    }
}
