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
package org.cactoos.iterator;

import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test Case for {@link LengthOf}.
 * @author Nikita Salomatin (nsalomatin@hotmail.com)
 * @version $Id$
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 line)
 */
public final class LengthOfTest {

    @Test
    public void lengthOfWithIntegerValue() throws Exception {
        MatcherAssert.assertThat(
            "Can't calculate length of iterator for integer",
            new LengthOf(
                new ListOf<>(1, 2, 3, 4).iterator()
            ).intValue(),
            Matchers.equalTo(4)
        );
    }

    @Test
    public void lengthOfWithDoubleNumber() throws Exception {
        MatcherAssert.assertThat(
            "Can't calculate length of iterator for double",
            new LengthOf(
                new ListOf<>(1, 2, 3, 4).iterator()
            ).doubleValue(),
            Matchers.equalTo(4.0)
        );
    }

    @Test
    public void lengthOfWithFloatNumber() throws Exception {
        MatcherAssert.assertThat(
            "Can't calculate length of iterator for float",
            new LengthOf(
                new ListOf<>(1, 2, 3, 4).iterator()
            ).floatValue(),
            Matchers.equalTo(4.0f)
        );
    }

    @Test
    public void lengthOfEmptyIterator() throws Exception {
        MatcherAssert.assertThat(
            "Can't calculate length of empty iterator",
            new LengthOf(
                new ListOf<>().iterator()
            ).intValue(),
            Matchers.equalTo(0)
        );
    }
}
