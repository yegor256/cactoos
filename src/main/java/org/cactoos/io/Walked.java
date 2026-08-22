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
 * Walked file tree.
 *
 * <p>An Elegant equivalent of
 * {@link Files#walk(Path, int, java.nio.file.FileVisitOption...)},
 * iterating every path under a starting directory up to a given depth.
 * Use {@link Directory} when the walk should be unbounded.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
public final class Walked implements Iterable<Path> {

    /**
     * Path of the starting directory.
     */
    private final Scalar<Path> start;

    /**
     * Maximum depth to walk.
     */
    private final int depth;

    /**
     * Ctor.
     * @param file File as a path to the starting directory
     * @param max Maximum number of directory levels to visit
     */
    public Walked(final File file, final int max) {
        this(file::toPath, max);
    }

    /**
     * Ctor.
     * @param path Path of the starting directory
     * @param max Maximum number of directory levels to visit
     */
    public Walked(final Path path, final int max) {
        this(() -> path, max);
    }

    /**
     * Ctor.
     * @param source Path of the starting directory, deferred
     * @param max Maximum number of directory levels to visit
     */
    private Walked(final Scalar<Path> source, final int max) {
        this.start = source;
        this.depth = max;
    }

    @Override
    public Iterator<Path> iterator() {
        try (Stream<Path> paths = Files.walk(new Unchecked<>(this.start).value(), this.depth)) {
            return paths.collect(Collectors.toList()).iterator();
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
