/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.HashCode;
import org.cactoos.text.Concatenated;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;

/**
 * Map envelope.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of key
 * @param <Y> Type of value
 * @since 0.24
 * @checkstyle AbstractClassNameCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.TooManyMethods",
        "PMD.AbstractNaming"
    }
)
public abstract class MapEnvelope<X, Y> implements Map<X, Y> {

    /**
     * The map.
     */
    private final Map<X, Y> map;

    /**
     * Ctor.
     * @param original The original map.
     */
    public MapEnvelope(final Map<X, Y> original) {
        this.map = original;
    }

    @Override
    public final int size() {
        return this.map.size();
    }

    @Override
    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public final boolean containsKey(final Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public final boolean containsValue(final Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public final Y get(final Object key) {
        return this.map.get(key);
    }

    @Override
    public final Y put(final X key, final Y value) {
        return this.map.put(key, value);
    }

    @Override
    public final Y remove(final Object key) {
        return this.map.remove(key);
    }

    @Override
    public final void putAll(final Map<? extends X, ? extends Y> extra) {
        this.map.putAll(extra);
    }

    @Override
    public final void clear() {
        this.map.clear();
    }

    @Override
    public final Set<X> keySet() {
        return this.map.keySet();
    }

    @Override
    public final Collection<Y> values() {
        return this.map.values();
    }

    @Override
    public final Set<Map.Entry<X, Y>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public final String toString() {
        return new Concatenated(
            new TextOf("{"),
            new Joined(
                ", ",
                new Mapped<>(Object::toString, this.entrySet())
            ),
            new TextOf("}")
        ).toString();
    }

    @Override
    public final boolean equals(final Object other) {
        return this.map.equals(other);
    }

    @Override
    public final int hashCode() {
        return new HashCode(this.map).value();
    }

}
