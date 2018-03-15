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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.cactoos.Func;
import org.cactoos.io.TeeInputStream;

/**
 * Func that unpack given zip archive.
 * <p>There is no thread-safety guarantee.
 *
 * @author Oleg Cherednik (abba-best@yandex.ru)
 * @version $Id$
 * @since 0.49.3
 */
public final class UnzipIt implements Func<File, File> {

    /**
     * Hold a destination path for archive.
     */
    private File destination;

    @Override
    public File apply(final File zip) throws Exception {
        final File dst = this.getDestination(zip);
        dst.mkdir();
        try (ZipInputStream in = new ZipInputStream(new FileInputStream(zip))) {
            ZipEntry entry = in.getNextEntry();
            while (entry != null) {
                final String filename = entry.getName();
                final File newfile = new File(dst, filename);
                if (entry.isDirectory()) {
                    newfile.mkdirs();
                } else {
                    newfile.getParentFile().mkdirs();
                    copyFile(in, newfile);
                }
                entry = in.getNextEntry();
            }
        }
        return dst;
    }

    /**
     * Set destination path for the archive (if not specified, then source zip
     * path will be used).
     *
     * @param dst Destination path
     * @return Retrieves <tt>this</tt> object
     */
    public UnzipIt withDestination(final File dst) {
        this.destination = dst;
        return this;
    }

    /**
     * Retrieves destination of the unzipped content.
     *
     * @param zip Not {@literal null} current zip file path
     * @return Not {@literal null} destination directory path
     */
    private File getDestination(final File zip) {
        File file = this.destination;
        if (this.destination == null) {
            file = zip.getParentFile();
        }
        return file;
    }

    /**
     * Copies given string {@code zip} into given {@code file}.
     *
     * @param zip Not {@literal null} zip stream
     * @param file Not {@literal null} destination file
     * @throws IOException in case of any problem
     */
    private static void copyFile(final ZipInputStream zip,
        final File file) throws IOException {
        final TeeInputStream ins = new TeeInputStream(
            zip, new FileOutputStream(file)
        );
        int count = 0;
        while (ins.read() > 0 && count >= 0) {
            count += 1;
        }
    }
}
