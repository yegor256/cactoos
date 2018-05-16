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
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Constant value.
 *
 * <p>This {@link Scalar} represents a constant value which never changes.</p>
 *
 * <p>Contrary to {@link StickyScalar} this constant is always
 * pre-computed.</p>
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. Despite that this class does NOT throw a checked
 * exception as it only returns a pre-computed value.</p>
 *
 * <p>Example:</p>
 * <pre>{@code
 *     final Scalar<String> constant = new Constant<>("Value");
 *     System.out.print("Constant is always the same: ");
 *     System.out.println(constant.value() == constant.value());
 * }</pre>
 *
 * <p>This class is thread-safe.</p>
 *
 * @param <T> Type of result
 * @see StickyScalar
 * @since 0.30
 */
public final class Constant<T> implements Scalar<T> {

    /**
     * Pre-computed value.
     */
    private final T val;

    /**
     * Ctor.
     * @param value The pre-computed constant.
     */
    public Constant(final T value) {
        this.val = value;
    }

    @Override
    public T value() {
        return this.val;
    }
}
