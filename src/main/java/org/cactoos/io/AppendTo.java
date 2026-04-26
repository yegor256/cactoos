/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.cactoos.Output;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Output that appends content to a given file.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0
 */
public final class AppendTo implements Output {

    /**
     * Source of the output.
     */
    private final Scalar<Path> source;

    /**
     * Ctor.
     * @param path Path as a source of a File
     */
    public AppendTo(final Path path) {
        this(() -> path);
    }

    /**
     * Ctor.
     * @param src File to which content will be appended
     */
    public AppendTo(final File src) {
        this(src::toPath);
    }

    /**
     * Ctor.
     * @param src Source of the output, deferred
     */
    private AppendTo(final Scalar<Path> src) {
        this.source = src;
    }

    @Override
    public OutputStream stream() throws Exception {
        return Files.newOutputStream(
            new Unchecked<>(this.source).value(), StandardOpenOption.APPEND
        );
    }
}
