/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link CloseShieldOutput}.
 * @since 1.0.0
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class CloseShieldOutputTest {

    @Test
    @SuppressWarnings("try")
    void preventsOriginalStreamToBeClosed() throws Exception {
        try (FakeOutputStream origin = new FakeOutputStream()) {
            // @checkstyle EmptyBlockCheck (5 lines)
            try (
                OutputStream ignored = new CloseShieldOutput(() -> origin).stream()
            ) {
            }
            MatcherAssert.assertThat(
                "Must not close origin stream",
                origin.isClosed(),
                new IsEqual<>(false)
            );
        }
    }
}
