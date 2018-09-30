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
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.scalar.Ternary;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Children files for the particular {@link File}.
 *
 * @since 0.37
 */
public final class ChildrenOf implements Iterable<File> {

    /**
     * Origin.
     */
    private final UncheckedScalar<Iterable<File>> src;

    /**
     * Ctor.
     * @param parent The parent file.
     */
    public ChildrenOf(final File parent) {
        this(
            () -> new Ternary<List<File>>(
                () -> parent == null
                    || parent.isFile()
                    || parent.listFiles() == null,
                Collections::emptyList,
                () -> Collections.unmodifiableList(
                    Arrays.asList(parent.listFiles())
                )
            ).value()
        );
    }

    /**
     * Ctor.
     * @param src The parent file.
     */
    public ChildrenOf(final Scalar<Iterable<File>> src) {
        this.src = new UncheckedScalar<>(src);
    }

    @Override
    public Iterator<File> iterator() {
        return this.src.value().iterator();
    }

}
