/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link WriterAsOutputStream}.
 *
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class WriterAsOutputStreamTest {

    @Test
    void writesToByteArray() {
        final String content = "Hello, товарищ! How are you?";
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatcherAssert.assertThat(
            "Can't copy Input to Writer",
            new TeeInput(
                new InputOf(content),
                new OutputTo(
                    new WriterAsOutputStream(
                        new OutputStreamWriter(
                            baos, StandardCharsets.UTF_8
                        ),
                        StandardCharsets.UTF_8,
                        13
                    )
                )
            ),
            new HasContent(
                new TextOf(baos::toByteArray, StandardCharsets.UTF_8)
            )
        );
    }

    @Test
    void writesLargeContentToFile(@TempDir final Path wdir) throws IOException {
        final Path temp = wdir.resolve("writestream1.txt");
        try (OutputStreamWriter writer = new OutputStreamWriter(
            Files.newOutputStream(temp.toAbsolutePath()),
            StandardCharsets.UTF_8
        )) {
            MatcherAssert.assertThat(
                "Can't copy Input to Output and return Input",
                new TeeInput(
                    new ResourceOf("org/cactoos/large-text.txt"),
                    new OutputTo(
                        new WriterAsOutputStream(
                            writer,
                            StandardCharsets.UTF_8,
                            345
                        )
                    )
                ),
                new HasContent(
                    new TextOf(temp)
                )
            );
        }
    }

    @Test
    void writesToFileAndRemovesIt(@TempDir final Path wdir) throws Exception {
        final Path temp = wdir.resolve("writestream2.txt");
        try (OutputStreamWriter writer = new OutputStreamWriter(
            Files.newOutputStream(temp.toAbsolutePath()),
            StandardCharsets.UTF_8
        )) {
            new LengthOf(
                new TeeInput(
                    new InputOf("Hello, товарищ! How are you?"),
                    new OutputTo(
                        new WriterAsOutputStream(
                            writer,
                            StandardCharsets.UTF_8,
                            345
                        )
                    )
                )
            ).value();
        }
        Files.delete(temp);
        MatcherAssert.assertThat(
            "file must not exist anymore",
            Files.exists(temp),
            new IsNot<>(new IsTrue())
        );
    }
}
