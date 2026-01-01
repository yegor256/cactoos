/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
import org.cactoos.bytes.BytesOf;

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
    private final Iterable<? extends Path> origin;

    /**
     * Ctor.
     * Usage examples:
     * <pre>
     * {@code
     * new Zip(new Directory(new File("/tmp")));
     * new Zip(new IterableOf<Path>(Paths.get("/tmp/x"), Paths.get("/tmp/y")));
     * }
     * </pre>
     *
     * @param origin Origin {@link Path} list (e. g. a {@link Directory})
     */
    public Zip(final Iterable<? extends Path> origin) {
        this.origin = origin;
    }

    @Override
    @SuppressWarnings("PMD.AvoidFileStream")
    public InputStream stream() throws Exception {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ZipOutputStream zip = new ZipOutputStream(out)) {
            for (final Path path : this.origin) {
                final File file = path.toFile();
                final ZipEntry entry = new ZipEntry(
                    file.getPath()
                );
                zip.putNextEntry(entry);
                if (file.isFile()) {
                    try (
                        FileInputStream input = new FileInputStream(file)
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
