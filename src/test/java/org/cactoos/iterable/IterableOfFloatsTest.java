/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link IterableOfFloats}.
 * @since 1.0
 */
final class IterableOfFloatsTest {

    @Test
    void convertsFloatValuesToIterable() {
        final float[] values = {1.0f, 2.0f, 3.0f};
        MatcherAssert.assertThat(
            "Must convert float values to iterable",
            new IterableOfFloats(values),
            new HasValues<>(values[0], values[1], values[2])
        );
    }
}
