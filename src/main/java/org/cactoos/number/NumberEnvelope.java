/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
package org.cactoos.number;

/**
 * Envelope for the {@link Number}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public abstract class NumberEnvelope extends Number {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -8562608838611967858L;

    /**
     * Wrapped Number.
     */
    private final Number wrapped;

    /**
     * Ctor.
     * @param wrapped Number
     */
    public NumberEnvelope(final Number wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public final int intValue() {
        return this.wrapped.intValue();
    }

    @Override
    public final long longValue() {
        return this.wrapped.longValue();
    }

    @Override
    public final float floatValue() {
        return this.wrapped.floatValue();
    }

    @Override
    public final double doubleValue() {
        return this.wrapped.doubleValue();
    }

    @Override
    public final short shortValue() {
        return this.wrapped.shortValue();
    }

    @Override
    public final byte byteValue() {
        return this.wrapped.byteValue();
    }

    @Override
    public final boolean equals(final Object obj) {
        return this.wrapped.equals(obj);
    }

    @Override
    public final int hashCode() {
        return this.wrapped.hashCode();
    }

    @Override
    public final String toString() {
        return this.wrapped.toString();
    }
}
