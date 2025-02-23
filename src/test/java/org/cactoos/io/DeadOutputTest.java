/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.hamcrest.core.StringEndsWith;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link DeadOutput}.
 *
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class DeadOutputTest {

    @Test
    void readsEmptyContent() {
        new Assertion<>(
            "must write empty content",
            new TeeInput(
                new InputOf("How are you, мой друг?"),
                new DeadOutput()
            ),
            new HasContent(new StringEndsWith("друг?"))
        ).affirm();
    }

}
