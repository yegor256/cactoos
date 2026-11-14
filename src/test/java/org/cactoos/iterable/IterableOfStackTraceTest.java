/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterable;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsIterableContaining;
import org.junit.jupiter.api.Test;

/**
 * Test Case for {@link IterableOfStackTrace}.
 *
 * @since 0.56
 */
final class IterableOfStackTraceTest {

    @Test
    void exceptionIterableTest() {
        final Throwable expected = new Throwable();
        MatcherAssert.assertThat(
            "Must get the expected exception.",
            new IterableOfStackTrace(new Throwable(expected)),
            new IsIterableContaining<>(new IsEqual<>(expected))
        );
    }
}
