/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.cactoos.Scalar;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Folded;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.SumOfIntScalar;
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.TextOf;

/**
 * Map envelope.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of key
 * @param <Y> Type of value
 * @see StickyMap
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
    private final UncheckedScalar<Map<X, Y>> map;

    /**
     * Ctor.
     * @param scalar The scalar
     */
    public MapEnvelope(final Scalar<Map<X, Y>> scalar) {
        this.map = new UncheckedScalar<>(scalar);
    }

    @Override
    public final int size() {
        return this.map.value().size();
    }

    @Override
    public final boolean isEmpty() {
        return this.map.value().isEmpty();
    }

    @Override
    public final boolean containsKey(final Object key) {
        return this.map.value().containsKey(key);
    }

    @Override
    public final boolean containsValue(final Object value) {
        return this.map.value().containsValue(value);
    }

    @Override
    public final Y get(final Object key) {
        return this.map.value().get(key);
    }

    @Override
    public final Y put(final X key, final Y value) {
        throw new UnsupportedOperationException(
            "#put() is not supported, it's a read-only map"
        );
    }

    @Override
    public final Y remove(final Object key) {
        throw new UnsupportedOperationException(
            "#remove() is not supported, it's a read-only map"
        );
    }

    @Override
    public final void putAll(final Map<? extends X, ? extends Y> list) {
        throw new UnsupportedOperationException(
            "#putAll() is not supported, it's a read-only map"
        );
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException(
            "#clear() is not supported, it's a read-only map"
        );
    }

    @Override
    public final Set<X> keySet() {
        return this.map.value().keySet();
    }

    @Override
    public final Collection<Y> values() {
        return this.map.value().values();
    }

    @Override
    public final Set<Map.Entry<X, Y>> entrySet() {
        return this.map.value().entrySet();
    }

    @Override
    public final String toString() {
        return new StringBuilder()
            .append('{')
            .append(new TextOf(this.entrySet()).toString())
            .append('}')
            .toString();
    }

    @Override
    public final boolean equals(final Object other) {
        return new UncheckedScalar<>(
            new Or(
                () -> this == other,
                new And(
                    () -> Map.class.isAssignableFrom(other.getClass()),
                    () -> this.size() == ((Map<?, ?>) other).size(),
                    () -> this.contentsEqual((Map<?, ?>) other)
                )
            )
        ).value();
    }

    // @checkstyle MagicNumberCheck (30 lines)
    @Override
    public final int hashCode() {
        return new UncheckedScalar<>(
            new Folded<>(
                42,
                (hash, entry) -> {
                    final int keys = new SumOfIntScalar(
                        () -> 37 * hash,
                        () -> entry.getKey().hashCode()
                    ).value();
                    return new SumOfIntScalar(
                        () -> 37 * keys,
                        () -> entry.getValue().hashCode()
                    ).value();
                },
                this.map.value().entrySet()
            )
        ).value();
    }

    /**
     * Indicates whether contents of an other {@code Map} is the same
     * as contents of this one on entry-by-entry basis.
     * @param other Another instance of {@code Map} to compare with
     * @return True if contents are equal false otherwise
     */
    private Boolean contentsEqual(final Map<?, ?> other) {
        return new UncheckedScalar<>(
            new And(
                (entry) -> {
                    final X key = entry.getKey();
                    final Y value = entry.getValue();
                    return new And(
                        () -> other.containsKey(key),
                        () -> other.get(key).equals(value)
                    ).value();
                }, this.entrySet()
            )
        ).value();
    }
}
