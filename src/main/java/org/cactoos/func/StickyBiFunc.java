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
package org.cactoos.func;

import java.util.LinkedHashMap;
import java.util.Map;
import org.cactoos.BiFunc;
import org.cactoos.map.MapEntry;
import org.cactoos.scalar.StickyScalar;

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
 * @see StickyScalar
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
