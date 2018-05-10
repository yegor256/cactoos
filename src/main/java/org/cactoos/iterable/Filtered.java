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
package org.cactoos.iterable;

import java.util.Iterator;
import org.cactoos.Func;

/**
 * Filtered iterable.
 *
 * <p>You can use it in order to create a declarative/lazy
 * version of a filtered collection/iterable. For example,
 * this code will create a list of two strings "hello" and "world":</p>
 *
 * <pre> Iterable&lt;String&gt; list = new Filtered&lt;&gt;(
 *   new ArrayOf&lt;&gt;(
 *     "hey", "hello", "world"
 *   ),
 *   input -&gt; input.length() &gt; 4
 * );
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of item
 * @see Filtered
 * @since 0.1
 */
public final class Filtered<X> extends IterableEnvelope<X> {

    /**
     * Ctor.
     * @param fnc Predicate
     * @param src Source iterable
     * @since 0.21
     */
    @SafeVarargs
    public Filtered(final Func<X, Boolean> fnc, final X... src) {
        this(fnc, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param fnc Predicate
     * @param src Source iterable
     * @since 0.21
     */
    public Filtered(final Func<X, Boolean> fnc, final Iterator<X> src) {
        this(fnc, new IterableOf<>(src));
    }

    /**
     * Ctor.
     * @param fnc Predicate
     * @param src Source iterable
     */
    public Filtered(final Func<X, Boolean> fnc, final Iterable<X> src) {
        super(() -> () -> new org.cactoos.iterator.Filtered<>(
            fnc, src.iterator()
        ));
    }

}
