/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link DeadInput}.
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class DeadInputTest {

    @Test
    void readsEmptyContent() {
        MatcherAssert.assertThat(
            "must read empty content",
            new DeadInput(),
            new HasContent("")
        );
    }
}
