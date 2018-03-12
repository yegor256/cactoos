package org.cactoos.func;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Oleg Cherednik (abba-best@yandex.ru)
 * @version $Id$
 * @since 0.49.3
 */
public class UnzipItTest {
    private static File zip;
    private static File destination;

    @BeforeClass
    public static void createZipContent() throws Exception {
        File srcDir = createTempDirectory();
        ZipItTest.createContent(srcDir);

        File tmp = createTempDirectory();
        tmp.mkdirs();
        zip = new ZipIt().withEmptyFiles().withEmptyFolders().withDestination(tmp).apply(srcDir);
        destination = createTempDirectory();
    }

    @AfterClass
    public static void removeZip() {
        if (zip != null)
            zip.delete();
    }

    @Test
    public void shouldUnzipToCustomDirectory() throws Exception {
        File dstDir = new UnzipIt().withDestination(destination).apply(zip);
        assertNotNull(dstDir);
        assertEquals(destination, dstDir);

        String[] expected = {
                "empty",
                "empty.txt",
                "one.txt",
                "path1",
                "path1/empty",
                "path1/empty.txt",
                "path1/one.txt",
                "path1/path11",
                "path1/path11/empty",
                "path1/path11/empty.txt",
                "path1/path11/one.txt",
                "path1/path11/two.txt",
                "path1/two.txt",
                "path2",
                "path2/empty",
                "path2/empty.txt",
                "path2/one.txt",
                "path2/path22",
                "path2/path22/empty",
                "path2/path22/empty.txt",
                "path2/path22/one.txt",
                "path2/path22/two.txt",
                "path2/two.txt",
                "two.txt" };

        String[] actual = getEntries(dstDir);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldUnzipInSourceDirectory() throws Exception {
        File dstDir = new UnzipIt().apply(zip);
        assertNotNull(dstDir);
        assertEquals(zip.getParentFile(), dstDir);

        String[] expected = {
                "empty",
                "empty.txt",
                "one.txt",
                "path1",
                "path1/empty",
                "path1/empty.txt",
                "path1/one.txt",
                "path1/path11",
                "path1/path11/empty",
                "path1/path11/empty.txt",
                "path1/path11/one.txt",
                "path1/path11/two.txt",
                "path1/two.txt",
                "path2",
                "path2/empty",
                "path2/empty.txt",
                "path2/one.txt",
                "path2/path22",
                "path2/path22/empty",
                "path2/path22/empty.txt",
                "path2/path22/one.txt",
                "path2/path22/two.txt",
                "path2/two.txt",
                "two.txt" };

        String[] actual = getEntries(dstDir);
        // zip file is in this directory
        assertEquals(expected.length + 1, actual.length);

        for (int i = 0, j = 0; i < actual.length; i++)
            if (!actual[i].endsWith(zip.getName()))
                assertEquals(expected[j++], actual[i]);
    }

    private static File createTempDirectory() throws IOException, URISyntaxException {
        Path path = Paths.get(ZipItTest.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        File dir = Files.createTempDirectory(path, "unzip_test_").toFile();
        dir.deleteOnExit();
        return dir;
    }

    private static String[] getEntries(File dstDir) throws IOException {
        int len = dstDir.getAbsolutePath().length();

        return Files.walk(Paths.get(dstDir.getAbsolutePath()))
                    .map(String::valueOf)
                    .map(path -> path.substring(len))
                    .filter(path -> !path.isEmpty())
                    .map(path -> path.substring(1))
                    .map(UnzipItTest::replaceIncorrectFileSeparators)
                    .sorted()
                    .toArray(String[]::new);
    }

    private static String replaceIncorrectFileSeparators(String path) {
        return ZipIt.BACK_SLASH.matcher(path).replaceAll(ZipIt.DIR_MARKER);
    }
}
