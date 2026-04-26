/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.exception;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test Case for {@link RootCause}.
 * @since 0.56
 */
final class RootCauseTest {

    @Test
    void rootCauseTest() throws Exception {
        final Throwable inner = new Throwable();
        MatcherAssert.assertThat(
            "Should return inner exception.",
            new RootCause(new Throwable(new Throwable(inner))).value(),
            new IsEqual<>(inner)
        );
    }
}
