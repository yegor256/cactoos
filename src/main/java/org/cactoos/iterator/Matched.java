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
package org.cactoos.iterator;

import java.util.Iterator;
import org.cactoos.BiFunc;
import org.cactoos.func.UncheckedBiFunc;
import org.cactoos.text.FormattedText;

/**
 * Matched iterator.
 *
 * Iterates over 2 iterators at the same time and verify that elements with the
 * same position has correlation by the function. The function might be equals,
 * endsWith, greaterThen, nonNull, empty, negative, positive, etc.
 *
 * @param <X> Type of item in first iterator.
 * @param <Y> Type of item in second iterator.
 * @since 0.47
 */
public final class Matched<X, Y> implements Iterator<X> {

    /**
     * The correlation function.
     */
    private final UncheckedBiFunc<? super X, ? super Y, Boolean> func;

    /**
     * First iterator.
     */
    private final Iterator<? extends X> first;

    /**
     * Second iterator.
     */
    private final Iterator<? extends Y> second;

    /**
     * Ctor.
     * @param fnc The function to detect the correlation between elements.
     * @param fst The first part of duplex iterator.
     * @param snd The second part of duplex iterator.
     */
    public Matched(
        final BiFunc<? super X, ? super Y, Boolean> fnc,
        final Iterator<? extends X> fst,
        final Iterator<? extends Y> snd
    ) {
        this.func = new UncheckedBiFunc<>(fnc);
        this.first = fst;
        this.second = snd;
    }

    @Override
    public boolean hasNext() {
        return this.first.hasNext() || this.second.hasNext();
    }

    @Override
    public X next() {
        if (!this.first.hasNext() || !this.second.hasNext()) {
            throw new IllegalStateException("Size mismatch of iterators");
        }
        final X fvl = this.first.next();
        final Y svl = this.second.next();
        if (!this.func.apply(fvl, svl)) {
            throw new IllegalStateException(
                new FormattedText(
                    "There is no correlation between `%s` and `%s`.",
                    fvl, svl
                ).toString()
            );
        }
        return fvl;
    }

}
