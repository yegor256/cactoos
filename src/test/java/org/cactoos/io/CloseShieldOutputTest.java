/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.OutputStream;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link CloseShieldOutput}.
 * @since 1.0.0
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
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
            new Assertion<>(
                "Must not close origin stream",
                origin.isClosed(),
                new IsEqual<>(false)
            ).affirm();
        }
    }
}
