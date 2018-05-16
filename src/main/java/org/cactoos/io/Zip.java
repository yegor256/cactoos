/*
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

package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.cactoos.Input;

/**
 * Zip files and directory.
 * <br>
 * <br>There is no thread-safety guarantee.
 *
 * @since 0.29
 */
@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
public final class Zip implements Input {

    /**
     * Origin directory.
     */
    private final Directory origin;

    /**
     * Ctor.
     *
     * @param origin Origin directory.
     */
    public Zip(final Directory origin) {
        this.origin = origin;
    }

    @Override
    public InputStream stream() throws Exception {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (final ZipOutputStream zip = new ZipOutputStream(out)) {
            for (final Path path : this.origin) {
                final File file = path.toFile();
                final ZipEntry entry = new ZipEntry(
                    file.getPath()
                );
                zip.putNextEntry(entry);
                if (file.isFile()) {
                    try (
                        final FileInputStream input = new FileInputStream(file)
                    ) {
                        zip.write(new BytesOf(new InputOf(input)).asBytes());
                    }
                }
                zip.closeEntry();
            }
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
