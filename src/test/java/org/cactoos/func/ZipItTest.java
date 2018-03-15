/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
package org.cactoos.func;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for zip directory.
 *
 * @author Oleg Cherednik (abba-best@yandex.ru)
 * @version $Id$
 * @since 0.49.3
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public class ZipItTest {

    /**
     * Source directory (zip content).
     */
    private static File srcDir;
    /**
     * Destination directory (will be holding zip archive).
     */
    private static File dstDir;

    @BeforeClass
    public static void createZipContent() throws Exception {
        srcDir = createTempDirectory();
        dstDir = createTempDirectory();
        srcDir.mkdirs();
        dstDir.mkdirs();
        createContent(srcDir);
    }

    @Test
    public final void shouldZipInCustomDirectoryIncludingEmptyFilesAndFolders()
        throws Exception {
        File zip = null;
        try {
            zip = new ZipIt()
                .withEmptyFolders()
                .withEmptyFiles()
                .withDestination(dstDir)
                .apply(srcDir);
            Assert.assertNotNull(zip);
            Assert.assertEquals(
                new File(dstDir, String.format("%s.zip", srcDir.getName())),
                    zip
            );
            final String[] expected = {
                "empty",
                "empty.txt",
                "one.txt",
                "path1/empty",
                "path1/empty.txt",
                "path1/one.txt",
                "path1/path11/empty",
                "path1/path11/empty.txt",
                "path1/path11/one.txt",
                "path1/path11/two.txt",
                "path1/two.txt",
                "path2/empty",
                "path2/empty.txt",
                "path2/one.txt",
                "path2/path22/empty",
                "path2/path22/empty.txt",
                "path2/path22/one.txt",
                "path2/path22/two.txt",
                "path2/two.txt",
                "two.txt",
            };
            final List<String> entries = getAllEntries(zip);
            Assert.assertEquals(expected.length, entries.size());
            final String[] actual = entries.toArray(new String[entries.size()]);
            Arrays.sort(actual);
            Assert.assertArrayEquals(expected, actual);
        } finally {
            if (zip != null) {
                zip.delete();
            }
        }
    }

    @Test
    public final void shouldZipInSourceDirNotIncludingEmptyFilesAndFolders()
        throws Exception {
        File zip = null;
        try {
            zip = new ZipIt().apply(srcDir);
            Assert.assertNotNull(zip);
            Assert.assertEquals(
                new File(
                    srcDir.getParentFile(),
                    String.format(
                        "%s.zip",
                        srcDir.getName()
                    )
                ),
                zip
            );
            final String[] expected = {
                "one.txt",
                "path1/one.txt",
                "path1/path11/one.txt",
                "path1/path11/two.txt",
                "path1/two.txt",
                "path2/one.txt",
                "path2/path22/one.txt",
                "path2/path22/two.txt",
                "path2/two.txt",
                "two.txt",
            };
            final List<String> entries = getAllEntries(zip);
            Assert.assertEquals(expected.length, entries.size());
            final String[] actual = entries.toArray(new String[entries.size()]);
            Arrays.sort(actual);
            Assert.assertArrayEquals(expected, actual);
        } finally {
            if (zip != null) {
                zip.delete();
            }
        }
    }

    static void createContent(final File src) throws Exception {
        createDirContent(src);
        File file = new File(src, "path1");
        file.mkdirs();
        createDirContent(file);
        file = new File(file, "path11");
        file.mkdirs();
        createDirContent(file);
        file = new File(src, "path2");
        file.mkdirs();
        createDirContent(file);
        file = new File(file, "path22");
        file.mkdirs();
        createDirContent(file);
    }

    private static File createTempDirectory() throws Exception {
        final Path path = Paths.get(
            ZipItTest.class.getProtectionDomain().getCodeSource()
            .getLocation().toURI()
        );
        final File dir = Files.createTempDirectory(path, "zip_test_").toFile();
        dir.deleteOnExit();
        return dir;
    }

    private static void createDirContent(final File parent) throws Exception {
        new File(parent, "empty").mkdirs();
        createFile(new File(parent, "empty.txt"), null);
        createFile(new File(parent, "one.txt"), "one");
        createFile(new File(parent, "two.txt"), "two");
    }

    private static void createFile(final File file, final String content)
        throws Exception {
        try (PrintWriter writer = new PrintWriter(
            file, StandardCharsets.UTF_8.name()
            )
            ) {
            if (content != null) {
                writer.println(content);
            }
        }
    }

    private static List<String> getAllEntries(final File zip)
        throws IOException {
        ZipEntry entry;
        final List<String> entries = new LinkedList<>();
        try (ZipInputStream in = new ZipInputStream(new FileInputStream(zip))) {
            while ((entry = in.getNextEntry()) != null) {
                entries.add(
                    removeDirectoryMarker(
                        replaceIncorrectFileSeparators(entry.getName())
                    )
                );
            }
        }
        return entries;
    }

    private static String removeDirectoryMarker(final String path) {
        String res = path;
        if (path.endsWith(ZipIt.DIR_MARKER)
            || path.endsWith(ZipIt.ILLEGAL_DIR_MARKER)) {
            res = path.substring(0, path.length() - 1);
        }
        return res;
    }

    private static String replaceIncorrectFileSeparators(final String path) {
        return ZipIt.BACK_SLASH.matcher(path).replaceAll(ZipIt.DIR_MARKER);
    }

}
