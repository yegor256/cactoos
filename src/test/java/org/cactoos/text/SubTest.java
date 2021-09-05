/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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

import org.cactoos.Func;
import org.cactoos.func.FuncOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Sub}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class SubTest {

    @Test
    void acceptsCharSequence() {
        final Func<CharSequence, Integer> half = new FuncOf<>(
            sequence -> sequence.length() / 2
        );
        new Assertion<>(
            "Must cut a text with start",
            new Sub(new TextOf("sequence"), half),
            new IsText("ence")
        ).affirm();
    }

    @Test
    void cutTextWithStartAndEnd() {
        new Assertion<>(
            "Can't cut a text with start and end",
            // @checkstyle MagicNumber (1 line)
            new Sub("hello world", 2, 50),
            new HasString("llo world")
        ).affirm();
    }

    @Test
    void cutTextWithStart() {
        new Assertion<>(
            "Can't cut a text with start",
            new Sub("cut here", 2),
            new HasString("t here")
        ).affirm();
    }

}
