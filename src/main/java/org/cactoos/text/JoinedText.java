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
package org.cactoos.text;

import java.util.StringJoiner;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Join a Text.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.9
 * @todo #849:30min Continue refactoring all classes implementing Text to extend
 *  TextEnvelope - in most cases asString should be removed and implementation
 *  from TextEnvelope should be used.
 */
public final class JoinedText extends TextEnvelope {

    /**
     * Ctor.
     * @param delimit Delimit among strings
     * @param strs Strings to be joined
     */
    public JoinedText(final String delimit, final String... strs) {
        this(delimit, new IterableOf<>(strs));
    }

    /**
     * Ctor.
     * @param delimit Delimit among strings
     * @param strs Strings to be joined
     */
    public JoinedText(final String delimit, final Iterable<String> strs) {
        this(
            new TextOf(delimit),
            new Mapped<>(TextOf::new, strs)
        );
    }

    /**
     * Ctor.
     * @param delimit Delimit among texts
     * @param txts Texts to be joined
     */
    public JoinedText(final Text delimit, final Text... txts) {
        this(delimit, new IterableOf<>(txts));
    }

    /**
     * Ctor.
     * @param delimit Delimit among texts
     * @param txts Texts to be joined
     */
    public JoinedText(final Text delimit, final Iterable<Text> txts) {
        super((Scalar<String>) () -> {
            final StringJoiner joint =
                new StringJoiner(delimit.asString());
            for (final Text text : txts) {
                joint.add(text.asString());
            }
            return joint.toString();
        });
    }

}
