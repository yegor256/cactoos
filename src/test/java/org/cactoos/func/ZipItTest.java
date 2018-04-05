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
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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
    /**
     * Constant <tt>empty</tt>.
     */
    private static final String EMPTY = "empty";
    /**
     * Constant <tt>empty.txt</tt>.
     */
    private static final String EMPTY_TXT = "empty.txt";
    /**
     * Constant <tt>one.txt</tt>.
     */
    private static final String ONE_TXT = "one.txt";
    /**
     * Constant <tt>two.txt</tt>.
     */
    private static final String TWO_TXT = "two.txt";
    /**
     * Constant <tt>path1/one.txt</tt>.
     */
    private static final String PATH1_ONE_TXT = "path1/one.txt";
    /**
     * Constant <tt>path1/two.txt</tt>.
     */
    private static final String PATH1_TWO_TXT = "path1/two.txt";
    /**
     * Constant <tt>path2/one.txt</tt>.
     */
    private static final String PATH2_ONE_TXT = "path2/one.txt";
    /**
     * Constant <tt>path2/two.txt</tt>.
     */
    private static final String PATH2_TWO_TXT = "path2/two.txt";
    /**
     * Constant <tt>path1/path11/one.txt</tt>.
     */
    private static final String PATH11_ONE_TXT = "path1/path11/one.txt";
    /**
     * Constant <tt>path1/path11/two.txt</tt>.
     */
    private static final String PATH11_TWO_TXT = "path1/path11/two.txt";
    /**
     * Constant <tt>path2/path22/one.txt</tt>.
     */
    private static final String PATH22_ONE_TXT = "path2/path22/one.txt";
    /**
     * Constant <tt>path2/path22/two.txt</tt>.
     */
    private static final String PATH22_TWO_TXT = "path2/path22/two.txt";
    /**
     * Constant <tt>%s.zip</tt>.
     */
    private static final String ZIP_EXT = "%s.zip";

    @BeforeClass
    public static void createZipContent() throws Exception {
        ZipItTest.srcDir = createTempDirectory();
        ZipItTest.dstDir = createTempDirectory();
        ZipItTest.srcDir.mkdirs();
        ZipItTest.dstDir.mkdirs();
        createContent(ZipItTest.srcDir);
    }

    @Test
    public final void shouldZipInCustomDirectoryIncludingEmptyFilesAndFolders()
        throws Exception {
        File zip = null;
        try {
            zip = new ZipIt()
                .withEmptyFolders()
                .withEmptyFiles()
                .withDestination(ZipItTest.dstDir)
                .apply(ZipItTest.srcDir);
            final Function<String, String> addext =
                name -> String.format(ZipItTest.ZIP_EXT, name);
            MatcherAssert.assertThat(
                zip,
                Matchers.notNullValue()
            );
            MatcherAssert.assertThat(
                new File(
                    ZipItTest.dstDir, addext.apply(ZipItTest.srcDir.getName())
                ),
                Matchers.equalTo(zip)
            );
            final String[] expected = {
                ZipItTest.EMPTY,
                ZipItTest.EMPTY_TXT,
                ZipItTest.ONE_TXT,
                "path1/empty",
                "path1/empty.txt",
                ZipItTest.PATH1_ONE_TXT,
                "path1/path11/empty",
                "path1/path11/empty.txt",
                ZipItTest.PATH11_ONE_TXT,
                ZipItTest.PATH11_TWO_TXT,
                ZipItTest.PATH1_TWO_TXT,
                "path2/empty",
                "path2/empty.txt",
                ZipItTest.PATH2_ONE_TXT,
                "path2/path22/empty",
                "path2/path22/empty.txt",
                ZipItTest.PATH22_ONE_TXT,
                ZipItTest.PATH22_TWO_TXT,
                ZipItTest.PATH2_TWO_TXT,
                ZipItTest.TWO_TXT,
            };
            final List<String> entries = getAllEntries(zip);
            MatcherAssert.assertThat(
                expected.length,
                Matchers.equalTo(entries.size())
            );
            final String[] actual = entries.toArray(new String[entries.size()]);
            Arrays.sort(actual);
            MatcherAssert.assertThat(
                expected,
                Matchers.equalTo(actual)
            );
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
            zip = new ZipIt().apply(ZipItTest.srcDir);
            final Function<String, String> addext =
                name -> String.format(ZipItTest.ZIP_EXT, name);
            MatcherAssert.assertThat(
                zip,
                Matchers.notNullValue()
            );
            MatcherAssert.assertThat(
                new File(
                    ZipItTest.srcDir.getParentFile(),
                    addext.apply(ZipItTest.srcDir.getName())
                ),
                Matchers.equalTo(zip)
            );
            final String[] expected = {
                ZipItTest.ONE_TXT,
                ZipItTest.PATH1_ONE_TXT,
                ZipItTest.PATH11_ONE_TXT,
                ZipItTest.PATH11_TWO_TXT,
                ZipItTest.PATH1_TWO_TXT,
                ZipItTest.PATH2_ONE_TXT,
                ZipItTest.PATH22_ONE_TXT,
                ZipItTest.PATH22_TWO_TXT,
                ZipItTest.PATH2_TWO_TXT,
                ZipItTest.TWO_TXT,
            };
            final List<String> entries = getAllEntries(zip);
            MatcherAssert.assertThat(
                expected.length,
                Matchers.equalTo(entries.size())
            );
            final String[] actual = entries.toArray(new String[entries.size()]);
            Arrays.sort(actual);
            MatcherAssert.assertThat(
                expected,
                Matchers.equalTo(actual)
            );
        } finally {
            if (zip != null) {
                zip.delete();
            }
        }
    }

    private static void createContent(final File src) throws Exception {
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
        new File(parent, ZipItTest.EMPTY).mkdirs();
        createFile(new File(parent, ZipItTest.EMPTY_TXT), null);
        createFile(new File(parent, ZipItTest.ONE_TXT), "one");
        createFile(new File(parent, ZipItTest.TWO_TXT), "two");
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
            while (true) {
                entry = in.getNextEntry();
                if (entry == null) {
                    break;
                }
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
            || path.endsWith(ZipIt.NOT_DIR_MARKER)) {
            res = path.substring(0, path.length() - 1);
        }
        return res;
    }

    private static String replaceIncorrectFileSeparators(final String path) {
        return ZipIt.BACK_SLASH.matcher(path).replaceAll(ZipIt.DIR_MARKER);
    }

}
