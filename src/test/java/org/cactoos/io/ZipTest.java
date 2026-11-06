/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.io;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsIterableContaining;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.StringStartsWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test case for {@link Zip}.
 *
 * @since 0.29
 */
final class ZipTest {

    @Test
    void zip(@TempDir final Path dir) throws Exception {
        dir.resolve("x/y").toFile().mkdirs();
        Files.write(dir.resolve("x/y/test"), "".getBytes(StandardCharsets.UTF_8));
        try (
            ZipInputStream input = new ZipInputStream(
                new Zip(
                    new Directory(dir)
                ).stream()
            )
        ) {
            int cnt = 0;
            ZipEntry entry = input.getNextEntry();
            while (entry != null) {
                ++cnt;
                entry = input.getNextEntry();
            }
            MatcherAssert.assertThat(
                "Can't list files in a directory represented by a path",
                cnt,
                new IsEqual<>(4)
            );
        }
    }

    @Test
    void zipsArbitraryFileList(@TempDir final Path dir) throws Exception {
        final List<Path> targets = new ListOf<>(
            dir.resolve("dir1/file1.txt"),
            dir.resolve("dir2/file2.txt")
        );
        try (ZipInputStream input = new ZipInputStream(new Zip(targets).stream())) {
            ZipEntry entry = input.getNextEntry();
            final List<String> entries = new ArrayList<>(2);
            while (entry != null) {
                entries.add(entry.getName());
                entry = input.getNextEntry();
            }
            MatcherAssert.assertThat(
                "Must zip files in different directories",
                entries,
                IsIterableContainingInAnyOrder.containsInAnyOrder(
                    ZipTest.name(targets.get(0)),
                    ZipTest.name(targets.get(1))
                )
            );
        }
    }

    @Test
    void doesNotStoreAbsolutePaths(@TempDir final Path dir) throws Exception {
        Files.write(dir.resolve("a.txt"), "A".getBytes(StandardCharsets.UTF_8));
        try (ZipInputStream input = new ZipInputStream(new Zip(new Directory(dir)).stream())) {
            ZipEntry entry = input.getNextEntry();
            final List<String> entries = new ArrayList<>(2);
            while (entry != null) {
                entries.add(entry.getName());
                entry = input.getNextEntry();
            }
            MatcherAssert.assertThat(
                "Must not store entries with a leading slash",
                entries,
                new IsNot<>(new IsIterableContaining<>(new StringStartsWith("/")))
            );
        }
    }

    @Test
    void marksDirectoriesAsDirectories(@TempDir final Path dir) throws Exception {
        final Path sub = dir.resolve("sub");
        sub.toFile().mkdirs();
        try (
            ZipInputStream input = new ZipInputStream(
                new Zip(Collections.singleton(sub)).stream()
            )
        ) {
            MatcherAssert.assertThat(
                "Must not store a directory as a plain file entry",
                input.getNextEntry().isDirectory(),
                new IsEqual<>(true)
            );
        }
    }

    private static String name(final Path path) {
        return path.toString().substring(path.getRoot().toString().length())
            .replace(path.getFileSystem().getSeparator(), "/");
    }
}
