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

import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.UncheckedScalar;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Character array as iterable.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ashton Hogan (info@ashtonhogan.com)
 * @version $Id$
 * @param <Character> Character type item
 * @since 0.12
 */
public final class IterableOfChars<Character> extends IterableEnvelope<Character> {

    /**
     * Ctor.
     * @param items The array
     */
    @SafeVarargs
    public IterableOfChars(final Character... items) {
        this(() -> new ListOf<>(items).iterator());
    }

    /**
     * Ctor.
     * @param list The list
     */
    public IterableOfChars(final List<Character> list) {
        this(list::iterator);
    }

    /**
     * Ctor.
     * @param list The list
     * @since 0.21
     */
    public IterableOfChars(final Iterator<Character> list) {
        this(() -> list);
    }

    /**
     * Ctor.
     * @param sclr The encapsulated iterator of {@Code Character}
     */
    private IterableOfChars(final Scalar<Iterator<Character>> sclr) {
        super(() -> () -> new UncheckedScalar<>(sclr).value());
    }

}
