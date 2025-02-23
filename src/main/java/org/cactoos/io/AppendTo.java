/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.cactoos.Output;

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
    private final File source;

    /**
     * Ctor.
     * @param path Path as a source of a File.
     */
    public AppendTo(final Path path) {
        this(path.toFile());
    }

    /**
     * Ctor.
     * @param src File to which content will be appended.
     */
    public AppendTo(final File src) {
        this.source = src;
    }

    @Override
    public OutputStream stream() throws Exception {
        return Files.newOutputStream(
            this.source.toPath(), StandardOpenOption.APPEND
        );
    }
}
