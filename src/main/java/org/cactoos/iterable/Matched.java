/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.BiFunc;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.FormattedText;

/**
 * Matched iterable.
 *
 * Iterates over 2 iterables at the same time and verify that elements with the
 *  same position has correlation by the function. The function might be equals,
 *  endsWith, greaterThen, nonNull, empty, negative, positive, etc.
 *
 * @param <X> Type of item.
 * @since 0.39
 */
public final class Matched<X> implements Iterable<X> {

    /**
     * The matched iterator.
     */
    private final Unchecked<Iterator<X>> mtr;

    /**
     * Ctor.
     * @param fst The first part of duplex iterator.
     * @param snd The second part of duplex iterator.
     */
    public Matched(final Iterable<X> fst, final Iterable<X> snd) {
        this(Object::equals, fst, snd);
    }

    /**
     * Ctor.
     * @param fnc The function to detect the correlation between elements.
     * @param fst The first part of duplex iterator.
     * @param snd The second part of duplex iterator.
     */
    public Matched(
        final BiFunc<X, X, Boolean> fnc,
        final Iterable<X> fst, final Iterable<X> snd
    ) {
        this(
            () -> {
                final Iterator<X> ftr = fst.iterator();
                final Iterator<X> str = snd.iterator();
                final List<X> rslt = new LinkedList<>();
                while (ftr.hasNext()) {
                    if (!str.hasNext()) {
                        throw new IllegalStateException(
                            "Size mismatch of iterators"
                        );
                    }
                    final X fvl = ftr.next();
                    final X svl = str.next();
                    if (fnc.apply(fvl, svl)) {
                        rslt.add(fvl);
                    } else {
                        throw new IllegalStateException(
                            new FormattedText(
                                "The is no correlation between `%s` and `%s`.",
                                fvl, svl
                            ).asString()
                        );
                    }
                }
                return Collections.unmodifiableList(rslt).iterator();
            }
        );
    }

    /**
     * Ctor.
     * @param mtr The matched iterator.
     */
    private Matched(final Scalar<Iterator<X>> mtr) {
        this.mtr = new Unchecked<>(mtr);
    }

    @Override
    public Iterator<X> iterator() {
        return this.mtr.value();
    }
}
