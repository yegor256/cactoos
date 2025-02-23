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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use char sequence as
 * an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (215 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeInputFromCharSequenceTest {

    @Test
    void copiesFromCharSequenceToFile(@TempDir final Path wdir) throws Exception {
        final String input =
            "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teeinput1.txt").toFile();
        new LengthOf(
            new TeeInput(input, output)
        ).value();
        new Assertion<>(
            "char sequence must be copied to the file",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromCharSequenceWithCharsetToFile(@TempDir final Path wdir)
        throws Exception {
        final String input =
            "Hello, товарищ file #2 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teeinput2.txt").toFile();
        new LengthOf(
            new TeeInput(input, output, StandardCharsets.UTF_8)
        ).value();
        new Assertion<>(
            "char sequence must be copied to the file with UTF_8 charset",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromCharSequenceWithCharsetByNameToFile(@TempDir final Path wdir)
        throws Exception {
        final String input =
            "Hello, товарищ file #3 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teeinput3.txt").toFile();
        new LengthOf(
            new TeeInput(input, output, StandardCharsets.UTF_8.name())
        ).value();
        new Assertion<>(
            "char sequence must be copied to the file with UTF_8 charset's name",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromCharSequenceToPath(@TempDir final Path wdir) throws Exception {
        final String input =
            "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teeinput4.txt").toFile();
        new LengthOf(
            new TeeInput(input, output.toPath())
        ).value();
        new Assertion<>(
            "char sequence must be copied to the path",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromCharSequenceWithCharsetToPath(@TempDir final Path wdir)
        throws Exception {
        final String input =
            "Hello, товарищ path #2 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teeinput5.txt").toFile();
        new LengthOf(
            new TeeInput(input, output.toPath(), StandardCharsets.UTF_8)
        ).value();
        new Assertion<>(
            "char sequence must be copied to the path with UTF_8 charset",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromCharSequenceWithCharsetByNameToPath(@TempDir final Path wdir)
        throws Exception {
        final String input =
            "Hello, товарищ path #3 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teeinput6.txt").toFile();
        new LengthOf(
            new TeeInput(input, output.toPath(), StandardCharsets.UTF_8.name())
        ).value();
        new Assertion<>(
            "char sequence must be copied to the path with UTF_8 charset's name",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromCharSequenceToOutput(@TempDir final Path wdir) throws Exception {
        final String input =
            "Hello, товарищ output #1 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teeinput7.txt").toFile();
        new LengthOf(
            new TeeInput(input, new OutputTo(output))
        ).value();
        new Assertion<>(
            "char sequence must be copied to the output",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromCharSequenceWithCharsetToOutput(@TempDir final Path wdir)
        throws Exception {
        final String input =
            "Hello, товарищ output #2 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teeinput8.txt").toFile();
        new LengthOf(
            new TeeInput(input, new OutputTo(output), StandardCharsets.UTF_8)
        ).value();
        new Assertion<>(
            "char sequence must be copied to the output with UTF_8 charset",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }

    @Test
    void copiesFromCharSequenceWithCharsetByNameToOutput(@TempDir final Path wdir)
        throws Exception {
        final String input =
            "Hello, товарищ output #3 äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teeinput9.txt").toFile();
        new LengthOf(
            new TeeInput(input, new OutputTo(output), StandardCharsets.UTF_8.name())
        ).value();
        new Assertion<>(
            "char sequence must be copied to the output with UTF_8 charset's name",
            new InputOf(output),
            new HasContent(input)
        ).affirm();
    }
}
