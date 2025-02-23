/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link WriterAsOutput}.
 *
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class WriterAsOutputTest {

    @Test
    void writesLargeContentToFile(@TempDir final Path wdir) throws IOException {
        final Path temp = wdir.resolve("cactoos-1.txt-1");
        try (OutputStreamWriter writer = new OutputStreamWriter(
            Files.newOutputStream(temp.toAbsolutePath()),
            StandardCharsets.UTF_8
        )) {
            new Assertion<>(
                "Can't copy Input to Output and return Input",
                new TeeInput(
                    new ResourceOf("org/cactoos/large-text.txt"),
                    new WriterAsOutput(writer)
                ),
                new HasContent(
                    new TextOf(temp)
                )
            ).affirm();
        }
    }

}
