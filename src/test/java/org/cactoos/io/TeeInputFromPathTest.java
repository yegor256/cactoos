/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
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
 * Test case for {@link TeeInput}. Cases for ctors which use {@link Path} as
 * an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (120 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeInputFromPathTest {

    @Test
    void copiesFromPathToPath(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("copytest11.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("copytest12.txt").toFile();
        new LengthOf(
            new TeeInput(input.toPath(), output.toPath())
        ).value();
        new Assertion<>(
            "Must copy from input path to output path",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }

    @Test
    void copiesFromPathToFile(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("copytest21.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("copytest22.txt").toFile();
        new LengthOf(
            new TeeInput(input.toPath(), output)
        ).value();
        new Assertion<>(
            "Must copy from input path to output file",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }

    @Test
    void copiesFromPathToOutput(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ output #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("copytest31.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("copytest32.txt").toFile();
        new LengthOf(
            new TeeInput(input.toPath(), new OutputTo(output))
        ).value();
        new Assertion<>(
            "Must copy from input path to output",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }
}
