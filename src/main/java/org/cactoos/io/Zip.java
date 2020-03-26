/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
import org.cactoos.func.ForEach;

/**
 * Zip files and directory.
 * <br>
 * <br>There is no thread-safety guarantee.
 *
 * @since 0.29
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 * @todo #898:30min Currently {@link Zip} does not support zipping of empty
 *  sub folder. Please change the implementation so that empty folders are
 *  zipped as well.
 */
@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
public final class Zip implements Input {

    /**
     * Origin list of files.
     */
    private final Directory directory;

    /**
     * Ctor.
     *
     * @param dir Origin directory.
     */
    public Zip(final Directory dir) {
        this.directory = dir;
    }

    @Override
    public InputStream stream() throws Exception {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(out)) {
            new ForEach<Path>(
                path -> {
                    final String relative = this.directory.path().getParent()
                        .relativize(path).toString();
                    final File file = path.toFile();
                    if (file.isFile()) {
                        zip.putNextEntry(new ZipEntry(relative));
                        try (FileInputStream fis = new FileInputStream(file)) {
                            zip.write(new BytesOf(new InputOf(fis)).asBytes());
                        }
                        zip.closeEntry();
                    }
                }
            ).exec(this.directory);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
