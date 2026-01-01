/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link UncheckedProc}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class UncheckedProcTest {

    @Test
    void rethrowsCheckedToUncheckedException() {
        new Assertion<>(
            "Checked exception was not rethrown as unchecked",
            () -> {
                new UncheckedProc<>(
                    input -> {
                        throw new IOException("intended");
                    }
                ).exec(1);
                return 1;
            },
            new Throws<>(UncheckedIOException.class)
        ).affirm();
    }

    @Test
    void runtimeExceptionGoesOut() {
        new Assertion<>(
            "Runtime exception was not rethrown",
            () -> {
                new UncheckedProc<>(
                    i -> {
                        throw new IllegalStateException("intended to fail");
                    }
                ).exec(1);
                return 1;
            },
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }

}
