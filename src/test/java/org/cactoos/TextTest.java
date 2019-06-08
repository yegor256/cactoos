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

import org.cactoos.text.NoNulls;
import org.cactoos.text.TextOf;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.TextHasString;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Text}.
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class TextTest {

    @Test
    public void failForNullArgument() {
        new Assertion<>(
            "Must fail for null argument",
            () -> new NoNulls(null).asString(),
            new Throws<>(
                "NULL instead of a valid text",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    public void failForNullResult() {
        new Assertion<>(
            "Must fail for null result",
            () -> new NoNulls(() -> null).asString(),
            new Throws<>(
                "NULL instead of a valid result string",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    public void okForNoNulls() {
        final String message = "Hello";
        new Assertion<>(
            "Must work with NoNulls",
            new NoNulls(
                new TextOf(message)
            ),
            new TextHasString(message)
        ).affirm();
    }

}
