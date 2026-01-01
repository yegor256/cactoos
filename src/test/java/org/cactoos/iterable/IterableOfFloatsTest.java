/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link IterableOfFloats}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IterableOfFloatsTest {

    @Test
    void convertsFloatValuesToIterable() {
        final float[] values = {1.0F, 2.0F, 3.0F};
        new Assertion<>(
            "Must convert float values to iterable",
            new IterableOfFloats(values),
            new HasValues<>(values[0], values[1], values[2])
        ).affirm();
    }
}
