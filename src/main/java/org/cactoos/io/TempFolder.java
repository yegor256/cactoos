/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.IoChecked;
import org.cactoos.scalar.Sticky;
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
    private final Scalar<Path> folder;

    /**
     * Ctor.
     * Creates new folder in temporary directory
     * with a random name.
     * @since 1.0
     */
    public TempFolder() {
        this(
            new Joined(
                new TextOf(""),
                new TextOf("tmp"),
                new Randomized(
                    // @checkstyle MagicNumber (1 line)
                    5,
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                    'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z'
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
        Files.delete(new IoChecked<>(this).value());
    }
}
