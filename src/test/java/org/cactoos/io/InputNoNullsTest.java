/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link InputNoNulls}.
 * @since 0.10
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class InputNoNullsTest {

    @Test
    void failForNullInput() {
        new Assertion<>(
            "Does not fail on null input",
            () -> new InputNoNulls(null).stream(),
            new Throws<>(Exception.class)
        ).affirm();
    }

    @Test
    void failForNullStream() {
        new Assertion<>(
            "Does not fail on null stream",
            () -> new InputNoNulls(() -> null).stream(),
            new Throws<>(Exception.class)
        ).affirm();
    }

    @Test
    void okForNoNullInput() throws Exception {
        new InputNoNulls(new DeadInput()).stream();
    }
}
