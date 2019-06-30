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
package org.cactoos.scalar;

import java.io.IOException;
import org.cactoos.Func;
import org.cactoos.Scalar;

/**
 * {@link Scalar} that apply a {@link Func} to the result
 * of another {@link Scalar}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link IOException}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator.</p>
 *
 * @param <T> Type of wrapped scalar result
 * @param <U> Type of result
 * @since 1.0.0
 */
public final class Mapped<T, U> implements Scalar<U> {

    /**
     * Original scalar.
     */
    private final Scalar<T> origin;

    /**
     * Func.
     */
    private final Func<T, U> func;

    /**
     * Ctor.
     * @param mapping Fun to apply
     * @param scalar Encapsulated scalar
     */
    public Mapped(final Func<T, U> mapping, final Scalar<T> scalar) {
        this.origin = scalar;
        this.func = mapping;
    }

    @Override
    public U value() throws Exception {
        return this.func.apply(this.origin.value());
    }

}
