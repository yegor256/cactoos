/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.LinkedHashMap;
import java.util.Map;
import org.cactoos.BiFunc;
import org.cactoos.map.MapEntry;
import org.cactoos.scalar.Sticky;

/**
 * Func that accepts two arguments and caches previously calculated values
 * and doesn't recalculate again.
 *
 * <p>Pay attention that this class is not thread-safe. It is highly
 * recommended to always decorate it with {@link SyncBiFunc}.</p>
 *
 * <p>This {@link BiFunc} decorator technically is an in-memory
 * cache.</p>
 *
 * <p>There is no thread-safety guarantee.
 * @param <X> Type of input
 * @param <Y> Type of input
 * @param <Z> Type of output
 * @see Sticky
 * @since 0.13
 */
public final class StickyBiFunc<X, Y, Z> implements BiFunc<X, Y, Z> {

    /**
     * Original func.
     */
    private final BiFunc<X, Y, Z> func;

    /**
     * Cache.
     */
    private final Map<Map.Entry<X, Y>, Z> cache;

    /**
     * Maximum cache size.
     */
    private final int size;

    /**
     * Ctor.
     * @param fnc Func original
     */
    public StickyBiFunc(final BiFunc<X, Y, Z> fnc) {
        this(fnc, Integer.MAX_VALUE);
    }

    /**
     * Ctor.
     * @param fnc Func original
     * @param max Maximum buffer size
     * @since 0.26
     */
    public StickyBiFunc(final BiFunc<X, Y, Z> fnc, final int max) {
        this.func = fnc;
        this.cache = new LinkedHashMap<>(0);
        this.size = max;
    }

    @Override
    public Z apply(final X first, final Y second) throws Exception {
        final Map.Entry<X, Y> key = new MapEntry<>(first, second);
        while (this.cache.size() > this.size) {
            this.cache.remove(this.cache.keySet().iterator().next());
        }
        if (!this.cache.containsKey(key)) {
            this.cache.put(key, this.func.apply(first, second));
        }
        return this.cache.get(key);
    }

}
