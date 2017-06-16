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

import org.cactoos.Func;
import org.cactoos.Scalar;

/**
 * Iterable into boolean.
 *
 * <p>You can use this class, for example, in order to iterate through
 * a collection of items:</p>
 *
 * <pre> new IterableAsBoolean&lt;&gt;(
 *   new IterableAsList&lt;String&gt;("hello", "world"),
 *   i -&gt; System.out.println(i)
 * ).asValue();</pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <X> Type of source item
 * @see IterableAsBooleans
 * @since 0.1
 */
public final class IterableAsBoolean<X> implements Scalar<Boolean> {

    /**
     * Iterable.
     */
    private final Iterable<X> iterable;

    /**
     * Proc.
     */
    private final Func<X, Boolean> func;

    /**
     * Ctor.
     * @param src Source iterable
     * @param fnc Func
     */
    public IterableAsBoolean(final Iterable<X> src,
        final Func<X, Boolean> fnc) {
        this.iterable = src;
        this.func = fnc;
    }

    @Override
    public Boolean asValue() {
        return new AllOf(
            new IterableAsBooleans<>(
                this.iterable,
                this.func
            )
        ).asValue();
    }
}
