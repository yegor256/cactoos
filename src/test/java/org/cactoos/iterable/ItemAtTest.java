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

import java.io.IOException;
import java.util.Collections;
import org.cactoos.ScalarHasValue;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link ItemAt}.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ItemAtTest {

    @Test
    public void firstElementTest() throws Exception {
        MatcherAssert.assertThat(
            "Can't take the first item from the iterable",
            new ItemAt<>(
                // @checkstyle MagicNumber (1 line)
                new IterableOf<>(1, 2, 3)
            ),
            new ScalarHasValue<>(1)
        );
    }

    @Test
    public void elementByPosTest() throws Exception {
        MatcherAssert.assertThat(
            "Can't take the item by position from the iterable",
            new ItemAt<>(
                // @checkstyle MagicNumber (1 line)
                new IterableOf<>(1, 2, 3),
                1
            ),
            new ScalarHasValue<>(2)
        );
    }

    @Test(expected = IOException.class)
    public void failForEmptyCollectionTest() throws Exception {
        new ItemAt<>(Collections.emptyList()).value();
    }

    @Test
    public void fallbackTest() throws Exception {
        final String fallback = "fallback";
        MatcherAssert.assertThat(
            "Can't fallback to default value",
            new ItemAt<>(
                Collections.emptyList(),
                fallback
            ),
            new ScalarHasValue<>(fallback)
        );
    }
}
