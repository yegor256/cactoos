/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cactoos.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Zip}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ZipTest {
    /**
     * Temporary folder.
     */
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void zip() throws Exception {
        final Path dir = this.folder.newFolder().toPath();
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
                // @checkstyle MagicNumber (1 line)
                new IsEqual<>(4)
            ).affirm();
        }
    }

    @Test
    public void zipsArbitraryFileList() throws Exception {
        this.folder.newFolder("dir1");
        this.folder.newFolder("dir2");
        this.folder.newFile("file0");
        final Path file1 = this.folder.newFile("dir1/file1.txt").toPath();
        final Path file2 = this.folder.newFile("dir1/file2.txt").toPath();
        try (ZipInputStream input = new ZipInputStream(
            new Zip(
                new IterableOf<>(file1, file2)
            ).stream()
        )) {
            ZipEntry entry = input.getNextEntry();
            List<String> zipEntries = new ArrayList<>();
            while (entry != null) {
                zipEntries.add(entry.getName());
                entry = input.getNextEntry();
            }
            new Assertion<>(
                "Can't zip files in different directories",
                zipEntries,
                IsIterableContainingInAnyOrder.containsInAnyOrder(
                    file1.toString(), file2.toString()
                )
            ).affirm();
        }
    }
}
