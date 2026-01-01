/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Directory}.
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class DirectoryTest {

    @Test
    void listsFilesAndFoldersInDirectory(@TempDir final Path dir) throws IOException {
        dir.resolve("x/y").toFile().mkdirs();
        Files.write(dir.resolve("x/y/test"), "".getBytes());
        new Assertion<>(
            "must list files in a directory represented by a path",
            new Directory(dir),
            new HasSize(4)
        ).affirm();
    }

    @Test
    void listsFilesInDirectoryByFile(@TempDir final Path dir) throws Exception {
        final File file = dir.toFile();
        dir.resolve("parent/child").toFile().mkdirs();
        Files.write(dir.resolve("parent/child/file"), "".getBytes());
        new Assertion<>(
            "must list files in a directory represented by a file",
            new Directory(file),
            new HasSize(4)
        ).affirm();
    }
}
