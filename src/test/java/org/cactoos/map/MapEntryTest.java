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
package org.cactoos.map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link MapEntry}.
 *
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class MapEntryTest {

    @Test
    void getKey() {
        final String key = "hello";
        final String value = "world";
        MatcherAssert.assertThat(
            "Can't get key in the map entry",
            new MapEntry<>(key, value).getKey(),
            new IsEqual<>(key)
        );
    }

    @Test
    void getValue() {
        final String key = "foo";
        final String value = "bar";
        MatcherAssert.assertThat(
            "Can't get value in the map entry",
            new MapEntry<>(key, value).getValue(),
            new IsEqual<>(value)
        );
    }

    @Test
    void cantSetValue() {
        new Assertion<>(
            "Exception is expected on change operations",
            () -> new MapEntry<>("one", "two").setValue("three"),
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void equalsTo() {
        final String key = "eo";
        final String value = "book";
        MatcherAssert.assertThat(
            "MapEntries are not equals",
            new MapEntry<>(key, value).equals(new MapEntry<>(key, value)),
            new IsEqual<>(true)
        );
    }

    @Test
    void compareHash() {
        MatcherAssert.assertThat(
            "the hash code are not equals",
            new MapEntry<>("elegant", "objects").hashCode(),
            new IsEqual<>(-1_673_632_025)
        );
    }

    @Test
    void toStringMethod() {
        MatcherAssert.assertThat(
            "ToString method returns unexpected value",
            new MapEntry<>("somekey", "somevalue").toString(),
            new IsEqual<>("somekey=somevalue")
        );
    }
}
