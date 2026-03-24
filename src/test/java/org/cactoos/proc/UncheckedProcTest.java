/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link UncheckedProc}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class UncheckedProcTest {

    @Test
    void rethrowsCheckedToUncheckedException() {
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void runtimeExceptionGoesOut() {
        MatcherAssert.assertThat(
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
        );
    }

}
