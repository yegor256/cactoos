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

/**
 * Proc as a Func.
 *
 * <p>Be careful, this function will always return {@code null}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.2
 */
public final class ProcAsFunc<X, Y> implements Func<X, Y> {

    /**
     * The proc.
     */
    private final Proc<X> proc;

    /**
     * The result.
     */
    private final Y result;

    /**
     * Ctor.
     * @param prc The proc
     */
    public ProcAsFunc(final Proc<X> prc) {
        this(prc, null);
    }

    /**
     * Ctor.
     * @param prc The proc
     * @param rslt Result to return
     * @since 0.7
     */
    public ProcAsFunc(final Proc<X> prc, final Y rslt) {
        this.proc = prc;
        this.result = rslt;
    }

    @Override
    public Y apply(final X input) throws Exception {
        this.proc.exec(input);
        return this.result;
    }
}
