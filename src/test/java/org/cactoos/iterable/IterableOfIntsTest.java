/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link IterableOfInts}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IterableOfIntsTest {

    @Test
    void convertsIntegerValuesToIterable() {
        final int[] values = {1, 2, 3};
        new Assertion<>(
            "Must convert integer values to iterable",
            new IterableOfInts(values),
            new HasValues<>(values[0], values[1], values[2])
        ).affirm();
    }
}
