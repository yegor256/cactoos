/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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

import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.text.FormattedText;

/**
 * Func that repeats its calculation a few times before
 * returning the last result.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.6
 */
public final class RepeatedFunc<X, Y> implements Func<X, Y> {

    /**
     * Original func.
     */
    private final Func<X, Y> func;

    /**
     * How many times to run.
     */
    private final int times;

    /**
     * Ctor.
     * @param proc Proc
     * @param max How many times
     * @since 0.12
     */
    public RepeatedFunc(final Proc<X> proc, final int max) {
        this(new FuncOf<>(proc), max);
    }

    /**
     * Ctor.
     *
     * <p>If {@code max} is equal or less than zero {@link #apply(Object)}
     * will return {@code null}.</p>
     *
     * @param fnc Func original
     * @param max How many times
     */
    public RepeatedFunc(final Func<X, Y> fnc, final int max) {
        this.func = fnc;
        this.times = max;
    }

    @Override
    public Y apply(final X input) throws Exception {
        Y result = null;
        for (int idx = 0; idx < this.times; ++idx) {
            result = this.func.apply(input);
        }
        if (result == null) {
            throw new IllegalArgumentException(
                new FormattedText(
                    "Repeat counter is equal or less than zero: %d",
                    this.times
                ).asString()
            );
        }
        return result;
    }

}
