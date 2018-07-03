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

import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Proc that will try a few times before throwing an exception.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of input
 * @since 0.36
 */
public final class RetryProc<X> implements Proc<X> {

    /**
     * Original proc.
     */
    private final Proc<X> proc;

    /**
     * Exit condition.
     */
    private final Func<Integer, Boolean> exit;

    /**
     * Ctor.
     * @param prc Proc original
     * @since 0.12
     */
    public RetryProc(final Proc<X> prc) {
        // @checkstyle MagicNumberCheck (1 line)
        this(prc, 3);
    }

    /**
     * Ctor.
     * @param prc Proc original
     * @param attempts Maximum number of attempts
     * @since 0.12
     */
    public RetryProc(final Proc<X> prc, final int attempts) {
        this(prc, attempt -> attempt >= attempts);
    }

    /**
     * Ctor.
     * @param prc Proc original
     * @param ext Exit condition, returns TRUE if there is no more reason to try
     * @since 0.12
     */
    public RetryProc(final Proc<X> prc, final Func<Integer, Boolean> ext) {
        this.proc = prc;
        this.exit = ext;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void exec(final X input) throws Exception {
        int attempt = 0;
        Exception error = new IllegalArgumentException(
            "An immediate exit, didn't have a chance to try at least once"
        );
        while (!this.exit.apply(attempt)) {
            try {
                this.proc.exec(input);
                return;
            } catch (final InterruptedException ex) {
                Thread.currentThread().interrupt();
                error = ex;
                break;
                // @checkstyle IllegalCatchCheck (1 line)
            } catch (final Exception ex) {
                error = ex;
            }
            ++attempt;
        }
        throw error;
    }
}
