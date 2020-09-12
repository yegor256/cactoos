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

import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link StartsWith}.
 *
 * @since 0.44
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public class StartsWithTest {

    @Test
    void emptyStartsWithEmpty() throws Exception {
        new Assertion<>(
            "Empty is not prefix of empty",
            new StartsWith(
                new TextOf(""),
                new TextOf("")
            ).value(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void textStartsWithEmpty() throws Exception {
        new Assertion<>(
            "Empty is not prefix of any string",
            new StartsWith(
                new TextOf("Any string"),
                new TextOf("")
            ).value(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void textStartsWithPrefix() throws Exception {
        new Assertion<>(
            "Foo is not prefix of FooBar",
            new StartsWith(
                "FooBar",
                "Foo"
            ).value(),
            new IsTrue()
        ).affirm();
    }

    @Test
    void textStartsWithoutPrefix() throws Exception {
        new Assertion<>(
            "Baz is prefix of FooBarBaz",
            new StartsWith(
                "FooBarBaz",
                "Baz"
            ).value(),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

}
