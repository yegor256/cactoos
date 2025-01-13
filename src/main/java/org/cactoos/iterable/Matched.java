/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
package org.cactoos.iterable;

import org.cactoos.BiFunc;

/**
 * Matched iterable.
 *
 * Iterates over 2 iterables at the same time and verify that elements with the
 * same position has correlation by the function. The function might be equals,
 * endsWith, greaterThen, nonNull, empty, negative, positive, etc.
 *
 * @param <X> Type of item.
 * @since 0.39
 */
public final class Matched<X> extends IterableEnvelope<X> {
    /**
     * Ctor.
     * @param fst The first part of duplex iterator.
     * @param snd The second part of duplex iterator.
     */
    public Matched(
        final Iterable<? extends X> fst,
        final Iterable<? extends X> snd
    ) {
        this(Object::equals, fst, snd);
    }

    /**
     * Ctor.
     * @param fnc The function to detect the correlation between elements.
     * @param fst The first part of duplex iterator.
     * @param snd The second part of duplex iterator.
     * @param <Y> Type of item in snd.
     */
    public <Y> Matched(
        final BiFunc<? super X, ? super Y, Boolean> fnc,
        final Iterable<? extends X> fst,
        final Iterable<? extends Y> snd
    ) {
        super(
            new IterableOf<>(
                () -> new org.cactoos.iterator.Matched<>(
                    fnc, fst.iterator(), snd.iterator()
                )
            )
        );
    }
}
