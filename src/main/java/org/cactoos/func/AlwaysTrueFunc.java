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
 * Func as that is always true.
 *
 * <p>You may want to use this decorator when you need
 * a procedure that returns boolean instead of a function:</p>
 *
 * <pre> List&lt;String&gt; list = new LinkedList&lt;&gt;();
 * new And(
 *   new IterableAsBooleans&lt;String&gt;(
 *     Collections.emptyList(),
 *     new AlwaysTrueFunc&lt;&gt;(list::add)
 *   )
 * ).asValue();
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of input
 * @since 0.2
 */
public final class AlwaysTrueFunc<X> implements Func<X, Boolean> {

    /**
     * Original func.
     */
    private final Func<X, ?> func;

    /**
     * Ctor.
     * @param proc Encapsulated proc
     */
    public AlwaysTrueFunc(final Proc<X> proc) {
        this(new ProcAsFunc<>(proc));
    }

    /**
     * Ctor.
     * @param fnc Encapsulated func
     */
    public AlwaysTrueFunc(final Func<X, ?> fnc) {
        this.func = fnc;
    }

    @Override
    public Boolean apply(final X input) throws Exception {
        this.func.apply(input);
        return true;
    }

}
