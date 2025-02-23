/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link IterableOfDoubles}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IterableOfDoublesTest {

    @Test
    void convertsDoubleValuesToIterable() {
        final double[] values = {1.0, 2.0, 3.0};
        new Assertion<>(
            "Must convert double values to iterable",
            new IterableOfDoubles(values),
            new HasValues<>(values[0], values[1], values[2])
        ).affirm();
    }
}
