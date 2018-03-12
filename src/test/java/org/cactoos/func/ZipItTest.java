package org.cactoos.func;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Oleg Cherednik (abba-best@yandex.ru)
 * @version $Id$
 * @since 0.49.3
 */
public class ZipItTest {
    private static File srcDir;
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
    public void shouldZipInCustomDirectoryIncludingEmptyFilesAndFolders() throws Exception {
        File zip = null;

        try {
            zip = new ZipIt()
                    .withEmptyFolders()
                    .withEmptyFiles()
                    .withDestination(dstDir)
                    .apply(srcDir);

            assertNotNull(zip);
            assertEquals(new File(dstDir, srcDir.getName() + ".zip"), zip);

            String[] expected = {
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
                    "two.txt" };

            List<String> entries = getAllEntries(zip);
            assertEquals(expected.length, entries.size());

            String[] actual = entries.toArray(new String[entries.size()]);
            Arrays.sort(actual);
            assertArrayEquals(expected, actual);
        } finally {
            if (zip != null)
                zip.delete();
        }
    }

    @Test
    public void shouldZipInSourceDirectoryNotIncludingEmptyFilesAndFolders() throws Exception {
        File zip = null;

        try {
            zip = new ZipIt().apply(srcDir);
            assertNotNull(zip);
            assertEquals(new File(srcDir.getParentFile(), srcDir.getName() + ".zip"), zip);

            String[] expected = {
                    "one.txt",
                    "path1/one.txt",
                    "path1/path11/one.txt",
                    "path1/path11/two.txt",
                    "path1/two.txt",
                    "path2/one.txt",
                    "path2/path22/one.txt",
                    "path2/path22/two.txt",
                    "path2/two.txt",
                    "two.txt" };

            List<String> entries = getAllEntries(zip);
            assertEquals(expected.length, entries.size());

            String[] actual = entries.toArray(new String[entries.size()]);
            Arrays.sort(actual);
            assertArrayEquals(expected, actual);
        } finally {
            if (zip != null)
                zip.delete();
        }
    }

    private static File createTempDirectory() throws IOException, URISyntaxException {
        Path path = Paths.get(ZipItTest.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        File dir = Files.createTempDirectory(path, "zip_test_").toFile();
        dir.deleteOnExit();
        return dir;
    }

    /**
     * Creates content for zip archive.
     *
     * srcDir
     * |-- empty
     * |-- path1
     * |   |-- empty
     * |   |-- path11
     * |   |   |-- empty
     * |   |   |-- empty.txt
     * |   |   |-- one.txt
     * |   |   |-- two.txt
     * |   |-- empty.txt
     * |   |-- one.txt
     * |   |-- two.txt
     * |-- path2
     * |   |-- empty
     * |   |-- path22
     * |   |   |-- empty
     * |   |   |-- empty.txt
     * |   |   |-- one.txt
     * |   |   |-- two.txt
     * |   |-- empty.txt
     * |   |-- one.txt
     * |   |-- two.txt
     * |-- empty.txt
     * |-- one.txt
     * |-- two.txt
     *
     * @param srcDir source directory
     */
    static void createContent(File srcDir) throws Exception {
        createDirContent(srcDir);

        File file = new File(srcDir, "path1");
        file.mkdirs();
        createDirContent(file);

        file = new File(file, "path11");
        file.mkdirs();
        createDirContent(file);

        file = new File(srcDir, "path2");
        file.mkdirs();
        createDirContent(file);

        file = new File(file, "path22");
        file.mkdirs();
        createDirContent(file);
    }

    private static void createDirContent(File parent) throws Exception {
        new File(parent, "empty").mkdirs();
        createFile(new File(parent, "empty.txt"), null);
        createFile(new File(parent, "one.txt"), "one");
        createFile(new File(parent, "two.txt"), "two");

    }

    private static void createFile(File file, String content) throws Exception {
        try (PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8.name())) {
            if (content != null)
                writer.println(content);
        }
    }

    private static List<String> getAllEntries(File zip) throws IOException {
        ZipEntry entry;
        List<String> entries = new ArrayList<>();

        try (ZipInputStream in = new ZipInputStream(new FileInputStream(zip))) {
            while ((entry = in.getNextEntry()) != null) {
                entries.add(removeDirectoryMarker(replaceIncorrectFileSeparators(entry.getName())));
            }
        }

        return entries;
    }

    private static String removeDirectoryMarker(String path) {
        return path.endsWith(ZipIt.DIR_MARKER) || path.endsWith(ZipIt.ILLEGAL_DIR_MARKER) ? path.substring(0, path.length() - 1) : path;
    }

    private static String replaceIncorrectFileSeparators(String path) {
        return ZipIt.BACK_SLASH.matcher(path).replaceAll(ZipIt.DIR_MARKER);
    }

}
