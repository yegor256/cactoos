/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link IterableOfShorts}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IterableOfShortsTest {

    @Test
    void convertsShortValuesToIterable() {
        final short[] values = {(short) 1, (short) 2, (short) 3};
        MatcherAssert.assertThat(
            "Must convert short values to iterable",
            new IterableOfShorts(values),
            new HasValues<>(values[0], values[1], values[2])
        );
    }
}
