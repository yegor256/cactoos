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
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for unzip archive.
 *
 * @author Oleg Cherednik (abba-best@yandex.ru)
 * @version $Id$
 * @since 0.49.3
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public class UnzipItTest {

    /**
     * Path to zup archive.
     */
    private static File zip;
    /**
     * Destination path (will be holding zip content).
     */
    private static File destination;
    /**
     * Constant <tt>empty</tt>.
     */
    private static final String EMPTY = "empty";
    /**
     * Constant <tt>path2</tt>.
     */
    private static final String PATH2 = "path2";
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
     * Constant <tt>path</tt>.
     */
    private static final String PATH1 = "path1";
    /**
     * Expected list of zip entries.
     */
    private static final String[] EXPECTED = {
        UnzipItTest.EMPTY,
        UnzipItTest.EMPTY_TXT,
        UnzipItTest.ONE_TXT,
        UnzipItTest.PATH1,
        "path1/empty",
        "path1/empty.txt",
        "path1/one.txt",
        "path1/path11",
        "path1/path11/empty",
        "path1/path11/empty.txt",
        "path1/path11/one.txt",
        "path1/path11/two.txt",
        "path1/two.txt",
        UnzipItTest.PATH2,
        "path2/empty",
        "path2/empty.txt",
        "path2/one.txt",
        "path2/path22",
        "path2/path22/empty",
        "path2/path22/empty.txt",
        "path2/path22/one.txt",
        "path2/path22/two.txt",
        "path2/two.txt",
        UnzipItTest.TWO_TXT,
    };

    @BeforeClass
    public static void createZipContent() throws Exception {
        final File src = createTempDirectory();
        UnzipItTest.createContent(src);
        final File tmp = createTempDirectory();
        tmp.mkdirs();
        UnzipItTest.zip = new ZipIt().withEmptyFiles().withEmptyFolders()
            .withDestination(tmp).apply(src);
        UnzipItTest.destination = createTempDirectory();
    }

    @AfterClass
    public static void removeZip() {
        if (UnzipItTest.zip != null) {
            UnzipItTest.zip.delete();
        }
    }

    @Test
    public final void shouldUnzipToCustomDirectory() throws Exception {
        final File dst = new UnzipIt().withDestination(UnzipItTest.destination)
            .apply(UnzipItTest.zip);
        MatcherAssert.assertThat(
            dst,
            Matchers.notNullValue()
        );
        MatcherAssert.assertThat(
            UnzipItTest.destination,
            Matchers.equalTo(dst)
        );
        final String[] actual = getEntries(dst);
        MatcherAssert.assertThat(
            UnzipItTest.EXPECTED,
            Matchers.equalTo(actual)
        );
    }

    @Test
    public final void shouldUnzipInSourceDirectory() throws Exception {
        final File dst = new UnzipIt().apply(UnzipItTest.zip);
        MatcherAssert.assertThat(
            dst,
            Matchers.notNullValue()
        );
        MatcherAssert.assertThat(
            UnzipItTest.zip.getParentFile(),
            Matchers.equalTo(dst)
        );
        final String[] actual = getEntries(dst);
        MatcherAssert.assertThat(
            UnzipItTest.EXPECTED.length + 1,
            Matchers.equalTo(actual.length)
        );
        for (int iii = 0,
            jjj = 0; iii < actual.length; iii += 1) {
            if (!actual[iii].endsWith(UnzipItTest.zip.getName())) {
                MatcherAssert.assertThat(
                    UnzipItTest.EXPECTED[jjj],
                    Matchers.equalTo(actual[iii])
                );
                jjj += 1;
            }
        }
    }

    private static File createTempDirectory()
        throws IOException, URISyntaxException {
        final Path path = Paths.get(
            ZipItTest.class.getProtectionDomain().getCodeSource()
            .getLocation().toURI()
        );
        final File dir = Files.createTempDirectory(path, "unzip_test_")
            .toFile();
        dir.deleteOnExit();
        return dir;
    }

    private static String[] getEntries(final File dst) throws IOException {
        final int len = dst.getAbsolutePath().length();
        return Files.walk(Paths.get(dst.getAbsolutePath()))
            .map(String::valueOf)
            .map(path -> path.substring(len))
            .filter(path -> !path.isEmpty())
            .map(path -> path.substring(1))
            .map(UnzipItTest::replaceIncorrectFileSeparators)
            .sorted()
            .toArray(String[]::new);
    }

    private static String replaceIncorrectFileSeparators(final String path) {
        return ZipIt.BACK_SLASH.matcher(path).replaceAll(ZipIt.DIR_MARKER);
    }

    private static void createContent(final File src) throws Exception {
        createDirContent(src);
        File file = new File(src, UnzipItTest.PATH1);
        file.mkdirs();
        createDirContent(file);
        file = new File(file, "path11");
        file.mkdirs();
        createDirContent(file);
        file = new File(src, UnzipItTest.PATH2);
        file.mkdirs();
        createDirContent(file);
        file = new File(file, "path22");
        file.mkdirs();
        createDirContent(file);
    }

    private static void createDirContent(final File parent) throws Exception {
        new File(parent, UnzipItTest.EMPTY).mkdirs();
        createFile(new File(parent, UnzipItTest.EMPTY_TXT), null);
        createFile(new File(parent, UnzipItTest.ONE_TXT), "one");
        createFile(new File(parent, UnzipItTest.TWO_TXT), "two");
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
}
