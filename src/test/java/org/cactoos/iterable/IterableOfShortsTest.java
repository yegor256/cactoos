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
 * Test case for {@link IterableOfShorts}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IterableOfShortsTest {

    @Test
    @SuppressWarnings("PMD.AvoidUsingShortType")
    void convertsShortValuesToIterable() {
        final short[] values = {(short) 1, (short) 2, (short) 3};
        new Assertion<>(
            "Must convert short values to iterable",
            new IterableOfShorts(values),
            new HasValues<>(values[0], values[1], values[2])
        ).affirm();
    }
}
