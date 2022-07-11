/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
package org.cactoos.proc;

import org.cactoos.BiFunc;
import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Represents many possible inputs as {@link BiProc}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @param <Y> Type of input
 * @since 0.50
 */
public final class BiProcOf<X, Y> extends BiProcEnvelope<X, Y> {

    /**
     * Ctor.
     * @param func The function
     */
    public BiProcOf(final Func<? super X, ?> func) {
        this(x -> {
            func.apply(x);
        });
    }

    /**
     * Ctor.
     * @param prc The procedure
     */
    public BiProcOf(final Proc<? super X> prc) {
        this(
            prc,
            yvl -> {
            }
        );
    }

    /**
     * Ctor.
     * @param first The procedure on first arg.
     * @param second The procedure on second arg.
     */
    public BiProcOf(
        final Proc<? super X> first,
        final Proc<? super Y> second
    ) {
        this(
            (xvl, yvl) -> {
                first.exec(xvl);
                second.exec(yvl);
            }
        );
    }

    /**
     * Ctor.
     * @param func The bi function
     */
    public BiProcOf(final BiFunc<? super X, ? super Y, ?> func) {
        this((first, second) -> {
            func.apply(first, second);
        });
    }

    /**
     * Ctor.
     * @param biprc The bi procedure
     */
    public BiProcOf(final BiProc<? super X, ? super Y> biprc) {
        super(biprc);
    }
}
