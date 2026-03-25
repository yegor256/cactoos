/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use
 * {@link org.cactoos.Bytes} as an input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (100 lines)
 */
final class TeeInputFromBytesTest {

    @Test
    void copiesFromBytesToPath(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ path äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teebytes1.txt").toFile();
        new LengthOf(
            new TeeInput(new BytesOf(message), output.toPath())
        ).value();
        MatcherAssert.assertThat(
            "Must copy bytes to file path",
            new InputOf(output),
            new HasContent(message)
        );
    }

    @Test
    void copiesFromBytesToFile(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ file äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teebytes2.txt").toFile();
        new LengthOf(
            new TeeInput(new BytesOf(message), output)
        ).value();
        MatcherAssert.assertThat(
            "Must copy bytes to file",
            new InputOf(output),
            new HasContent(message)
        );
    }

    @Test
    void copiesFromBytesToOutput(@TempDir final Path wdir) throws Exception {
        final String message =
            "Hello, товарищ output äÄ üÜ öÖ and ß";
        final File output = wdir.resolve("teebytes3.txt").toFile();
        new LengthOf(
            new TeeInput(new BytesOf(message), new OutputTo(output))
        ).value();
        MatcherAssert.assertThat(
            "Must bytes to output",
            new InputOf(output),
            new HasContent(message)
        );
    }
}
