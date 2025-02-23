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
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link WriterTo}.
 *
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class WriterToTest {

    @Test
    void writesLargeContentToFile(@TempDir final Path wdir) {
        final Path temp = wdir.resolve("cactoos-1.txt-1");
        new Assertion<>(
            "Can't copy Input to Output and return Input",
            new TeeInput(
                new ResourceOf("org/cactoos/large-text.txt"),
                new WriterAsOutput(new WriterTo(temp))
            ),
            new HasContent(
                new TextOf(temp)
            )
        ).affirm();
    }

}
