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
package org.cactoos.map;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link MapEntry}.
 *
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class MapEntryTest {

    @Test
    public void getKey() {
        final String key = "hello";
        final String value = "world";
        new Assertion<>(
            "Can't get key in the map entry",
            new MapEntry<>(key, value).getKey(),
            new IsEqual<String>(key)
        ).affirm();
    }

    @Test
    public void getValue() {
        final String key = "foo";
        final String value = "bar";
        new Assertion<>(
            "Can't get value in the map entry",
            new MapEntry<>(key, value).getValue(),
            new IsEqual<String>(value)
        ).affirm();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cantSetValue() {
        new MapEntry<>("one", "two").setValue("three");
    }

    @Test
    public void equalsTo() {
        final String key = "eo";
        final String value = "book";
        new Assertion<>(
            "MapEntries are not equals",
            new MapEntry<>(key, value).equals(new MapEntry<>(key, value)),
            new IsEqual<Boolean>(true)
        ).affirm();
    }

    @Test
    public void compareHash() {
        new Assertion<>(
            "the hash code are not equals",
            new MapEntry<>("elegant", "objects").hashCode(),
            // @checkstyle MagicNumber (1 line)
            new IsEqual<>(32_739_498)
        ).affirm();
    }

    @Test
    public void toStringMethod() {
        new Assertion<>(
            "ToString method returns unexpected value",
            new MapEntry<>("somekey", "somevalue").toString(),
            new IsEqual<String>("somekey=somevalue")
        ).affirm();
    }
}
