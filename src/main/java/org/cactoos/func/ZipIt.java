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
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.cactoos.Func;
import org.cactoos.io.TeeInputStream;

/**
 * Func that creates zip archive for given directory with additional settings
 * like: include empty files or folders.
 * <p>There is no thread-safety guarantee.
 *
 * @author Oleg Cherednik (abba-best@yandex.ru)
 * @version $Id$
 * @since 0.49.3
 */
public final class ZipIt implements Func<File, File> {

    /**
     * Linux like path separator. It work in all platforms, so it is treated as
     * <tt>legal dir marker</tt>.
     */
    public static final String DIR_MARKER = "/";
    /**
     * No Linux like path separator. It is treated as <tt>illegal dir
     * marker</tt>.
     */
    public static final String NOT_DIR_MARKER = "\\";
    /**
     * Double back slash to resolve root paths on Windows platforms.
     */
    public static final Pattern BACK_SLASH = Pattern.compile("\\\\");

    /**
     * If {@code true}, then empty folders will be included to the zip.
     */
    private boolean emptyfolders;
    /**
     * If {@code true}, then empty files will be included to the zip.
     */
    private boolean emptyfiles;
    /**
     * Destination path for save created zip archive.
     */
    private File destination;

    @Override
    public File apply(final File srcdir) throws Exception {
        final File pathtozip = this.getDestination(srcdir);
        try (ZipOutputStream zip =
            new ZipOutputStream(new FileOutputStream(pathtozip))
        ) {
            final List<String> entries = this.collectZipEntries(srcdir, srcdir);
            for (final String entry : entries) {
                final File src = ZipIt.createFile(srcdir, entry);
                if (src.isDirectory()) {
                    zip.putNextEntry(
                        ZipIt.createZipEntry(entry + ZipIt.DIR_MARKER)
                    );
                } else {
                    zip.putNextEntry(ZipIt.createZipEntry(entry));
                    copyFile(src, zip);
                }
                zip.closeEntry();
            }
        }
        return pathtozip;
    }

    /**
     * Set {@link #emptyfolders} marker to {@code true}. It will make empty
     * folders be zipped as well.
     *
     * @return Retrieves <tt>this</tt> object
     */
    public ZipIt withEmptyFolders() {
        this.emptyfolders = true;
        return this;
    }

    /**
     * Set {@link #emptyfiles} marker to {@code true}. It will make empty
     * files be zipped as well.
     *
     * @return Retrieves <tt>this</tt> object
     */
    public ZipIt withEmptyFiles() {
        this.emptyfiles = true;
        return this;
    }

    /**
     * Set destination path for the archive (if not specified, then source zip
     * path will be used).
     *
     * @param dest Destination path
     * @return Retrieves <tt>this</tt> object
     */
    public ZipIt withDestination(final File dest) {
        this.destination = dest;
        return this;
    }

    /**
     * Create {@link ZipEntry} object base on given {@code name}.
     *
     * @param name Entry's name
     * @return No {@literal null} {@link ZipEntry} instance
     */
    private static ZipEntry createZipEntry(final String name) {
        return new ZipEntry(name);
    }

    /**
     * Set destination path for the zip archive (if not specified, then parent
     * directory of the source directory will be used).
     *
     * @param src Source content path
     * @return Retrieves <tt>this</tt> object
     */
    private File getDestination(final File src) {
        File parent = this.destination;
        if (this.destination == null) {
            parent = src.getParentFile();
        }
        return new File(parent, String.format("%s.zip", src.getName()));
    }

    /**
     * Copies given file {@code file} into given zip stream {@code zip}.
     *
     * @param file Not {@literal null} source content
     * @param zip Not {@literal null} zip stream
     * @throws IOException in case of any problem
     */
    private static void copyFile(final File file, final ZipOutputStream zip)
        throws IOException {
        final TeeInputStream ins = new TeeInputStream(
            new FileInputStream(file), zip
        );
        int count = 0;
        while (ins.read() > 0 && count >= 0) {
            count += 1;
        }
    }

    /**
     * Collects all paths that will be saved as zip entries in the zip archive.
     * Starting from {@code srcdir} including subdirectories.
     *
     * @param srcdir Not {@literal null} source directory
     * @param dirorfile Not {@literal null} directory or file
     * @return Not {@literal null} zip entries list
     */
    private List<String> collectZipEntries(final File srcdir,
        final File dirorfile) {
        final List<String> entries = new LinkedList<>();
        if (dirorfile.isFile()) {
            this.addFile(entries, srcdir, dirorfile);
        } else if (dirorfile.isDirectory()) {
            this.addDirectory(entries, srcdir, dirorfile);
        }
        return entries;
    }

    /**
     * Adds given {@code file} under given source directory {@code srcdir} to
     * the list of zip entries {@code entries}.
     *
     * @param entries No {@literal null} zip entries
     * @param srcdir Not {@literal null} source directory
     * @param file Not {@literal null} file ready to add to the {@code entries}
     */
    private void addFile(final List<String> entries,
        final File srcdir, final File file) {
        if (file.length() != 0 || this.emptyfiles) {
            entries.add(createZipEntry(srcdir, file.toString()));
        }
    }

    /**
     * Adds given {@code dir} including subdirectory under given source
     * directory {@code srcdir} to the list of zip entries {@code entries}.
     *
     * @param entries No {@literal null} zip entries
     * @param srcdir Not {@literal null} source directory
     * @param dir Not {@literal null} directory for scan entries
     */
    private void addDirectory(final List<String> entries,
        final File srcdir, final File dir) {
        final String[] sub = dir.list();
        if (sub == null) {
            return;
        }
        if (sub.length == 0) {
            if (this.emptyfolders) {
                entries.add(createZipEntry(srcdir, dir.toString()));
            }
        } else {
            for (final String filename : sub) {
                entries.addAll(
                    this.collectZipEntries(
                        srcdir, ZipIt.createFile(dir, filename)
                    )
                );
            }
        }
    }

    /**
     * Retrieves zip entry path based on given {@code srcdir} directory and
     * {@code file}.
     *
     * @param srcdir Not {@literal null} source directory
     * @param file Not {@literal null} file
     * @return Not {@literal null} zip entry path
     */
    private static String createZipEntry(final File srcdir, final String file) {
        return file.substring(
            srcdir.getAbsolutePath().length() + 1,
            file.length()
        );
    }

    /**
     * Create {@link File} object base on given {@code parent} and
     * {@code child}.
     *
     * @param parent File's parent
     * @param child Files's child
     * @return No {@literal null} {@link File} instance
     */
    private static File createFile(final File parent, final String child) {
        return new File(parent, child);
    }

}
