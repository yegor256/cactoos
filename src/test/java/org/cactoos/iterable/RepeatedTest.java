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
package org.cactoos.iterable;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Repeated}.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 lines)
 */
public final class RepeatedTest {

    @Test
    public void allSameTest() throws Exception {
        final int size = 42;
        final int element = 11;
        MatcherAssert.assertThat(
            "Can't generate an iterable with fixed size",
            new LengthOf(
                new Filtered<>(
                    input -> input == element,
                    new Repeated<>(size, element)
                )
            ).intValue(),
            Matchers.equalTo(size)
        );
    }

    @Test
    public void emptyTest() throws Exception {
        MatcherAssert.assertThat(
            "Can't generate an empty iterable",
            new LengthOf(new Repeated<>(0, 0)).intValue(),
            Matchers.equalTo(0)
        );
    }

    @Test
    public void repeatsIntegerTwice() throws Exception {
        final Iterable<Integer> list = new Repeated<>(5, 1);
        MatcherAssert.assertThat(
            "Can't repeat an integer",
            list, Matchers.iterableWithSize(5)
        );
        MatcherAssert.assertThat(
            "Can't repeat an integer, again",
            list, Matchers.iterableWithSize(5)
        );
    }
}
