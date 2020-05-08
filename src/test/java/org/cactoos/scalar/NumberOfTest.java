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
package org.cactoos.scalar;

import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link NumberOf}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 lines)
 */
public final class NumberOfTest {

    @Test
    public void parsesFloat() throws IOException {
         new Assertion<>(
            "Must parse float number",
            new NumberOf("1656.894").floatValue(),
            Matchers.equalTo(1656.894F)
        ).affirm();
    }

    @Test(expected = RuntimeException.class)
    public void failsIfTextDoesNotRepresentAFloat() throws IOException {
        new NumberOf("abcfds").floatValue();
    }

    @Test
    public void parsesLong() throws IOException {
         new Assertion<>(
            "Must parse long number",
            new NumberOf("186789235425346").longValue(),
            Matchers.equalTo(186_789_235_425_346L)
        ).affirm();
    }

    @Test(expected = RuntimeException.class)
    public void failsIfTextDoesNotRepresentALong() throws IOException {
        new NumberOf("abcddd").longValue();
    }

    @Test
    public void parsesInteger() throws IOException {
         new Assertion<>(
            "Must parse integer number",
            new NumberOf("1867892354").intValue(),
            Matchers.equalTo(1_867_892_354)
        ).affirm();
    }

    @Test(expected = RuntimeException.class)
    public void failsIfTextDoesNotRepresentAnInt() throws IOException {
        new NumberOf("abc fdsf").intValue();
    }

    @Test
    public void parsesDouble() throws IOException {
         new Assertion<>(
            "Must parse double number",
            new NumberOf("185.65156465123").doubleValue(),
            Matchers.equalTo(185.65_156_465_123)
        ).affirm();
    }

    @Test(expected = RuntimeException.class)
    public void failsIfTextDoesNotRepresentADouble() throws IOException {
        new NumberOf("abfdsc").doubleValue();
    }

    @Test
    public void parsesValueInt() throws IOException {
        new Assertion<>(
            "Must parse into int",
            () -> new NumberOf("185").value().intValue(),
            new ScalarHasValue<>(185)
        ).affirm();
    }
}
