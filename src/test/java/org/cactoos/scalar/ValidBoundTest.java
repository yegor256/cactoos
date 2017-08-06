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
package org.cactoos.scalar;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link ValidBound}.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.13.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ValidBoundTest {

    @Test
    public void greaterThan() throws Exception {
        final Integer left = 1;
        MatcherAssert.assertThat(
            // @checkstyle MagicNumberCheck (1 line)
            new ValidBound<>(left, ">", 0).value(),
            Matchers.equalTo(left)
        );
    }

    @Test
    public void atLeast() throws Exception {
        final Integer left = 0;
        MatcherAssert.assertThat(
            // @checkstyle MagicNumberCheck (1 line)
            new ValidBound<>(left, ">=", 0).value(),
            Matchers.equalTo(left)
        );
    }

    @Test
    public void lessThan() throws Exception {
        final Integer right = 9;
        MatcherAssert.assertThat(
            // @checkstyle MagicNumberCheck (1 line)
            new ValidBound<>(right, "<", 10).value(),
            Matchers.equalTo(right)
        );
    }

    @Test
    public void atMost() throws Exception {
        final Integer right = 10;
        MatcherAssert.assertThat(
            // @checkstyle MagicNumberCheck (1 line)
            new ValidBound<>(right, "<=", 10).value(),
            Matchers.equalTo(right)
        );
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void outOfBound() throws Exception {
        final Integer right = 11;
        MatcherAssert.assertThat(
            // @checkstyle MagicNumberCheck (1 line)
            new ValidBound<>(right, "<=", 10).value(),
            Matchers.equalTo(right)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidSymbol() throws Exception {
        final Integer right = 10;
        MatcherAssert.assertThat(
            // @checkstyle MagicNumberCheck (1 line)
            new ValidBound<>(right, "]", 10).value(),
            Matchers.equalTo(right)
        );
    }

}
