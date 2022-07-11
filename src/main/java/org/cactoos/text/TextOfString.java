/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.Unchecked;

/**
 * Text of {@link String}
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class TextOfString implements Text {

    /**
     * Input text.
     */
    private final String input;

    /**
     * Ctor.
     *
     * @param input The String
     */
    public TextOfString(final String input) {
        this.input = input;
    }

    @Override
    public String asString() throws Exception {
        return this.input;
    }

    @Override
    public String toString() {
        return this.input;
    }

    @Override
    public int hashCode() {
        return this.input.hashCode();
    }

    @Override
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EQ_UNUSUAL")
    public boolean equals(final Object obj) {
        return new Unchecked<>(
            new Or(
                () -> this == obj,
                new And(
                    () -> obj instanceof Text,
                    () -> this.input.equals(new UncheckedText((Text) obj).asString())
                )
            )
        ).value();
    }
}
