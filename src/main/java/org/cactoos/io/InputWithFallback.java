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
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import org.cactoos.Func;
import org.cactoos.Input;
import org.cactoos.func.IoCheckedFunc;

/**
 * Input that returns an alternative input if the main one throws
 * {@link IOException}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.9
 */
public final class InputWithFallback implements Input {

    /**
     * The main one.
     */
    private final Input main;

    /**
     * The alternative one.
     */
    private final IoCheckedFunc<IOException, Input> alternative;

    /**
     * Ctor.
     * @param input Main input
     */
    public InputWithFallback(final Input input) {
        this(input, new DeadInput());
    }

    /**
     * Ctor.
     * @param input Main input
     * @param alt Alternative
     */
    public InputWithFallback(final Input input, final Input alt) {
        this(input, error -> alt);
    }

    /**
     * Ctor.
     * @param input Main input
     * @param alt Alternative
     */
    public InputWithFallback(final Input input,
        final Func<IOException, Input> alt) {
        this(input, new IoCheckedFunc<>(alt));
    }

    /**
     * Ctor.
     * @param input Main input
     * @param alt Alternative
     */
    public InputWithFallback(final Input input,
        final IoCheckedFunc<IOException, Input> alt) {
        this.main = input;
        this.alternative = alt;
    }

    @Override
    public InputStream stream() throws Exception {
        InputStream stream;
        try {
            stream = this.main.stream();
        } catch (final IOException ex) {
            stream = this.alternative.apply(ex).stream();
        }
        return stream;
    }

}
