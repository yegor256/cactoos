/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link IterableOfBooleans}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IterableOfBooleansTest {

    @Test
    void convertsBooleanValuesToIterable() {
        MatcherAssert.assertThat(
            "Must convert boolean values to iterable",
            new IterableOfBooleans(true, false),
            new HasValues<>(true, false)
        );
    }
}
