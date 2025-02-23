/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
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

/**
 * Files and folders in a directory.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.21
 */
public final class Directory implements Iterable<Path> {

    /**
     * Path of the directory.
     */
    private final Path dir;

    /**
     * Ctor.
     * @param file File as a path to directory.
     */
    public Directory(final File file) {
        this(file.toPath());
    }

    /**
     * Ctor.
     * @param path Path of the dir
     */
    public Directory(final Path path) {
        this.dir = path;
    }

    @Override
    public Iterator<Path> iterator() {
        try (Stream<Path> files = Files.walk(this.dir)) {
            return files.collect(Collectors.toList()).iterator();
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
