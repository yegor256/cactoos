/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.IOException;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;

/**
 * Test case for {@link InputWithFallback}.
 *
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class InputWithFallbackTest {

    @Test
    void readsAlternativeInput() {
        new Assertion<>(
            "Can't read alternative source",
            new TextOf(
                new InputWithFallback(
                    new InputOf(
                        new File("/this-file-is-absent-for-sure.txt")
                    ),
                    new InputOf("hello, world!")
                )
            ),
            new EndsWith("world!")
        ).affirm();
    }

    @Test
    void readsAlternativeInputUri() {
        new Assertion<>(
            "Can't read alternative source from URI",
            new TextOf(
                new InputWithFallback(
                    () -> {
                        throw new IOException("Always fails!");
                    },
                    new InputOf("it works!")
                )
            ),
            new EndsWith("works!")
        ).affirm();
    }

}
