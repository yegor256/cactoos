/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
package org.cactoos.iterable;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link MapEntry}.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class MapEntryTest {

    @Test
    public void getKey() {
        final String key = "hello";
        final String value = "world";
        MatcherAssert.assertThat(
            "Can't get key in the map entry",
            new MapEntry<>(key, value).getKey(),
            Matchers.equalTo(key)
        );
    }

    @Test
    public void getValue() {
        final String key = "foo";
        final String value = "bar";
        MatcherAssert.assertThat(
            "Can't get value in the map entry",
            new MapEntry<>(key, value).getValue(),
            Matchers.equalTo(value)
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cantSetValue() {
        new MapEntry<>("one", "two").setValue("three");
    }

    @Test
    public void equalsTo() {
        final String key = "eo";
        final String value = "book";
        MatcherAssert.assertThat(
            "MapEntries are not equals",
            new MapEntry<>(key, value).equals(new MapEntry<>(key, value)),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void compareHash() {
        MatcherAssert.assertThat(
            "the hash code are not equals",
            new MapEntry<>("elegant", "objects").hashCode(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(32739498)
        );
    }

}
