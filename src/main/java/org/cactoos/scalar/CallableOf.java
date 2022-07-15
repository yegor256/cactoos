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
package org.cactoos.scalar;

import java.util.concurrent.Callable;
import org.cactoos.Scalar;

/**
 * Func as {@link Callable}.
 *
 * <p>You may want to use this decorator where
 * {@link Callable} is required, but you just have a function:</p>
 *
 * <pre> Callable&lt;String&gt; callable = new CallableOf&lt;&gt;(
 *   i -&gt; "Hello, world!"
 * );
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of output
 * @since 0.12
 */
public final class CallableOf<T> extends CallableEnvelope<T> {

    /**
     * Ctor.
     * @param slr Encapsulated scalar
     * @since 0.41
     */
    public CallableOf(final Scalar<? extends T> slr) {
        super(slr::value);
    }
}
