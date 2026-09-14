/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.io.InputOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link Synced}.
 *
 * @since 0.18
 */
final class SyncedTest {

    @Test
    void syncsText() {
        MatcherAssert.assertThat(
            "Must work with a text",
            new Synced(new TextOf("Hello")),
            new IsText("Hello")
        );
    }

    @Test
    void syncsTextWithLock() {
        MatcherAssert.assertThat(
            "Must work with a text and an external lock",
            new Synced(new TextOf("Hello"), new Object()),
            new IsText("Hello")
        );
    }

    @Test
    void syncsInput() {
        MatcherAssert.assertThat(
            "Must work with an input",
            new Synced(new InputOf("Hello")),
            new IsText("Hello")
        );
    }

    @Test
    void syncsInputWithLock() {
        MatcherAssert.assertThat(
            "Must work with an input and an external lock",
            new Synced(new InputOf("Hello"), new Object()),
            new IsText("Hello")
        );
    }
}
