/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class TempFolder implements Scalar<Path>, Closeable {

    /**
     * Creates the temporary folder, returning its path.
     */
    private final Scalar<Path> folder;

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
                            new RangeOf<>('0', '9', ch -> ++ch),
                            new RangeOf<>('A', 'Z', ch -> ++ch),
                            new RangeOf<>('a', 'z', ch -> ++ch)
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
    private TempFolder(final Scalar<Path> flr) {
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
