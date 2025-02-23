/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link IterableOfLongs}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IterableOfLongsTest {

    @Test
    void convertsLongValuesToIterable() {
        final long[] values = {1L, 2L, 3L};
        new Assertion<>(
            "Must convert long values to iterable",
            new IterableOfLongs(values),
            new HasValues<>(values[0], values[1], values[2])
        ).affirm();
    }
}
