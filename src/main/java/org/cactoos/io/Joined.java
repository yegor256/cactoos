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
package org.cactoos.io;

import java.io.InputStream;
import java.io.SequenceInputStream;
import org.cactoos.Input;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.Reduced;

/**
 * Concatenation of several inputs.
 * @since 0.36
 */
public final class Joined implements Input {
    /**
     * The inputs.
     */
    private final Iterable<Input> inputs;

    /**
     * Ctor.
     * @param ipts Iterable of inputs
     */
    public Joined(final Iterable<Input> ipts) {
        this.inputs = ipts;
    }

    /**
     * Ctor.
     * @param first First input
     * @param rest The other inputs
     */
    public Joined(final Input first, final Input... rest) {
        this(
            new org.cactoos.iterable.Joined<>(
                first,
                new IterableOf<>(rest)
            )
        );
    }

    @Override
    public InputStream stream() throws Exception {
        return new Reduced<InputStream>(
            (left, right) -> new SequenceInputStream(left, right),
            new Mapped<>(
                input -> () -> input.stream(),
                this.inputs
            )
        ).value();
    }
}
