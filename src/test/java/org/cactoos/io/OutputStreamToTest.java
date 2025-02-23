/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.nio.file.Path;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link OutputStreamTo}.
 *
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class OutputStreamToTest {

    @Test
    void writesLargeContentToFile(@TempDir final Path wdir) {
        final Path temp = wdir.resolve("cactoos-1.txt-1");
        new Assertion<>(
            "Must copy Input to Output and return Input",
            new TextOf(
                new Sticky(
                    new TeeInput(
                        new ResourceOf("org/cactoos/large-text.txt"),
                        new OutputTo(new OutputStreamTo(temp))
                    )
                )
            ),
            new IsText(
                new TextOf(temp)
            )
        ).affirm();
    }

}
