/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.StringEndsWith;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link DeadOutput}.
 *
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class DeadOutputTest {

    @Test
    void readsEmptyContent() {
        MatcherAssert.assertThat(
            "must write empty content",
            new TeeInput(
                new InputOf("How are you, мой друг?"),
                new DeadOutput()
            ),
            new HasContent(new StringEndsWith("друг?"))
        );
    }
}
