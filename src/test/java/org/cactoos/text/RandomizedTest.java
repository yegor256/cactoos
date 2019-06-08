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
package org.cactoos.text;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.TextHasString;

/**
 * Test for {@link Randomized}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class RandomizedTest {

    @Test
    public void generatesRandomTextOfRandomLength() {
        new Assertion<>(
            "Generated text is empty",
            new Randomized().asString().length(),
            Matchers.greaterThan(0)
        ).affirm();
    }

    @Test
    public void generatesRandomTextOfSpecifiedLength() {
        new Assertion<>(
            "Generated text has incorrect length",
            new Randomized(512).asString().length(),
            new IsEqual<>(512)
        ).affirm();
    }

    @Test
    public void generatesRandomTextOfSpecifiedChars() {
        new Assertion<>(
            "Generated text contains not allowed characters",
            new Randomized('a')
                .asString()
                .replaceAll("a", "")
                .length(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void generatesRandomTextOfSpecifiedCharsAndLength() {
        new Assertion<>(
            "Generated text doesn't match specification",
            new Randomized(10, 'a'),
            new TextHasString("aaaaaaaaaa")
        ).affirm();
    }
}
