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

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.cactoos.Func;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.SyncScalar;

/**
 * Map decorator that goes through the map only once.
 *
 * <p>The map is read-only.</p>
 *
 * <p>Objects of this class are thread-safe.</p>
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of key
 * @param <Y> Type of value
 * @since 0.24
 */
public final class SyncMap<X, Y> extends MapEnvelope<X, Y> {

    /**
     * Ctor.
     * @param list List of entries
     */
    @SafeVarargs
    public SyncMap(final Map.Entry<X, Y>... list) {
        this(new IterableOf<>(list));
    }

    /**
     * Ctor.
     * @param map The map to extend
     * @param list List of entries
     */
    @SafeVarargs
    public SyncMap(final Map<X, Y> map, final Map.Entry<X, Y>... list) {
        this(map, new IterableOf<>(list));
    }

    /**
     * Ctor.
     * @param map The map to extend
     * @param list List of items
     * @param key Func to create key
     * @param value Func to create value
     * @param <Z> Type of items in the list
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public <Z> SyncMap(final Func<Z, X> key,
        final Func<Z, Y> value, final Map<X, Y> map,
        final Iterable<Z> list) {
        this(
            item -> new MapEntry<>(key.apply(item), value.apply(item)),
            map, list
        );
    }

    /**
     * Ctor.
     * @param list List of items
     * @param key Func to create key
     * @param value Func to create value
     * @param <Z> Type of items in the list
     */
    public <Z> SyncMap(final Iterable<Z> list, final Func<Z, X> key,
        final Func<Z, Y> value) {
        this(item -> new MapEntry<>(key.apply(item), value.apply(item)), list);
    }

    /**
     * Ctor.
     * @param list List of items
     * @param entry Func to create entry
     * @param <Z> Type of items in the list
     */
    @SafeVarargs
    public <Z> SyncMap(final Func<Z, Map.Entry<X, Y>> entry,
        final Z... list) {
        this(new Mapped<>(entry, list));
    }

    /**
     * Ctor.
     * @param list List of items
     * @param entry Func to create entry
     * @param <Z> Type of items in the list
     */
    public <Z> SyncMap(final Func<Z, Map.Entry<X, Y>> entry,
        final Iterable<Z> list) {
        this(new Mapped<>(entry, list));
    }

    /**
     * Ctor.
     * @param map The map to extend
     * @param list List of items
     * @param entry Func to create entry
     * @param <Z> Type of items in the list
     */
    public <Z> SyncMap(final Func<Z, Map.Entry<X, Y>> entry,
        final Map<X, Y> map, final Iterable<Z> list) {
        this(map, new Mapped<>(entry, list));
    }

    /**
     * Ctor.
     * @param list Entries for the entries
     */
    public SyncMap(final Iterable<Map.Entry<X, Y>> list) {
        this(new MapOf<>(list));
    }

    /**
     * Ctor.
     * @param list Entries for the entries
     */
    public SyncMap(final Iterator<Map.Entry<X, Y>> list) {
        this(() -> list);
    }

    /**
     * Ctor.
     * @param map Pre-existing map we want to extend
     * @param list Entries for the entries
     */
    public SyncMap(final Map<X, Y> map,
        final Iterable<Map.Entry<X, Y>> list) {
        this(new MapOf<>(map, list));
    }

    /**
     * Ctor.
     * @param map The map
     */
    public SyncMap(final Map<X, Y> map) {
        super(
            new SyncScalar<>(
                () -> {
                    final Map<X, Y> temp = new ConcurrentHashMap<>(0);
                    temp.putAll(map);
                    return temp;
                }
            )
        );
    }

}
