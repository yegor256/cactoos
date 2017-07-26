/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.func.StickyScalar;
import org.cactoos.func.UncheckedScalar;
import org.cactoos.text.StringAsText;
import org.cactoos.text.UncheckedText;

/**
 * Create a temporary file used only for tests.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.12
 */
public final class TempFile implements Scalar<Path>, Closeable {
    /**
     * The Path of the file.
     */
    private final Scalar<Path> pth;

    /**
     * Ctor.
     */
    public TempFile() {
        this("", "");
    }

    /**
     * Ctor.
     * @param prefix The prefix of the temporary filename
     */
    public TempFile(final String prefix) {
        this(new StringAsText(prefix));
    }

    /**
     * Ctor.
     * @param prefix The prefix of the temporary filename
     */
    public TempFile(final Text prefix) {
        this(prefix, new StringAsText(".tmp"));
    }

    /**
     * Ctor.
     * @param prefix The prefix of the temporary filename
     * @param suffix The suffix of the temporary filename
     */
    public TempFile(final String prefix, final String suffix) {
        this(new StringAsText(prefix), new StringAsText(suffix));
    }

    /**
     * Ctor.
     * @param prefix The prefix of the temporary filename
     * @param suffix The suffix of the temporary filename
     */
    public TempFile(final Text prefix, final Text suffix) {
        this(new UncheckedText(prefix), new UncheckedText(suffix));
    }

    /**
     * Ctor.
     * @param prefix The prefix of the temporary filename
     * @param suffix The suffix of the temporary filename
     */
    public TempFile(final UncheckedText prefix, final UncheckedText suffix) {
        this(
            new StickyScalar<>(
                () -> Files.createTempFile(
                    prefix.asString(),
                    suffix.asString()
                )
            )
        );
    }

    /**
     * Ctor.
     * @param path The path of the temporary file.
     */
    public TempFile(final Scalar<Path> path) {
        this.pth = path;
    }

    @Override
    public void close() throws IOException {
        Files.delete(new UncheckedScalar<>(this).value());
    }

    @Override
    public Path value() throws Exception {
        return this.pth.value();
    }

}
