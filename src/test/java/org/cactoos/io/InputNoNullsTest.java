/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link InputNoNulls}.
 * @since 0.10
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class InputNoNullsTest {

    @Test
    void failForNullInput() {
        MatcherAssert.assertThat(
            "Does not fail on null input",
            () -> new InputNoNulls(null).stream(),
            new Throws<>(Exception.class)
        );
    }

    @Test
    void failForNullStream() {
        MatcherAssert.assertThat(
            "Does not fail on null stream",
            () -> new InputNoNulls(() -> null).stream(),
            new Throws<>(Exception.class)
        );
    }

    @Test
    void okForNoNullInput() throws Exception {
        new InputNoNulls(new DeadInput()).stream();
    }
}
