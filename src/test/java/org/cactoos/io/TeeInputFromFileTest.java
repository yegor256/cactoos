/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.scalar.LengthOf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use file as an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (120 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeInputFromFileTest {

    @Test
    void copiesFromFileToFile(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("teeinput1-1.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("teeinput1-2.txt").toFile();
        new LengthOf(
            new TeeInput(
                input,
                output
            )
        ).value();
        new Assertion<>(
            "Must copy from input file to output file",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }

    @Test
    void copiesFromFileToPath(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("teeinput2-1.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("teeinput2-2.txt").toFile();
        new LengthOf(
            new TeeInput(
                input,
                output.toPath()
            )
        ).value();
        new Assertion<>(
            "Must copy from input file to output path",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }

    @Test
    void copiesFromFileToOutput(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ output #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("teeinput3-1.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("teeinput3-2.txt").toFile();
        new LengthOf(
            new TeeInput(
                input,
                new OutputTo(output)
            )
        ).value();
        new Assertion<>(
            "Must copy from input file to output",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }
}
