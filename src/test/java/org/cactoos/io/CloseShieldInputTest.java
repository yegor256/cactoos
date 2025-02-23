/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.InputStream;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link CloseShieldInput}.
 * @since 1.0.0
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
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
            new Assertion<>(
                "Must not close origin stream",
                origin.isClosed(),
                new IsEqual<>(false)
            ).affirm();
        }
    }

    @Test
    void readsContent() throws Exception {
        final String content = "Text content";
        try (
            InputStream in = new InputStreamOf(content)
        ) {
            new Assertion<>(
                "Must read text",
                new TextOf(new CloseShieldInput(new InputOf(in))),
                new IsText(content)
            ).affirm();
        }
    }
}
