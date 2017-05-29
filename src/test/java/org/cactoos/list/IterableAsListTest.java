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
 */
public final class IterableAsListTest {

    /**
     * Test element by index.
     *
     * @throws Exception if failed
     */
    @Test
    public void elementAtIndexTest() throws Exception {
        final int num = 345;
        MatcherAssert.assertThat(
            new IterableAsList<>(
                // @checkstyle MagicNumber (2 lines)
                new ArrayAsIterable<>(0, 1, 2, num, 3, 4)
            ).get(3),
            Matchers.equalTo(num)
        );
    }

    /**
     * Test size of list.
     *
     * @throws Exception if failed
     */
    @Test
    public void sizeTest() throws Exception {
        final int size = 42;
        MatcherAssert.assertThat(
            new IterableAsList<>(
                Collections.nCopies(size, 0)
            ),
            Matchers.hasSize(size)
        );
    }

    /**
     * Test empty IterableAsList.
     *
     * @throws Exception if failed
     */
    @Test
    public void emptyTest() throws Exception {
        MatcherAssert.assertThat(
            new IterableAsList<>(
                Collections.emptyList()
            ).size(),
            Matchers.equalTo(0)
        );
    }

    /**
     * Test low bound of list.
     *
     * @throws Exception if failed
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void lowBoundTest() throws Exception {
        // @checkstyle MagicNumber (1 line)
        new IterableAsList<>(Collections.nCopies(10, 0)).get(-1);
    }

    /**
     * Test high bound of list.
     *
     * @throws Exception if failed
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void highBoundTest() throws Exception {
        // @checkstyle MagicNumber (1 line)
        new IterableAsList<>(Collections.nCopies(10, 0)).get(11);
    }
}
