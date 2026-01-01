/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link UncheckedBiFunc}.
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class UncheckedBiFuncTest {

    @Test
    void rethrowsCheckedToUncheckedException() {
        new Assertion<>(
            "Checked exception does not rethrown as unchecked",
            () -> new UncheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IOException("intended");
                }
            ).apply(1, 2),
            new Throws<>(UncheckedIOException.class)
        ).affirm();
    }

    @Test
    void testUncheckedBiFunc() {
        new Assertion<>(
            "Must return value",
            new UncheckedBiFunc<>(
                (fst, scd) -> true
            ).apply(1, 2),
            new IsTrue()
        ).affirm();
    }

    @Test
    void runtimeExceptionGoesOut() {
        new Assertion<>(
            "Runtime exception rethrown without changes",
            () -> new UncheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IllegalStateException("intended to fail");
                }
            ).apply(1, 2),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

}
