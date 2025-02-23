/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Zip}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class ZipTest {

    @Test
    void zip(@TempDir final Path dir) throws Exception {
        dir.resolve("x/y").toFile().mkdirs();
        Files.write(dir.resolve("x/y/test"), "".getBytes());
        try (ZipInputStream input = new ZipInputStream(
            new Zip(
                new Directory(dir)
            ).stream()
        )) {
            int cnt = 0;
            ZipEntry entry = input.getNextEntry();
            while (entry != null) {
                ++cnt;
                entry = input.getNextEntry();
            }
            new Assertion<>(
                "Can't list files in a directory represented by a path",
                cnt,
                new IsEqual<>(4)
            ).affirm();
        }
    }

    @Test
    void zipsArbitraryFileList(@TempDir final Path dir) throws Exception {
        dir.resolve("file0");
        final ListOf<Path> targets = new ListOf<>(
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
            new Assertion<>(
                "Must zip files in different directories",
                entries,
                IsIterableContainingInAnyOrder.containsInAnyOrder(
                    targets.get(0).toString(),
                    targets.get(1).toString()
                )
            ).affirm();
        }
    }
}
