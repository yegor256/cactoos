/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.Satisfies;
import org.llorllale.cactoos.matchers.StartsWith;

/**
 * Unit tests for {@link TempFile}.
 *
 * @since 1.0
 */
final class TempFileTest {

    @Test
    void createFile() throws Exception {
        try (TempFile file = new TempFile()) {
            MatcherAssert.assertThat(
                "Must create a temp file",
                Files.exists(file.value()),
                new IsEqual<>(true)
            );
        }
    }

    @Test
    void createFileInCustomPath() throws Exception {
        final Path custom = Paths.get(System.getProperty("user.home"));
        try (TempFile file = new TempFile(() -> custom, "", "")) {
            MatcherAssert.assertThat(
                "Must create a temp file at a custom path",
                file,
                new AllOf<TempFile>(
                    new Satisfies<>(
                        tmp -> Files.exists(tmp.value())
                    ),
                    new Satisfies<>(
                        tmp -> tmp.value().getParent().equals(custom)
                    )
                )
            );
        }
    }

    @Test
    void deleteFile() throws Exception {
        final Path path;
        try (TempFile file = new TempFile()) {
            path = file.value();
        }
        MatcherAssert.assertThat(
            "Must delete file on close",
            Files.exists(path),
            new IsEqual<>(false)
        );
    }

    @Test
    void createFileWithPrefix() throws Exception {
        final Text prefix = new FormattedText(
            "randomPrefix%s",
            System.currentTimeMillis()
        );
        try (TempFile file = new TempFile(prefix, new TextOf(""))) {
            MatcherAssert.assertThat(
                "File must be created with the given prefix",
                new TextOf(file.value().getFileName().toString()),
                new StartsWith(prefix)
            );
        }
    }

    @Test
    void createFileWithSuffix() throws Exception {
        final Text suffix = new FormattedText(
            "randomSuffix%s", System.currentTimeMillis()
        );
        try (TempFile file = new TempFile(new TextOf(""), suffix)) {
            MatcherAssert.assertThat(
                "File must be created with the given suffix",
                new TextOf(file.value().getFileName().toString()),
                new EndsWith(suffix)
            );
        }
    }
}
