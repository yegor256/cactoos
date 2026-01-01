/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Map;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.HashCode;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * MapEntry as {@link java.util.AbstractMap.Entry}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <K> Key type
 * @param <V> Value type
 * @since 0.9
 */
public final class MapEntry<K, V> implements Map.Entry<K, V> {
    /**
     * The key.
     */
    private final K key;

    /**
     * The value.
     */
    private final V value;

    /**
     * Ctor.
     * @param src The key of the entry's map.
     * @param trgt The value associated to the key of the entry's map.
     */
    public MapEntry(final K src, final V trgt) {
        this.key = src;
        this.value = trgt;
    }

    @Override
    public String toString() {
        return new UncheckedText(
            new FormattedText(
                "%s=%s",
                this.key,
                this.value
            )
        ).asString();
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(final V val) {
        throw new UnsupportedOperationException(
            "#setValue() is not supported"
        );
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Map.Entry
            && Map.Entry.class.cast(obj).getKey().equals(this.key)
            && Map.Entry.class.cast(obj).getValue().equals(this.value);
    }

    @Override
    public int hashCode() {
        return new HashCode(
            new IterableOf<>(this.key, this.value)
        ).value();
    }
}
