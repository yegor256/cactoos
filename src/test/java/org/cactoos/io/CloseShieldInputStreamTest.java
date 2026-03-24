/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link CloseShieldInputStream}.
 * @since 1.0.0
 */
final class CloseShieldInputStreamTest {

    @Test
    @SuppressWarnings("try")
    void preventsOriginalStreamToBeClosed() throws Exception {
        try (FakeInputStream origin = new FakeInputStream()) {
            // @checkstyle EmptyBlockCheck (2 lines)
            try (InputStream ignored = new CloseShieldInputStream(origin)) {
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
        final String content = "Text content";
        try (InputStream in = new InputStreamOf(content)) {
            MatcherAssert.assertThat(
                "Must read from text",
                new TextOf(new CloseShieldInputStream(in)),
                new IsText(content)
            );
        }
    }

    @Test
    void makesDataAvailable() throws IOException {
        final String content = "Hello,חבר!";
        try (InputStream in = new InputStreamOf(content)) {
            MatcherAssert.assertThat(
                "Must show that data is available",
                new CloseShieldInputStream(in).available(),
                new Satisfies<>(l -> l > 0)
            );
        }
    }
}
