/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Directory}.
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class DirectoryTest {

    @Test
    void listsFilesAndFoldersInDirectory(@TempDir final Path dir) throws IOException {
        dir.resolve("x/y").toFile().mkdirs();
        Files.write(dir.resolve("x/y/test"), "".getBytes());
        MatcherAssert.assertThat(
            "must list files in a directory represented by a path",
            new Directory(dir),
            new HasSize(4)
        );
    }

    @Test
    void listsFilesInDirectoryByFile(@TempDir final Path dir) throws Exception {
        final File file = dir.toFile();
        dir.resolve("parent/child").toFile().mkdirs();
        Files.write(dir.resolve("parent/child/file"), "".getBytes());
        MatcherAssert.assertThat(
            "must list files in a directory represented by a file",
            new Directory(file),
            new HasSize(4)
        );
    }
}
