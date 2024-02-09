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
package org.cactoos.scalar;

import org.cactoos.io.InputOf;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasProperty;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link PropertiesOf}.
 *
 * @since 0.7
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class PropertiesOfTest {

    @Test
    void readsStringContent() {
        new Assertion<>(
            "Must read properties from an input string",
            new PropertiesOf(
                "foo=Hello, world!\nbar=works fine?\n"
            ),
            new HasValue<>(
                new HasProperty("foo", "Hello, world!")
            )
        ).affirm();
    }

    @Test
    void readsInputContent() {
        new Assertion<>(
            "Must read properties from an input",
            new PropertiesOf(
                new InputOf("greet=Hello, inner world!\nask=works fine?\n")
            ),
            new HasValue<>(
                new HasProperty("greet", "Hello, inner world!")
            )
        ).affirm();
    }

    @Test
    void convertsMapToProperties() {
        new Assertion<>(
            "Must convert map to properties",
            new PropertiesOf(
                new MapOf<Integer, String>(
                    new MapEntry<>(0, "hello, world"),
                    new MapEntry<>(1, "how are you?")
                )
            ),
            new HasValue<>(
                new HasProperty("0", "hello, world")
            )
        ).affirm();
    }

    @Test
    void convertsMapEntriesToProperties() {
        new Assertion<>(
            "Must convert map entries to properties",
            new PropertiesOf(
                new MapEntry<>(0, "hello world"),
                new MapEntry<>(1, "How are you?")
            ),
            new HasValue<>(
                new HasProperty("0", "hello world")
            )
        ).affirm();
    }

}
