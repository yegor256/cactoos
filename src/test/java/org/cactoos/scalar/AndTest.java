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
package org.cactoos.scalar;

import org.cactoos.MatcherAssert;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link And}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 line)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class AndTest {

    @Test
    public void allTrue() throws Exception {
        new Assertion<>(
            "Each object must be True",
            new And(
                new True(),
                new True(),
                new True()
            ),
            new ScalarHasValue<>(true)
        ).affirm();
    }

    @Test
    public void oneFalse() throws Exception {
        new Assertion<>(
            "One object must be False",
            new And(
                new True(),
                new False(),
                new True()
            ),
            new ScalarHasValue<>(false)
        ).affirm();
    }

    @Test
    public void allFalse() throws Exception {
        new Assertion<>(
            "Each object must be False",
            new And(
                new IterableOf<Scalar<Boolean>>(
                    new False(),
                    new False(),
                    new False()
                )
            ),
            new ScalarHasValue<>(false)
        ).affirm();
    }

    @Test
    public void emptyIterator() throws Exception {
        new Assertion<>(
            "Iterator must be empty",
            new And(new IterableOf<Scalar<Boolean>>()),
            new ScalarHasValue<>(true)
        ).affirm();
    }

    @Test
    public void testFuncIterable() throws Exception {
        MatcherAssert.assertThat(
            new And(
                input -> input > 0,
                new IterableOf<>(1, -1, 0)
            ),
            new ScalarHasValue<>(false)
        );
    }

    @Test
    public void testFuncIterator() throws Exception {
        MatcherAssert.assertThat(
            new And(
                input -> input > 0,
                new IterableOf<>(1, -1, 0)
            ),
            new ScalarHasValue<>(false)
        );
    }

    @Test
    public void testFuncVarargs() throws Exception {
        MatcherAssert.assertThat(
            new And(
                input -> input > 0,
                -1, -2, 0
            ),
            new ScalarHasValue<>(false)
        );
    }

    @Test
    public void testMultipleFuncConditionTrue() throws Exception {
        MatcherAssert.assertThat(
            "Can't compare subject with true conditions",
            new And(
                3,
                input -> input > 0,
                input -> input > 5,
                input -> input > 4
            ),
            new ScalarHasValue<>(false)
        );
    }

    @Test
    public void testMultipleFuncConditionFalse() throws Exception {
        MatcherAssert.assertThat(
            "Can't compare subject with false conditions",
            new And(
                "cactoos",
                input -> input.contains("singleton"),
                input -> input.contains("static")
            ),
            new ScalarHasValue<>(false)
        );
    }
}
