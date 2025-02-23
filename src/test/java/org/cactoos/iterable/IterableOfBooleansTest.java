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
 * Test case for {@link IterableOfBooleans}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IterableOfBooleansTest {

    @Test
    void convertsBooleanValuesToIterable() {
        new Assertion<>(
            "Must convert boolean values to iterable",
            new IterableOfBooleans(true, false),
            new HasValues<>(true, false)
        ).affirm();
    }
}
