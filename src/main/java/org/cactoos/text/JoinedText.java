/**
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

import java.io.IOException;
import java.util.StringJoiner;
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;

/**
 * Join a Text.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.9
 */
public final class JoinedText implements Text {

    /**
     * The texts.
     */
    private final Iterable<Text> texts;

    /**
     * The delimiter.
     */
    private final Text delimiter;

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
            new Mapped<>(
                text -> new TextOf(text), strs
            )
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
        this.delimiter = delimit;
        this.texts = txts;
    }

    @Override
    public String asString() throws IOException {
        final StringJoiner joint = new StringJoiner(this.delimiter.asString());
        for (final Text text : this.texts) {
            joint.add(text.asString());
        }
        return joint.toString();
    }

}
