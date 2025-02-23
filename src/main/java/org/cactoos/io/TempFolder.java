/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.RangeOf;
import org.cactoos.iterable.Sorted;
import org.cactoos.proc.ForEach;
import org.cactoos.proc.IoCheckedProc;
import org.cactoos.scalar.IoChecked;
import org.cactoos.scalar.Sticky;
import org.cactoos.text.Concatenated;
import org.cactoos.text.Joined;
import org.cactoos.text.Randomized;
import org.cactoos.text.TextOf;

/**
 * A temporary folder.
 * This is ephemeral folder to be used in small scopes.
 * The physical folder is deleted from the filesystem when the temp folder is
 * closed.
 * @since 1.0
 */
public final class TempFolder implements Scalar<Path>, Closeable {

    /**
     * Creates the temporary folder, returning its path.
     */
    private final Scalar<? extends Path> folder;

    /**
     * Ctor.
     * Creates new folder in temporary directory
     * with a random name.
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public TempFolder() {
        this(
            new Concatenated(
                new TextOf("tmp"),
                new Randomized(
                    new org.cactoos.iterable.Joined<>(
                        new IterableOf<>(
                            new RangeOf<>('0', '9', ch -> (char) (ch + 1)),
                            new RangeOf<>('A', 'Z', ch -> (char) (ch + 1)),
                            new RangeOf<>('a', 'z', ch -> (char) (ch + 1))
                        )
                    ),
                    () -> 5
                )
            )
        );
    }

    /**
     * Ctor.
     * Creates new folder in temporary directory.
     * @param path Relative path to new directory.
     * @since 1.0
     */
    public TempFolder(final String path) {
        this(new TextOf(path));
    }

    /**
     * Ctor.
     * Creates new folder in temporary directory.
     * @param path Relative path to new directory.
     * @since 1.0
     */
    public TempFolder(final Text path) {
        this(
            new Sticky<>(
                () -> Files.createDirectory(
                    Paths.get(
                        new Joined(
                            File.separator,
                            System.getProperty("java.io.tmpdir"),
                            path.asString()
                        ).asString()
                    )
                )
            )
        );
    }

    /**
     * Primary ctor.
     * @param flr Creates the folder and returns the path to it
     * @since 1.0
     */
    private TempFolder(final Scalar<? extends Path> flr) {
        this.folder = flr;
    }

    @Override
    public Path value() throws Exception {
        return this.folder.value();
    }

    @Override
    public void close() throws IOException {
        new IoCheckedProc<>(
            new ForEach<Path>(
                path -> path.toFile().delete()
            )
        ).exec(
            new Sorted<>(
                Comparator.reverseOrder(),
                new Directory(
                    new IoChecked<>(this).value()
                )
            )
        );
    }
}
