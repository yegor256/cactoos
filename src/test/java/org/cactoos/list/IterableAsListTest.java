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
package org.cactoos.list;

import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link IterableAsList}.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class IterableAsListTest {

    @Test
    public void elementAtIndexTest() throws Exception {
        final int num = 345;
        MatcherAssert.assertThat(
            "Can't convert an iterable to a list",
            new IterableAsList<>(-1, num, 0, 1).get(1),
            Matchers.equalTo(num)
        );
    }

    @Test
    public void sizeTest() throws Exception {
        final int size = 42;
        MatcherAssert.assertThat(
            "Can't build a list with a certain size",
            new IterableAsList<>(
                Collections.nCopies(size, 0)
            ),
            Matchers.hasSize(size)
        );
    }

    @Test
    public void emptyTest() throws Exception {
        MatcherAssert.assertThat(
            "Can't convert an empty iterable to an empty list",
            new IterableAsList<>(
                Collections.emptyList()
            ).size(),
            Matchers.equalTo(0)
        );
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void lowBoundTest() throws Exception {
        // @checkstyle MagicNumber (1 line)
        new IterableAsList<>(Collections.nCopies(10, 0)).get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void highBoundTest() throws Exception {
        // @checkstyle MagicNumber (1 line)
        new IterableAsList<>(Collections.nCopies(10, 0)).get(11);
    }
}
