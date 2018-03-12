package org.cactoos.func;

import org.cactoos.Func;
import org.cactoos.io.TeeInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Func that unpack given zip archive.
 * <p>There is no thread-safety guarantee.
 *
 * @author Oleg Cherednik (abba-best@yandex.ru)
 * @version $Id$
 * @since 0.49.3
 */
public final class UnzipIt implements Func<File, File> {
    private File destination;

    public UnzipIt withDestination(File destination) {
        this.destination = destination;
        return this;
    }

    private File getDestination(File zip) {
        return destination != null ? destination : zip.getParentFile();
    }

    @Override
    public File apply(File zip) throws Exception {
        File dstDir = getDestination(zip);
        dstDir.mkdir();

        try (ZipInputStream in = new ZipInputStream(new FileInputStream(zip))) {
            ZipEntry entry = in.getNextEntry();

            while (entry != null) {
                String fileName = entry.getName();
                File newFile = new File(dstDir, fileName);

                if (entry.isDirectory())
                    newFile.mkdirs();
                else {
                    newFile.getParentFile().mkdirs();
                    copyFile(in, newFile);
                }

                entry = in.getNextEntry();
            }
        }

        return dstDir;
    }

    private static void copyFile(ZipInputStream zip, File file) throws IOException {
        TeeInputStream in = new TeeInputStream(zip, new FileOutputStream(file));
        while (in.read() > 0) {
        }
    }
}
