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
package org.cactoos.scalar;

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.func.FuncOf;

/**
 * Validates the given {@code origin} against the specified {@code rule}.
 * If the validation fails, the {@code exception} is thrown.
 *
 * @param <T> Element type.
 *
 * @since 0.56
 */
public final class Strict<T> extends ScalarEnvelope<T> {

    /**
     * Ctor.
     *
     * @param origin The value to test againts the {@code rule}.
     * @param rule The rule, that validates {@code origin}.
     * @param exception The exception, that is thrown if validation fails.
     */
    public Strict(
        final Scalar<T> origin,
        final Func<Scalar<T>, Scalar<Boolean>> rule,
        final Func<Scalar<T>, Exception> exception
    ) {
        super(
            new Mapped<>(
                new FuncOf<>(origin),
                new ThrowsOnFalse(
                    new Flattened<>(
                        new ScalarOf<>(rule, origin)
                    ),
                    new ScalarOf<>(exception, origin)
                )
            )
        );
    }
}
