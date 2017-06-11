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

import java.io.IOException;
import org.cactoos.ConstScalar;
import org.cactoos.Scalar;

/**
 * First element in {@link Iterable} or default value if iterable is empty.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @param <T> Scalar type
 * @since 0.4
 */
public final class FirstOrDefault<T> implements Scalar<T> {

    /**
     * First element.
     */
    private final FirstOf<T> first;

    /**
     * Fallback value.
     */
    private final Scalar<T> fallback;

    /**
     * Ctor.
     *
     * @param source Iterable
     * @param fallback Default value
     */
    public FirstOrDefault(final Iterable<T> source, final T fallback) {
        this(source, new ConstScalar<>(fallback));
    }

    /**
     * Ctor.
     *
     * @param source Iterable
     * @param fallback Default value
     */
    public FirstOrDefault(final Iterable<T> source, final Scalar<T> fallback) {
        this(new FirstOf<>(source), fallback);
    }

    /**
     * Ctor.
     *
     * @param first First element
     * @param fallback Default value
     */
    public FirstOrDefault(final FirstOf<T> first, final T fallback) {
        this(first, new ConstScalar<>(fallback));
    }

    /**
     * Ctor.
     *
     * @param first First element
     * @param fallback Default value
     */
    public FirstOrDefault(final FirstOf<T> first, final Scalar<T> fallback) {
        this.first = first;
        this.fallback = fallback;
    }

    @Override
    public T asValue() throws Exception {
        T value;
        try {
            value = this.first.asValue();
        } catch (final IOException ex) {
            value = this.fallback.asValue();
        }
        return value;
    }
}
