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

import java.util.Iterator;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.iterator.IteratorOf;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Array as iterable.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @param <X> Type of item
 * @since 0.12
 */
public final class IterableOf<X> extends IterableEnvelope<X> {

    /**
     * Ctor.
     * @param items The array
     */
    @SafeVarargs
    public IterableOf(final X... items) {
        this(() -> new IteratorOf<>(items));
    }

    /**
     * Ctor.
     * @param list The list
     */
    public IterableOf(final List<X> list) {
        this(list::iterator);
    }

    /**
     * Ctor.
     * @param list The list
     * @since 0.21
     */
    public IterableOf(final Iterator<X> list) {
        this(() -> list);
    }

    /**
     * Ctor.
     * @param sclr The encapsulated iterator of x
     */
    private IterableOf(final Scalar<Iterator<X>> sclr) {
        super(() -> () -> new UncheckedScalar<>(sclr).value());
    }

}
