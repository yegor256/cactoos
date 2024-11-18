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
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.iterator.IteratorOf;

/**
 * Split the Text using whitespace as the separator,
 * preserving all tokens, including empty tokens created by adjacent separators.
 *
 * @see String#split(String)
 * @see String#split(String, int)
 * @since 0.9
 */
public final class SplitPreserveAllTokens extends IterableEnvelope<Text> {

    /**
     * Ctor.
     *
     * @param text The CharSequence to be split into tokens
     */
    public SplitPreserveAllTokens(final CharSequence text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     *
     * @param text The Text to be split into tokens
     */
    public SplitPreserveAllTokens(final Text text) {
        super(
            new Mapped<>(
                TextOf::new,
                new IterableOf<>(
                    () -> new IteratorOf<>(text.asString().split(" ", -1))
                )
            )
        );
    }

}
