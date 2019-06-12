/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
package org.cactoos;

import org.cactoos.scalar.IoChecked;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Scalar.
 *
 * <p>If you don't want to have any checked exceptions being thrown
 * out of your {@link Scalar}, you can use
 * {@link Unchecked} decorator. Also
 * you may try {@link IoChecked}.</p>
 *
 * <p>If you want to cache the result of the {@link Scalar} and
 * make sure it doesn't calculate anything twice, you can use
 * {@link Sticky} decorator.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of result
 * @see Sticky
 * @see Unchecked
 * @see IoChecked
 * @since 0.1
 */
public interface Scalar<T> {

    /**
     * Convert it to the value.
     * @return The value
     * @throws Exception If fails
     */
    T value() throws Exception;

}
