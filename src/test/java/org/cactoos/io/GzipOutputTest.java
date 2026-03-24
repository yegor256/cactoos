/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.io;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPOutputStream;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link GzipOutput}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class GzipOutputTest {

    @Test
    void writeToGzipOutput() throws Exception {
        final String content = "Hello!";
        final ByteArrayOutputStream expected = new ByteArrayOutputStream();
        try (
            Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                    new GZIPOutputStream(expected),
                    StandardCharsets.UTF_8
                )
            )
        ) {
            writer.write(content);
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (OutputStream output = new GzipOutput(
            new OutputTo(baos)
        ).stream()
        ) {
            new LengthOf(
                new TeeInput(
                    content,
                    new OutputTo(output)
                )
            ).value();
        }
        MatcherAssert.assertThat(
            "Can't write to a gzip output",
            baos.toByteArray(),
            new IsEqual<>(expected.toByteArray())
        );
    }

    @Test
    void writeToClosedGzipOutput(@TempDir final Path wdir) throws Exception {
        final OutputStream stream =
            Files.newOutputStream(
                wdir.resolve("cactoos.txt")
            );
        stream.close();
        MatcherAssert.assertThat(
            "can't write to closed stream",
            () -> new LengthOf(
                new TeeInput(
                    "Hello!",
                    new GzipOutput(new OutputTo(stream))
                )
            ).value(),
            new Throws<>(IOException.class)
        );
    }
}
