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
import java.util.Iterator;
import org.cactoos.Scalar;

/**
 * First element in {@link Iterable} or fallback value if iterable is empty.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @param <T> Scalar type
 * @since 0.1
 */
public final class FirstOf<T> implements Scalar<T> {

    /**
     * Source iterable.
     */
    private final Iterable<T> src;

    /**
     * Fallback value.
     */
    private final Scalar<T> fbk;

    /**
     * Ctor.
     *
     * @param src Iterable
     */
    public FirstOf(final Iterable<T> src) {
        this(
            src,
            () -> {
                throw new IOException("Iterable is empty");
            }
        );
    }

    /**
     * Ctor.
     *
     * @param src Iterable
     * @param fbk Fallback value
     */
    public FirstOf(final Iterable<T> src, final T fbk) {
        this(src, () -> fbk);
    }

    /**
     * Ctor.
     *
     * @param src Iterable
     * @param fbk Fallback value
     */
    public FirstOf(final Iterable<T> src, final Scalar<T> fbk) {
        this.src = src;
        this.fbk = fbk;
    }

    @Override
    public T asValue() throws Exception {
        final Iterator<T> itr = this.src.iterator();
        final T ret;
        if (itr.hasNext()) {
            ret = itr.next();
        } else {
            ret = this.fbk.asValue();
        }
        return ret;
    }
}
