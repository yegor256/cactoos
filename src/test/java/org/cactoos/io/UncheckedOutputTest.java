/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link UncheckedOutput}.
 *
 * @since 0.11
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class UncheckedOutputTest {

    @Test
    void rethrowsCheckedToUncheckedException() {
        new Assertion<>(
            "Exception is not rethrown as runtime",
            () -> new UncheckedOutput(
                () -> {
                    throw new IOException("intended");
                }
            ).stream(),
            new Throws<>(RuntimeException.class)
        ).affirm();
    }

}
