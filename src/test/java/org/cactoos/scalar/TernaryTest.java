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

import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link Ternary}.
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
final class TernaryTest {

    @Test
    void conditionTrueScalar() throws Exception {
        new Assertion<>(
            "Must work with true scalar condition",
            new Ternary<>(
                new True(),
                6,
                16
            ),
            new ScalarHasValue<>(6)
        ).affirm();
    }

    @Test
    void conditionFalseScalar() throws Exception {
        new Assertion<>(
            "Must work with false scalar condition",
            new Ternary<>(
                new False(),
                6,
                16
            ),
            new ScalarHasValue<>(16)
        ).affirm();
    }

    @Test
    void conditionStatic() throws Exception {
        new Assertion<>(
            "Must work with primitive static condition",
            new Ternary<>(
                true,
                6,
                16
            ),
            new ScalarHasValue<>(6)
        ).affirm();
    }

    @Test
    void consequentScalar() throws Exception {
        new Assertion<>(
            "Must work with scalar consequent and alternative",
            new Ternary<>(
                true,
                new Constant<>(6),
                new Constant<>(16)
            ),
            new ScalarHasValue<>(6)
        ).affirm();
    }

    @Test
    void inputStatic() throws Exception {
        new Assertion<>(
            "Must call the functions with the input",
            new Ternary<>(
                5,
                input -> input > 3,
                input -> input + 1,
                input -> input + 2
            ),
            new ScalarHasValue<>(6)
        ).affirm();
    }

    @Test
    void inputScalar() throws Exception {
        new Assertion<>(
            "Must call the functions with the input scalar value",
            new Ternary<>(
                new Constant<>(5),
                (Integer input) -> input > 3,
                input -> input + 1,
                input -> input + 2
            ),
            new ScalarHasValue<>(6)
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void inputScalarValueConserved() throws Exception {
        new Assertion<>(
            "Must conserve the same scalar value for each whole evaluation",
            new Ternary<>(
                new ScalarOf<>(new AtomicInteger(0)::incrementAndGet),
                (Integer i) -> i == 1,
                i -> new FormattedText("%d equals 1", i),
                i -> new FormattedText("else: %d", i)
            ),
            new AllOf<>(
                new IterableOf<>(
                    new ScalarHasValue<>(new TextOf("1 equals 1")),
                    new ScalarHasValue<>(new TextOf("else: 2")),
                    new ScalarHasValue<>(new TextOf("else: 3"))
                )
            )
        ).affirm();
    }
}
