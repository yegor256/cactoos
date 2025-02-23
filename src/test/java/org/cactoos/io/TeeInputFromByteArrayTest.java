/*
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
 * Test case for {@link TeeInput}. Cases for ctors which use byte array as an
 * input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (100 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeInputFromByteArrayTest {

    @Test
    void copiesFromByteArrayToPath(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ path äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("copytest.txt").toFile();
        new LengthOf(
            new TeeInput(
                message.getBytes(StandardCharsets.UTF_8),
                output.toPath()
            )
        ).value();
        new Assertion<>(
            "Must copy bytes to path",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }

    @Test
    void copiesFromByteArrayToFile(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ file äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("copyarraytest.txt").toFile();
        new LengthOf(
            new TeeInput(
                message.getBytes(StandardCharsets.UTF_8),
                output
            )
        ).value();
        new Assertion<>(
            "Must copy bytes to file",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }

    @Test
    void copiesFromByteArrayToOutput(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ output äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("copytooutput.txt").toFile();
        new LengthOf(
            new TeeInput(
                message.getBytes(StandardCharsets.UTF_8),
                new OutputTo(output)
            )
        ).value();
        new Assertion<>(
            "Must copy bytes to output",
            new InputOf(output),
            new HasContent(message)
        ).affirm();
    }
}
