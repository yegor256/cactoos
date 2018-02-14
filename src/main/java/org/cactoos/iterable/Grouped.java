/**
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

import org.cactoos.Func;

/**
 * Grouped iterable.
 *
 * <p>You can use it in order to create a declarative/lazy
 * version of an iterable of grouped collections/iterables.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @param <X> Type of item
 * @since 1.0
 */
public final class Grouped<X> extends IterableEnvelope<Iterable<X>> {
    /**
     * Ctor.
     * @param origin The iterable the grouping is based on.
     * @param funcs The iterable containing grouping functions.
     */
    public Grouped(final Iterable<X> origin,
        final Iterable<Func<X, Boolean>> funcs) {
        super(() -> new Mapped<>(func -> new Filtered<>(func, origin), funcs));
    }

}
