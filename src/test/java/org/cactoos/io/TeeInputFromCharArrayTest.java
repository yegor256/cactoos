/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use char array as an
 * input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (215 lines)
 */
final class TeeInputFromCharArrayTest {

    @Test
    void copiesFromCharArrayWithCharsetToFile(@TempDir final Path wdir) throws Exception {
        final File output = wdir.resolve("tee1.txt").toFile();
        new LengthOf(
            new TeeInput(
                "Hello, товарищ file #1 äÄ üÜ öÖ and ß".toCharArray(),
                output,
                StandardCharsets.UTF_8
            )
        ).value();
        MatcherAssert.assertThat(
            "char array must be copied to the file with charset UTF_8",
            new InputOf(output),
            new HasContent("Hello, товарищ file #1 äÄ üÜ öÖ and ß")
        );
    }

    @Test
    void copiesFromCharArrayWithCharsetByNameToFile(@TempDir final Path wdir)
        throws Exception {
        final File output = wdir.resolve("tee2.txt").toFile();
        new LengthOf(
            new TeeInput(
                "Hello, товарищ file #2 äÄ üÜ öÖ and ß".toCharArray(),
                output,
                StandardCharsets.UTF_8.name()
            )
        ).value();
        MatcherAssert.assertThat(
            "char array must be copied to the file with UTF_8 charset's name",
            new InputOf(output),
            new HasContent("Hello, товарищ file #2 äÄ üÜ öÖ and ß")
        );
    }

    @Test
    void copiesFromCharArrayToOutput(@TempDir final Path wdir) throws Exception {
        final File output = wdir.resolve("tee3.txt").toFile();
        new LengthOf(
            new TeeInput(
                "Hello, товарищ output #1 äÄ üÜ öÖ and ß".toCharArray(),
                new OutputTo(output)
            )
        ).value();
        MatcherAssert.assertThat(
            "char array must be copied to the output",
            new InputOf(output),
            new HasContent("Hello, товарищ output #1 äÄ üÜ öÖ and ß")
        );
    }

    @Test
    void copiesFromCharArrayWithCharsetToOutput(@TempDir final Path wdir) throws Exception {
        final File output = wdir.resolve("tee4.txt").toFile();
        new LengthOf(
            new TeeInput(
                "Hello, товарищ output #2 äÄ üÜ öÖ and ß".toCharArray(),
                new OutputTo(output),
                StandardCharsets.UTF_8
            )
        ).value();
        MatcherAssert.assertThat(
            "char array must be copied to the output with UTF_8 charset",
            new InputOf(output),
            new HasContent("Hello, товарищ output #2 äÄ üÜ öÖ and ß")
        );
    }

    @Test
    void copiesFromCharArrayWithCharsetByNameToOutput(@TempDir final Path wdir)
        throws Exception {
        final File output = wdir.resolve("tee5.txt").toFile();
        new LengthOf(
            new TeeInput(
                "Hello, товарищ output #3 äÄ üÜ öÖ and ß".toCharArray(),
                new OutputTo(output),
                StandardCharsets.UTF_8.name()
            )
        ).value();
        MatcherAssert.assertThat(
            "char array must be copied to the output with UTF_8 charset's name",
            new InputOf(output),
            new HasContent("Hello, товарищ output #3 äÄ üÜ öÖ and ß")
        );
    }

    @Test
    void copiesFromCharArrayToPath(@TempDir final Path wdir) throws Exception {
        final File output = wdir.resolve("tee6.txt").toFile();
        new LengthOf(
            new TeeInput(
                "Hello, товарищ path #1 äÄ üÜ öÖ and ß".toCharArray(),
                output.toPath()
            )
        ).value();
        MatcherAssert.assertThat(
            "char array must be copied to the path",
            new InputOf(output),
            new HasContent("Hello, товарищ path #1 äÄ üÜ öÖ and ß")
        );
    }

    @Test
    void copiesFromCharArrayWithCharsetToPath(@TempDir final Path wdir) throws Exception {
        final File output = wdir.resolve("tee7.txt").toFile();
        new LengthOf(
            new TeeInput(
                "Hello, товарищ path #2 äÄ üÜ öÖ and ß".toCharArray(),
                output.toPath(),
                StandardCharsets.UTF_8
            )
        ).value();
        MatcherAssert.assertThat(
            "char array must be copied to the path with UTF_8 charset",
            new InputOf(output),
            new HasContent("Hello, товарищ path #2 äÄ üÜ öÖ and ß")
        );
    }

    @Test
    void copiesFromCharArrayWithCharsetByNameToPath(@TempDir final Path wdir)
        throws Exception {
        final File output = wdir.resolve("tee8.txt").toFile();
        new LengthOf(
            new TeeInput(
                "Hello, товарищ path #3 äÄ üÜ öÖ and ß".toCharArray(),
                output.toPath(),
                StandardCharsets.UTF_8.name()
            )
        ).value();
        MatcherAssert.assertThat(
            "char array must be copied to the path with UTF_8 charset's name",
            new InputOf(output),
            new HasContent("Hello, товарищ path #3 äÄ üÜ öÖ and ß")
        );
    }

    @Test
    void copiesFromCharArrayToFile(@TempDir final Path wdir) throws Exception {
        final File output = wdir.resolve("tee9.txt").toFile();
        new LengthOf(
            new TeeInput(
                "Hello, товарищ file äÄ üÜ öÖ and ß".toCharArray(),
                output
            )
        ).value();
        MatcherAssert.assertThat(
            "char array must be copied to the file",
            new InputOf(output),
            new HasContent("Hello, товарищ file äÄ üÜ öÖ and ß")
        );
    }
}
