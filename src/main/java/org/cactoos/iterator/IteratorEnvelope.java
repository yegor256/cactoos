/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * {@link Iterator} that delegates to another {@link Iterator}.
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <X> Type of item
 * @since 0.43
 */
public abstract class IteratorEnvelope<X> implements Iterator<X> {

    /**
     * Wrapped {@link Iterator}.
     */
    private final Iterator<? extends X> wrapped;

    /**
     * Ctor.
     * @param iter The {@link Iterator} to wrap.
     */
    public IteratorEnvelope(final Iterator<? extends X> iter) {
        this.wrapped = iter;
    }

    @Override
    public final boolean hasNext() {
        return this.wrapped.hasNext();
    }

    @Override
    public final X next() {
        return this.wrapped.next();
    }

    @Override
    public final void forEachRemaining(final Consumer<? super X> action) {
        this.wrapped.forEachRemaining(action);
    }

    @Override
    public final void remove() {
        this.wrapped.remove();
    }

    @Override
    public final String toString() {
        return this.wrapped.toString();
    }

    @Override
    public final boolean equals(final Object obj) {
        return this.wrapped.equals(obj);
    }

    @Override
    public final int hashCode() {
        return this.wrapped.hashCode();
    }
}
