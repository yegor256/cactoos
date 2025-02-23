/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.IoChecked;
import org.cactoos.scalar.Sticky;
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
     * <p>
     * The temporary file will be created inside the filesystem's
     * temporary folder (system property: {@code java.io.tmpdir}).
     * @param prefix The temp filename's prefix
     * @param suffix The temp filename's suffix
     * @since 1.0
     */
    public TempFile(final Text prefix, final Text suffix) {
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
            new Sticky<>(
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
        Files.delete(new IoChecked<>(this).value());
    }

}
