/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link CloseShieldInput}.
 * @since 1.0.0
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class CloseShieldInputTest {

    @Test
    @SuppressWarnings("try")
    void preventsOriginalStreamToBeClosed() throws Exception {
        try (FakeInputStream origin = new FakeInputStream()) {
            // @checkstyle EmptyBlockCheck (5 lines)
            try (
                InputStream ignored =
                    new CloseShieldInput(new InputOf(origin)).stream()
            ) {
            }
            MatcherAssert.assertThat(
                "Must not close origin stream",
                origin.isClosed(),
                new IsEqual<>(false)
            );
        }
    }

    @Test
    void readsContent() throws Exception {
        try (
            InputStream in = new InputStreamOf("Text content")
        ) {
            MatcherAssert.assertThat(
                "Must read text",
                new TextOf(new CloseShieldInput(new InputOf(in))),
                new IsText("Text content")
            );
        }
    }
}
