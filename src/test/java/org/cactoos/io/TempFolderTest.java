/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.iterable.IterableOf;
import org.cactoos.proc.ForEach;
import org.cactoos.proc.ProcOf;
import org.cactoos.text.Randomized;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link TempFolder}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class TempFolderTest {

    @Test
    void createsDirectory() throws Exception {
        try (TempFolder folder = new TempFolder()) {
            final File dir = folder.value().toFile();
            MatcherAssert.assertThat(
                "must create new directory",
                dir.exists() && dir.isDirectory(),
                new IsTrue()
            );
        }
    }

    @Test
    void deletesDirectory() throws Exception {
        final Path path;
        try (TempFolder dir = new TempFolder(
            new Randomized('d', 'e', 'g').asString()
        )) {
            path = dir.value();
        }
        MatcherAssert.assertThat(
            "Can't delete folder while closing",
            !path.toFile().exists(),
            new IsTrue()
        );
    }

    @Test
    void deletesNonEmptyDirectory() throws Exception {
        final Path path;
        try (TempFolder temp = new TempFolder()) {
            path = temp.value();
            new ForEach<>(
                new ProcOf<String>(
                    name -> {
                        new ForEach<>(
                            new ProcOf<String>(
                                filename -> {
                                    new TempFile(
                                        () -> Files.createDirectories(
                                            new File(path.toFile(), name).toPath()
                                        ),
                                        filename,
                                        ""
                                    ).value();
                                }
                            )
                        ).exec(
                            new IterableOf<>(
                                "file1.txt", "file2.txt", "file3.txt"
                            )
                        );
                    }
                )
            ).exec(
                new IterableOf<>(
                    "a", "b", "c", "d", "e"
                )
            );
        }
        MatcherAssert.assertThat(
            "Can't delete not empty folder while closing",
            path.toFile().exists(),
            new IsNot<>(new IsTrue())
        );
    }

    @Test
    void createDirectoryWithDirectoriesAndFiles() throws Exception {
        try (TempFolder temp = new TempFolder()) {
            new ForEach<>(
                new ProcOf<String>(
                    name -> {
                        new ForEach<>(
                            new ProcOf<String>(
                                filename -> {
                                    new TempFile(
                                        () -> Files.createDirectories(
                                            new File(temp.value().toFile(), name).toPath()
                                        ),
                                        filename,
                                        ""
                                    ).value();
                                }
                            )
                        ).exec(
                            new IterableOf<>(
                                "1.txt", "2.txt", "3.txt"
                            )
                        );
                    }
                )
            ).exec(
                new IterableOf<>(
                    "1", "2", "3", "4", "5"
                )
            );
            MatcherAssert.assertThat(
                "Directory contains files and sub directories",
                temp.value().toFile().exists(),
                new IsTrue()
            );
        }
    }
}
