/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.cactoos.Scalar;
import org.cactoos.scalar.Unchecked;

/**
 * Files and folders in a directory.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.21
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
public final class Directory implements Iterable<Path> {

    /**
     * Path of the directory.
     */
    private final Scalar<Path> dir;

    /**
     * Ctor.
     * @param file File as a path to directory
     */
    public Directory(final File file) {
        this(file::toPath);
    }

    /**
     * Ctor.
     * @param path Path of the dir
     */
    public Directory(final Path path) {
        this(() -> path);
    }

    /**
     * Ctor.
     * @param source Path of the directory, deferred
     */
    private Directory(final Scalar<Path> source) {
        this.dir = source;
    }

    @Override
    public Iterator<Path> iterator() {
        try (Stream<Path> files = Files.walk(new Unchecked<>(this.dir).value())) {
            return files.collect(Collectors.toList()).iterator();
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
