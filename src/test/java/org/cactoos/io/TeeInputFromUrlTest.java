/*
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
 * Test case for {@link TeeInput}. Cases for ctors which use
 * {@link java.net.URL} as an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (125 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeInputFromUrlTest {

    @Test
    void copiesFromUrlToPath(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ path #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("teeurl1-1.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("teeurl1-2.txt").toFile();
        new LengthOf(
            new TeeInput(input.toURI().toURL(), output.toPath())
        ).value();
        new Assertion<>(
            "Must copy from URL to path.",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }

    @Test
    void copiesFromUrlToFile(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ file #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("teeurl2-1.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("teeurl2-2.txt").toFile();
        new LengthOf(
            new TeeInput(input.toURI().toURL(), output)
        ).value();
        new Assertion<>(
            "Must copy from URL to file.",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }

    @Test
    void copiesFromUrlToOutput(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ output #1 äÄ üÜ öÖ and ß";
        final File input = wdir.resolve("teeurl3-1.txt").toFile();
        Files.write(
            input.toPath(),
            message.getBytes(StandardCharsets.UTF_8)
        );
        final File output = wdir.resolve("teeurl3-2.txt").toFile();
        new LengthOf(
            new TeeInput(input.toURI().toURL(), new OutputTo(output))
        ).value();
        new Assertion<>(
            "Must copy from URL to output.",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }
}
