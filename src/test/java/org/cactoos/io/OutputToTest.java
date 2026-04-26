/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link OutputTo}.
 * @since 0.15
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class OutputToTest {

    @Test
    void writesIntoPath(@TempDir final Path wdir) throws Exception {
        final Path path = wdir.resolve("one/two/three/file.txt");
        final String content = "Hello, товарищ!";
        new LengthOf(new TeeInput(content, new OutputTo(path))).value();
        MatcherAssert.assertThat(
            "Must write into path",
            new InputOf(path),
            new HasContent(content)
        );
    }

    @Test
    void writesIntoFile(@TempDir final Path wdir) throws Exception {
        final Path path = wdir.resolve("a/b/c/file.txt");
        final String txt = "Hello, друг!";
        new LengthOf(
            new TeeInput(txt, new SyncOutput(new OutputTo(path.toFile())))
        ).value();
        MatcherAssert.assertThat(
            "Must write into file",
            new InputOf(path.toFile()),
            new HasContent(txt)
        );
    }

    @Test
    void writesIntoWriter() throws Exception {
        final String txt = "Hello, writer!";
        final StringWriter output = new StringWriter();
        new LengthOf(new TeeInput(txt, new OutputTo(output))).value();
        MatcherAssert.assertThat(
            "Must write into writer",
            new InputOf(output.getBuffer()),
            new HasContent(txt)
        );
    }

    @Test
    void writesIntoWriterWithCharset() throws Exception {
        final String txt = "Hello, writer with charset!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(txt, new OutputTo(output, StandardCharsets.UTF_8))
        ).value();
        MatcherAssert.assertThat(
            "Must write into writer with charset",
            new InputOf(output.getBuffer()),
            new HasContent(txt)
        );
    }

    @Test
    void writesIntoWriterWithCharsetByName() throws Exception {
        final String txt = "Hello, writer with charset by name!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(txt, new OutputTo(output, StandardCharsets.UTF_8))
        ).value();
        MatcherAssert.assertThat(
            "Must write into writer with charset by name",
            new InputOf(output.getBuffer()),
            new HasContent(txt)
        );
    }

    @Test
    void writesIntoWriterWithCharsetAndSize() throws Exception {
        final String txt = "Hello, writer with charset and size!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(
                txt,
                new OutputTo(output, StandardCharsets.UTF_8, 1)
            )
        ).value();
        MatcherAssert.assertThat(
            "Must write into writer with charset and size",
            new InputOf(output.getBuffer()),
            new HasContent(txt)
        );
    }

    @Test
    void writesIntoWriterWithSize() throws Exception {
        final String txt = "Hello, writer with size!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(
                txt,
                new OutputTo(output, 1)
            )
        ).value();
        MatcherAssert.assertThat(
            "Must write into writer with size",
            new InputOf(output.getBuffer()),
            new HasContent(txt)
        );
    }

    @Test
    void writesIntoWriterWithCharsetByNameAndSize() throws Exception {
        final String txt = "Hello, writer with charset by name and size!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(
                txt,
                new OutputTo(output, StandardCharsets.UTF_8.name(), 1)
            )
        ).value();
        MatcherAssert.assertThat(
            "Must write into writer with charset by name and size",
            new InputOf(output.getBuffer()),
            new HasContent(txt)
        );
    }

    @Test
    void writesIntoWriterWithDecoderAndSize() throws Exception {
        final String txt = "Hello, writer with decoder and size!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(
                txt,
                new OutputTo(output, StandardCharsets.UTF_8.newDecoder(), 1)
            )
        ).value();
        MatcherAssert.assertThat(
            "Must write into writer with decoder and size",
            new InputOf(output.getBuffer()),
            new HasContent(txt)
        );
    }
}
