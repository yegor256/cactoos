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
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link TempFolder}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TempFolderTest {

    @Test
    void createsDirectory() throws Exception {
        try (TempFolder folder = new TempFolder()) {
            final File dir = folder.value().toFile();
            new Assertion<>(
                "must create new directory",
                dir.exists() && dir.isDirectory(),
                new IsTrue()
            ).affirm();
        }
    }

    @Test
    @SuppressWarnings("PMD.CloseResource")
    void deletesDirectory() throws Exception {
        final TempFolder dir = new TempFolder(
            new Randomized('d', 'e', 'g').asString()
        );
        dir.close();
        new Assertion<>(
            "Can't delete folder while closing",
            !dir.value().toFile().exists(),
            new IsTrue()
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.CloseResource")
    void deletesNonEmptyDirectory() throws Exception {
        final TempFolder temp = new TempFolder();
        final Path root = temp.value();
        new ForEach<>(
            new ProcOf<String>(
                name -> {
                    final Path dir = Files.createDirectories(
                        new File(root.toFile(), name).toPath()
                    );
                    new ForEach<>(
                        new ProcOf<String>(
                            filename -> {
                                new TempFile(
                                    () -> dir,
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
        temp.close();
        new Assertion<>(
            "Can't delete not empty folder while closing",
            temp.value().toFile().exists(),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @Test
    void createDirectoryWithDirectoriesAndFiles() throws Exception {
        try (TempFolder temp = new TempFolder()) {
            final Path root = temp.value();
            new ForEach<>(
                new ProcOf<String>(
                    name -> {
                        final Path dir = Files.createDirectories(
                            new File(root.toFile(), name).toPath()
                        );
                        new ForEach<>(
                            new ProcOf<String>(
                                filename -> {
                                    new TempFile(
                                        () -> dir,
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
            new Assertion<>(
                "Directory contains files and sub directories",
                temp.value().toFile().exists(),
                new IsTrue()
            ).affirm();
        }
    }
}
