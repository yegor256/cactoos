/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.LinkedList;
import java.util.List;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Or}.
 *
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class OrTest {

    @Test
    void allFalse() {
        new Assertion<>(
            "value should be calculated for all false",
            new Or(
                new False(),
                new False(),
                new False(),
                new False(),
                new False()
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void oneTrue() {
        new Assertion<>(
            "value should be calculated for all false except one",
            new Or(
                new False(),
                new True(),
                new False(),
                new False(),
                new False()
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void allTrue() {
        new Assertion<>(
            "value should be calculated for all true",
            new Or(
                new IterableOf<Scalar<Boolean>>(
                    new True(),
                    new True(),
                    new True(),
                    new True(),
                    new True()
                )
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void emptyIterator() {
        new Assertion<>(
            "false should be a result for empty iterable",
            new Or(new IterableOf<Scalar<Boolean>>()),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void testProcIterable() throws Exception {
        final List<Integer> list = new LinkedList<>();
        new Or(
            (Proc<Integer>) list::add,
            new IterableOf<>(1, 2, 3, 4)
        ).value();
        new Assertion<>(
            "proc iterable should collect list",
            list,
            Matchers.contains(1, 2, 3, 4)
        ).affirm();
    }

    @Test
    void testProcVarargs() throws Exception {
        final List<Integer> list = new LinkedList<>();
        new Or(
            (Proc<Integer>) list::add,
            2, 3, 4
        ).value();
        new Assertion<>(
            "proc iterable should collect list from varargs",
            list,
            Matchers.contains(2, 3, 4)
        ).affirm();
    }

    @Test
    void testFuncIterable() {
        new Assertion<>(
            "function should be called for iterable",
            new Or(
                input -> input > 0,
                new IterableOf<>(-1, 1, 0)
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void testFuncVarargs() {
        new Assertion<>(
            "function should be called for varargs",
            new Or(
                input -> input > 0,
                -1, -2, 0
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void testMultipleFuncConditionTrue() {
        new Assertion<>(
            "Can't compare subject with true conditions",
            new Or(
                3,
                input -> input > 0,
                input -> input > 5,
                input -> input > 4
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void testMultipleFuncConditionFalse() {
        new Assertion<>(
            "Can't compare subject with false conditions",
            new Or(
                "cactoos",
                input -> input.contains("singleton"),
                input -> input.contains("static")
            ),
            new HasValue<>(false)
        ).affirm();
    }
}
