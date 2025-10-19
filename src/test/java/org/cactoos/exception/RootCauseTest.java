/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.exception;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test Case for {@link RootCause}.
 *
 * @since 0.56
 */
final class RootCauseTest {

    @Test
    void rootCauseTest() throws Exception {
        final Throwable inner = new Throwable();
        final RootCause exc = new RootCause(new Throwable(new Throwable(inner)));
        new Assertion<>(
            "Should return inner exception.",
            exc.value(),
            new IsEqual<>(inner)
        ).affirm();
    }
}
