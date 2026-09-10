/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.cactoos.text.Concatenated;
import org.cactoos.text.Randomized;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link AppendTo}.
 * @since 1.0
 */
final class AppendToTest {

    /**
     * Ensures that AppendTo is failing on a negative predicate result.
     */
    @Test
    void failsIfFileDoesNotExist() throws Exception {
        final File source = new File(
            new Randomized(
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'
            ).asString()
        );
        MatcherAssert.assertThat(
            "Can't throw exception with proper message",
            () -> new AppendTo(source).stream(),
            new Throws<>(
                source.getPath(),
                NoSuchFileException.class
            )
        );
    }

    /**
     * Ensures that AppendTo is appending to a given file.
     * @param wdir TempDir to work in
     * @throws Exception if fails
     * @todo #1586:1h Tests failed on Windows due to not closed streams.
     *  It will be good to have feature to simplify autoclosing streams.
     */
    @Test
    void appendsToFile(@TempDir final Path wdir) throws Exception {
        final File source = wdir.resolve("apptest.txt").toFile();
        final String first = "abdcd";
        try (OutputStream out = new OutputTo(source).stream()) {
            out.write(first.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
        final String second = "efgh";
        try (OutputStream out = new AppendTo(source).stream()) {
            out.write(second.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
        MatcherAssert.assertThat(
            "Does not contain expected text",
            new InputOf(source),
            new HasContent(new Concatenated(first, second))
        );
    }

    /**
     * Ensures that AppendTo is appending unicode text to a given file.
     * @param wdir TempDir to work in
     * @throws Exception if fails
     */
    @Test
    void appendsUnicodeToFile(@TempDir final Path wdir) throws Exception {
        final File source = wdir.resolve("appunitest.txt").toFile();
        final String first = "Hello, товарищ output #3 äÄ ";
        try (OutputStream out = new OutputTo(source).stream()) {
            out.write(first.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
        final String second = "#4 äÄ üÜ öÖ and ß";
        try (OutputStream out = new AppendTo(source).stream()) {
            out.write(second.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
        MatcherAssert.assertThat(
            "Can't find expected unicode text content",
            new InputOf(source),
            new HasContent(new Concatenated(first, second))
        );
    }
}
