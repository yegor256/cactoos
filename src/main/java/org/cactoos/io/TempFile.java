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

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.IoCheckedScalar;
import org.cactoos.scalar.StickyScalar;
import org.cactoos.text.TextOf;

/**
 * A temporary file.
 * <p>
 * These are ephemeral files to be used in small scopes.
 * Typical use looks like this:
 * <pre>{@code
 *    try (final TempFile file = new TempFile()) {
 *       //write to the file
 *    }
 * }</pre>
 * The physical file is deleted from the filesystem when the temp file is
 * closed.
 *
 * @since 1.0
 */
public final class TempFile implements Scalar<Path>, Closeable {
    /**
     * Creates the temporary file, returning its path.
     */
    private final Scalar<Path> file;

    /**
     * Ctor.
     * <p>
     * Specifies empty strings for suffix and prefix, and creates the file
     * in the filesystem's temporary directory denoted by the system property
     * {@code java.io.tmpdir}.
     * @since 1.0
     */
    public TempFile() {
        this("", "");
    }

    /**
     * Ctor.
     * <p>
     * The temporary file will be created inside the filesystem's
     * temporary folder (system property: {@code java.io.tmpdir}).
     * @param prefix The temp filename's prefix
     * @param suffix The temp filename's suffix
     * @since 1.0
     */
    public TempFile(final String prefix, final String suffix) {
        this(
            () -> Paths.get(System.getProperty("java.io.tmpdir")),
            prefix,
            suffix
        );
    }

    /**
     * Ctor.
     * @param dir The directory in which to create the temp file
     * @param prefix The temp filename's prefix
     * @param suffix The temp filename's suffix
     * @since 1.0
     */
    public TempFile(
        final Scalar<Path> dir,
        final String prefix,
        final String suffix) {
        this(
            dir,
            new TextOf(prefix),
            new TextOf(suffix)
        );
    }

    /**
     * Ctor.
     * @param dir The directory in which to create the temp file
     * @param prefix The temp filename's prefix
     * @param suffix The temp filename's suffix
     * @since 1.0
     */
    public TempFile(
        final Scalar<Path> dir,
        final Text prefix,
        final Text suffix) {
        this(
            new StickyScalar<>(
                () -> Files.createTempFile(
                    dir.value(),
                    prefix.asString(),
                    suffix.asString()
                )
            )
        );
    }

    /**
     * Primary ctor.
     * @param fullpath Creates the file and returns the path to it
     * @since 1.0
     */
    private TempFile(final Scalar<Path> fullpath) {
        this.file = fullpath;
    }

    @Override
    public Path value() throws Exception {
        return this.file.value();
    }

    /**
     * Deletes the file from the filesystem.
     * @checkstyle NoJavadocForOverriddenMethodsCheck (5 lines)
     * @checkstyle JavadocMethodCheck (5 lines)
     */
    @Override
    public void close() throws IOException {
        Files.delete(new IoCheckedScalar<>(this).value());
    }

}
