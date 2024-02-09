/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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

import java.util.Map;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.iterable.Mapped;

/**
 * This class can be used to merge a few {@link Map}.
 * Repeatable keys will be overridden by next ones.
 *
 * @param <K> Key type
 * @param <V> Value type
 * @since 1.0
 */
public final class Merged<K, V> extends MapEnvelope<K, V> {
    /**
     * Ctor.
     * @param maps Maps to merge.
     */
    @SafeVarargs
    public Merged(final Map<? extends K, ? extends V>... maps) {
        this(new IterableOf<>(maps));
    }

    /**
     * Ctor.
     * @param maps Iterable of {@link Map}s to merge.
     */
    public Merged(final Iterable<? extends Map<? extends K, ? extends V>> maps) {
        super(
            new MapOf<>(
                new Joined<>(
                    new Mapped<>(
                        Map::entrySet,
                        maps
                    )
                )
            )
        );
    }
}
