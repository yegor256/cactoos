package org.cactoos.func;

import org.cactoos.Func;
import org.cactoos.io.TeeInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Func that creates zip archive for given directory with additional settings like: include empty files or folders.
 * <p>There is no thread-safety guarantee.
 *
 * @author Oleg Cherednik (abba-best@yandex.ru)
 * @version $Id$
 * @since 0.49.3
 */
public final class ZipIt implements Func<File, File> {
    static final String DIR_MARKER = "/";
    static final String ILLEGAL_DIR_MARKER = "\\";
    static final Pattern BACK_SLASH = Pattern.compile("\\\\");

    private boolean emptyFolders;
    private boolean emptyFiles;
    private File destination;

    public ZipIt withEmptyFolders() {
        emptyFolders = true;
        return this;
    }

    public ZipIt withEmptyFiles() {
        emptyFiles = true;
        return this;
    }

    public ZipIt withDestination(File destination) {
        this.destination = destination;
        return this;
    }

    private File getDestination(File srcDir) {
        File parent = destination != null ? destination : srcDir.getParentFile();
        return new File(parent, srcDir.getName() + ".zip");
    }

    @Override
    public File apply(File srcDir) throws Exception {
        File pathToZip = getDestination(srcDir);

        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(pathToZip))) {
            List<String> entries = collectZipEntries(srcDir, srcDir);

            for (String entry : entries) {
                File src = new File(srcDir + File.separator + entry);

                if (src.isDirectory())
                    zip.putNextEntry(new ZipEntry(entry + DIR_MARKER));
                else {
                    zip.putNextEntry(new ZipEntry(entry));
                    copyFile(src, zip);
                }

                zip.closeEntry();
            }
        }

        return pathToZip;
    }

    private static void copyFile(File file, ZipOutputStream zip) throws IOException {
        TeeInputStream in = new TeeInputStream(new FileInputStream(file), zip);
        while (in.read() > 0) {
        }
    }

    private List<String> collectZipEntries(File srcDir, File dirOrFile) {
        List<String> entries = new ArrayList<>();

        if (dirOrFile.isFile())
            addFile(entries, srcDir, dirOrFile);
        else if (dirOrFile.isDirectory())
            addDirectory(entries, srcDir, dirOrFile);

        return entries;
    }

    private void addFile(List<String> entries, File srcDir, File file) {
        if (file.length() != 0 || emptyFiles)
            entries.add(createZipEntry(srcDir, file.toString()));
    }

    private void addDirectory(List<String> entries, File srcDir, File dir) {
        String[] sub = dir.list();

        if (sub == null)
            return;

        if (sub.length == 0) {
            if (emptyFolders)
                entries.add(createZipEntry(srcDir, dir.toString()));
        } else
            for (String fileName : sub)
                entries.addAll(collectZipEntries(srcDir, new File(dir, fileName)));
    }

    private static String createZipEntry(File srcDir, String file) {
        return file.substring(srcDir.getAbsolutePath().length() + 1, file.length());
    }
}
