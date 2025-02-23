/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use
 * {@link org.cactoos.Text} as an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (215 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeInputFromTextTest {

    @Test
    void copiesFromTextToPath(@TempDir final Path wdir) throws Exception {
        final String input =
            "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teetext1.txt").toFile();
        new LengthOf(
            new TeeInput(new TextOf(input), output.toPath())
        ).value();
        new Assertion<>(
            "text must be copied to the path",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromTextWithCharsetToPath(@TempDir final Path wdir) throws Exception {
        final String input =
            "Hello, товарищ path #2 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teetext2.txt").toFile();
        new LengthOf(
            new TeeInput(
                new TextOf(input),
                output.toPath(),
                StandardCharsets.UTF_8
            )
        ).value();
        new Assertion<>(
            "text must be copied to the path with UTF_8 charset",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromTextWithCharsetByNameToPath(@TempDir final Path wdir) throws Exception {
        final String input =
            "Hello, товарищ path #3 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teetext3.txt").toFile();
        new LengthOf(
            new TeeInput(
                new TextOf(input),
                output.toPath(),
                StandardCharsets.UTF_8.name()
            )
        ).value();
        new Assertion<>(
            "text must be copied to the path with UTF_8 charset's name",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromTextToFile(@TempDir final Path wdir) throws Exception {
        final String input =
            "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teetext4.txt").toFile();
        new LengthOf(
            new TeeInput(new TextOf(input), output)
        ).value();
        new Assertion<>(
            "text must be copied to the file",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromTextWithCharsetToFile(@TempDir final Path wdir) throws Exception {
        final String input =
            "Hello, товарищ file #2 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teetext5.txt").toFile();
        new LengthOf(
            new TeeInput(
                new TextOf(input),
                output,
                StandardCharsets.UTF_8
            )
        ).value();
        new Assertion<>(
            "text must be copied to the file with UTF_8 charset",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromTextWithCharsetByNameToFile(@TempDir final Path wdir) throws Exception {
        final String input =
            "Hello, товарищ file #3 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teetext6.txt").toFile();
        new LengthOf(
            new TeeInput(
                new TextOf(input),
                output,
                StandardCharsets.UTF_8.name()
            )
        ).value();
        new Assertion<>(
            "text must be copied to the file with UTF_8 charset's name",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromTextToOutput(@TempDir final Path wdir) throws Exception {
        final String input =
            "Hello, товарищ output #1 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teetext7.txt").toFile();
        new LengthOf(
            new TeeInput(new TextOf(input), new OutputTo(output))
        ).value();
        new Assertion<>(
            "text must be copied to the output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromTextWithCharsetToOutput(@TempDir final Path wdir)
        throws Exception {
        final String input =
            "Hello, товарищ output #2 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teetext8.txt").toFile();
        new LengthOf(
            new TeeInput(
                new TextOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8
            )
        ).value();
        new Assertion<>(
            "text must be copied to the output with UTF_8 charset",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromTextWithCharsetByNameToOutput(@TempDir final Path wdir)
        throws Exception {
        final String input =
            "Hello, товарищ output #3 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teetext9.txt").toFile();
        new LengthOf(
            new TeeInput(
                new TextOf(input),
                new OutputTo(output),
                StandardCharsets.UTF_8.name()
            )
        ).value();
        new Assertion<>(
            "text must be copied to the output with UTF_8 charset's name",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }
}
