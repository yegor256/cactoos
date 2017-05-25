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

import java.io.IOException;
import org.cactoos.Func;
import org.cactoos.Proc;

/**
 * Consumer that is a func that returns {@code boolean}.
 *
 * <p>You may need this class when you iterate a collection, for example:</p>
 *
 * <pre> new AllOf(
 *   new TransformedIterable&lt;String&gt;(
 *     Arrays.asList("hello", "world"),
 *     new ProcAsFunc(i -> System.out.println(i))
 *   )
 * ).asValue();</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <T> Type of items
 * @since 0.1
 */
public final class ProcAsFunc<T> implements Func<T, Boolean> {

    /**
     * Proc.
     */
    private final Proc<T> proc;

    /**
     * The result to return.
     */
    private final Boolean result;

    /**
     * Ctor.
     * @param cns Consumer
     */
    public ProcAsFunc(final Proc<T> cns) {
        this(cns, true);
    }

    /**
     * Ctor.
     * @param cns Consumer
     * @param rslt Result to return
     */
    public ProcAsFunc(final Proc<T> cns, final Boolean rslt) {
        this.proc = cns;
        this.result = rslt;
    }

    @Override
    public Boolean apply(final T input) throws IOException {
        this.proc.apply(input);
        return this.result;
    }

}
