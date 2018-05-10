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
import org.cactoos.collection.CollectionNoNulls;

/**
 * A decorator of {@link Map} that tolerates no NULLs.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <K> Type of key
 * @param <V> Type of value
 * @since 0.27
 */
@SuppressWarnings("PMD.TooManyMethods")
public class MapNoNulls<K, V> implements Map<K, V> {

    /**
     * The map.
     */
    private final Map<K, V> map;

    /**
     * Ctor.
     * @param origin The scalar
     */
    public MapNoNulls(final Map<K, V> origin) {
        this.map = origin;
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
        if (key == null) {
            throw new IllegalStateException(
                "Key at #containsKey(K) is NULL"
            );
        }
        return this.map.containsKey(key);
    }

    @Override
    public final boolean containsValue(final Object value) {
        if (value == null) {
            throw new IllegalStateException(
                "Value at #containsValue(K) is NULL"
            );
        }
        return this.map.containsValue(value);
    }

    @Override
    public final V get(final Object key) {
        if (key == null) {
            throw new IllegalStateException(
                "Key at #get(K) is NULL"
            );
        }
        final V value = this.map.get(key);
        if (value == null) {
            throw new IllegalStateException(
                String.format(
                    "Value returned by #get(%s) is NULL",
                    key
                )
            );
        }
        return value;
    }

    @Override
    public final V put(final K key, final V value) {
        if (key == null) {
            throw new IllegalStateException(
                String.format(
                    "Key at #put(K,%s) is NULL", value
                )
            );
        }
        if (value == null) {
            throw new IllegalStateException(
                String.format(
                    "Value at #put(%s,V) is NULL", key
                )
            );
        }
        final V result = this.map.put(key, value);
        if (result == null) {
            throw new IllegalStateException(
                String.format(
                    "Value returned by #put(%s,%s) is NULL",
                    key, value
                )
            );
        }
        return result;
    }

    @Override
    public final V remove(final Object key) {
        if (key == null) {
            throw new IllegalStateException(
                "Key at #remove(K) is NULL"
            );
        }
        final V result = this.map.remove(key);
        if (result == null) {
            throw new IllegalStateException(
                String.format(
                    "Value returned by #remove(%s) is NULL",
                    key
                )
            );
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void putAll(final Map<? extends K, ? extends V> items) {
        this.map.putAll(new MapNoNulls<>((Map<K, V>) items));
    }

    @Override
    public final void clear() {
        this.map.clear();
    }

    @Override
    public final Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public final Collection<V> values() {
        return new CollectionNoNulls<>(this.map.values());
    }

    @Override
    public final Set<Map.Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }

}
