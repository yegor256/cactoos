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

import java.util.ArrayList;
import java.util.List;
import org.cactoos.Scalar;

/**
 * Cached version of a Scalar.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Tim Hinkes (timmeey@timmeey.de)
 * @version $Id$
 * @param <T> Type of result
 * @since 0.3
 */
public final class StickyScalar<T> implements Scalar<T> {

    /**
     * The scalar to cache.
     */
    private final Scalar<T> source;

    /**
     * The list used as a optional value.
     */
    private final List<T> cache;

    /**
     * Ctor.
     * @param src The Scalar to cache
     */
    public StickyScalar(final Scalar<T> src) {
        this.source = src;
        this.cache = new ArrayList<>(1);
    }

    @Override
    public T asValue() throws Exception {
        if (this.cache.isEmpty()) {
            this.cache.add(this.source.asValue());
        }
        return this.cache.get(0);
    }
}
