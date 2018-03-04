/**
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
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.TextOf;

/**
 * Map envelope.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
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
}
