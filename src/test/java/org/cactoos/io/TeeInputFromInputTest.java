/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.scalar.LengthOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use
 * {@link org.cactoos.Input} as an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (200 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeInputFromInputTest {

    @Test
    void copiesFromInputToPath(@TempDir final Path wdir) throws Exception {
        final String input = "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("copy1.txt").toFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                output.toPath()
            )
        ).value();
        new Assertion<>(
            "Must copy from input to the output path",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromInputToFile(@TempDir final Path wdir) throws Exception {
        final String input = "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("copy2.txt").toFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                output
            )
        ).value();
        new Assertion<>(
            "Must copy from input to the output file",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromInputToWriter(@TempDir final Path wdir) throws Exception {
        final String input = "Hello, товарищ write #1 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("copy3.txt").toFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output)
            )
        ).value();
        new Assertion<>(
            "Must copy from input to the output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromInputWithSizeToWriter(@TempDir final Path wdir) throws Exception {
        final String input = "Hello, товарищ writer #2 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("copy4.txt").toFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output),
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from input with size to the output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromInputWithCharsetToWriter(@TempDir final Path wdir) throws Exception {
        final String input = "Hello, товарищ writer #3 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("copy5.txt").toFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output),
                StandardCharsets.UTF_8
            )
        ).value();
        new Assertion<>(
            "Must copy from input with charset to output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromInputWithCharsetAndSizeToWriter(@TempDir final Path wdir) throws Exception {
        final String input = "Hello, товарищ writer #4 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("copy5.txt").toFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output),
                StandardCharsets.UTF_8,
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from input with charset and size to output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromInputWithCharsetByNameToWriter(@TempDir final Path wdir) throws Exception {
        final String input = "Hello, товарищ writer #5 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("copy6.txt").toFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output),
                StandardCharsets.UTF_8.name()
            )
        ).value();
        new Assertion<>(
            "Must copy from input with charset by name to output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromInputWithCharsetByNameAndSizeToWriter(@TempDir final Path wdir)
        throws Exception {
        final String input = "Hello, товарищ writer #6 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("copy7.txt").toFile();
        new LengthOf(
            new TeeInput(
                new InputOf(input),
                new WriterTo(output),
                StandardCharsets.UTF_8.name(),
                input.length()
            )
        ).value();
        new Assertion<>(
            "Must copy from input with charset by name and size to output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }
}
