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

import org.cactoos.Proc;

/**
 * Runnable with a fallback plan.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.6
 */
public final class RunnableWithFallback implements Runnable {

    /**
     * The runnable.
     */
    private final Runnable runnable;

    /**
     * The fallback.
     */
    private final Proc<Throwable> fallback;

    /**
     * Ctor.
     * @param rnb Runnable
     * @param fbk The fallback
     */
    public RunnableWithFallback(final Runnable rnb,
        final Proc<Throwable> fbk) {
        this.runnable = rnb;
        this.fallback = fbk;
    }

    @Override
    public void run() {
        new UncheckedFunc<>(
            new FuncWithFallback<Boolean, Boolean>(
                new RunnableAsFunc<>(this.runnable),
                new ProcAsFunc<>(this.fallback)
            )
        ).apply(true);
    }

}
