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

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import org.cactoos.text.FormattedText;

/**
 * Create an autocloseable temporary file that deletes itself.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@SuppressWarnings("serial")
public final class TempFile extends File implements AutoCloseable {
    /**
     * Ctor.
     * @param pathname The pathname of the file
     */
    public TempFile(final String pathname) {
        super(pathname);
    }

    @Override
    public void close() throws IOException {
        try {
            if (!Files.deleteIfExists(this.toPath())) {
                throw new IOException(
                    new FormattedText(
                        "file '%s' not deleted because it is not exist",
                        this.getPath()
                    ).asString()
                );
            }
        } catch (final DirectoryNotEmptyException ex) {
            throw new IOException(ex);
        } catch (final SecurityException ex) {
            throw new IOException(ex);
        } catch (final InvalidPathException ex) {
            throw new IOException(ex);
        }
    }

}
