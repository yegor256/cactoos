/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
 *
 */

package org.cactoos.io.file;

import java.io.File;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Joined;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.Ternary;

/**
 * Map hierarchical tree of files to the collection.
 *
 * @since 0.37
 */
public final class Hierarchy extends IterableEnvelope<File> {

    /**
     * Ctor.
     * @param root The root file of the tree.
     */
    public Hierarchy(final String root) {
        this(() -> new File(root));
    }

    /**
     * Ctor.
     * @param roots The root/parent files of the trees.
     */
    public Hierarchy(final File... roots) {
        this(new ListOf<>(roots));
    }

    /**
     * Ctor.
     * @param roots The root/parent files of the trees.
     */
    @SafeVarargs
    public Hierarchy(final Scalar<File>... roots) {
        this(new org.cactoos.iterable.Mapped<>(Scalar::value, roots));
    }

    /**
     * Ctor.
     * @param roots The root/parent files of the trees.
     * @checkstyle IndentationCheck (20 lines)
     */
    public Hierarchy(final Iterable<File> roots) {
        super(
            () -> new Joined<>(
                new Mapped<>(
                    root -> new Ternary<>(
                        root::isFile,
                        () -> new IterableOf<>(root),
                        () -> new Hierarchy(new ChildrenOf(root))
                    ).value(),
                    roots
                )
            )
        );
    }

}
