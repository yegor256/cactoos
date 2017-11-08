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

import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link SumOf}.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class SumOfTest {

    @Test
    public void withListOfNumbers() throws Exception {
        MatcherAssert.assertThat(
            new SumOf(1, 2, 3).intValue(),
            Matchers.equalTo(6)
        );
        MatcherAssert.assertThat(
            new SumOf(1.0d, 2.0d, 3.0d).doubleValue(),
            Matchers.equalTo(6.0d)
        );
        MatcherAssert.assertThat(
            new SumOf(1.0f, 2.0f, 3.0f).floatValue(),
            Matchers.equalTo(6.0f)
        );
        MatcherAssert.assertThat(
            new SumOf(1L, 2L, 3L).longValue(),
            Matchers.equalTo(6L)
        );
    }

    @Test
    public void withVarargsCtor() throws Exception {
        MatcherAssert.assertThat(
            new SumOf(
                () -> 1,
                () -> 2,
                () -> 3
            ).longValue(),
            Matchers.equalTo(6L)
        );
    }

    @Test
    public void withIterCtor() throws Exception {
        MatcherAssert.assertThat(
            new SumOf(
                new IterableOf<Scalar<Number>>(
                    () -> 7,
                    () -> 8,
                    () -> 10
                )
            ).longValue(),
            Matchers.equalTo(25L)
        );
    }
}
