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
package org.cactoos.list;

import org.cactoos.Scalar;

/**
 * Is {@code true} when all items in the collection are {@code true}.
 *
 * <p>You can use this class in order to iterate all items
 * in the collection. This is very similar to the {@code forEach()}
 * in steams, but more object oriented. For example, if you want
 * to print all items from the array:</p>
 *
 * <pre> new AllOf(
 *   new TransformedIterable&lt;String&gt;(
 *     new IterableAsList&lt;String&gt;("hello", "world"),
 *     new ProcAsFunc&lt;&gt;(i -&gt; System.out.println(i))
 *   )
 * ).asValue();</pre>
 *
 * <p>Or even more compact, through {@link IterableAsBooleans}:</p>
 *
 * <pre> new AllOf(
 *   new IterableAsBooleans&lt;String&gt;(
 *     new IterableAsList&lt;String&gt;("hello", "world"),
 *     i -&gt; System.out.println(i)
 *   )
 * ).asValue();</pre>
 *
 * <p>Or you can even use {@link IterableAsBoolean}:</p>
 *
 * <pre> new IterableAsBoolean&lt;String&gt;(
 *   new IterableAsList&lt;String&gt;("hello", "world"),
 *   i -&gt; System.out.println(i)
 * ).asValue();</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class AllOf implements Scalar<Boolean> {

    /**
     * Iterable.
     */
    private final Iterable<Boolean> iterable;

    /**
     * Ctor.
     * @param src Source iterable
     */
    public AllOf(final Iterable<Boolean> src) {
        this.iterable = src;
    }

    @Override
    public Boolean asValue() {
        boolean success = true;
        for (final Boolean item : this.iterable) {
            if (!item) {
                success = false;
                break;
            }
        }
        return success;
    }

}
