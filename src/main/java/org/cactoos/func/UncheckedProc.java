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

import org.cactoos.Proc;

/**
 * Proc that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @since 0.2
 * @todo #861:30min Avoid usage of null value in exec(X) which is against design principles.
 * Please take a look on #551 and #843 for more details.
 */
public final class UncheckedProc<X> implements Proc<X> {

    /**
     * Original proc.
     */
    private final Proc<X> proc;

    /**
     * Ctor.
     * @param prc Encapsulated func
     */
    public UncheckedProc(final Proc<X> prc) {
        this.proc = prc;
    }

    @Override
    public void exec(final X input) {
        new UncheckedFunc<>(new FuncOf<>(this.proc, null)).apply(input);
    }

}
