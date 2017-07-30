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

import org.cactoos.ScalarHasValue;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link NaturalNumbers}.
 *
 * @author Tim Hinkes (timmeey@timmeey.de)
 * @version $Id$
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class NaturalNumbersTest {

    @Test
    public void containsSequentialNaturalNumbers() {
        MatcherAssert.assertThat(
            "Can't get sequential natural numbers",
            new NaturalNumbers(),
            // @checkstyle MagicNumber (1 line)
            Matchers.hasItems(0L, 1L, 5L, 10L)
        );
    }

    @Test
    public void notStartsWithNegativeNumbers() {
        MatcherAssert.assertThat(
            "Contains negative Numbers",
            new ItemAt<Long>(new NaturalNumbers(), 0),
            new ScalarHasValue<>(0L)
        );
    }

    @Test
    public void containsNaturalNumbersRange() {
        MatcherAssert.assertThat(
            "Can't get range of natural numbers",
            // @checkstyle MagicNumber (2 lines)
            new NaturalNumbers(() -> 10L, () -> 15L),
            Matchers.hasItems(10L, 12L, 14L)
        );
    }

    @Test
    public void containsNaturalNumbersRangeWithoutLast() {
        MatcherAssert.assertThat(
            "Can't get range of natural numbers without last",
            // @checkstyle MagicNumber (2 lines)
            new NaturalNumbers(() -> 5L),
            Matchers.hasItems(0L, 1L, 2L, 3L, 4L)
        );
    }

}
