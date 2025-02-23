/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.cactoos.Func;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link UncheckedFunc}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class UncheckedFuncTest {

    @Test
    void rethrowsCheckedToUncheckedException() {
        new Assertion<>(
            "Exception should be rethrown as unchecked",
            () -> new UncheckedFunc<>(
                (Func<Integer, String>) i -> {
                    throw new IOException("intended");
                }
            ).apply(1),
            new Throws<>(UncheckedIOException.class)
        ).affirm();
    }

    @Test
    void runtimeExceptionGoesOut() {
        new Assertion<>(
            "Runtime exception should be rethrown as is",
            () -> new UncheckedFunc<>(
                i -> {
                    throw new IllegalStateException("intended to fail");
                }
            ).apply(1),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

}
