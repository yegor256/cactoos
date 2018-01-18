/**
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
 */
package org.cactoos.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * A file that can only be written to in an atomic fashion.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @author Ashton Hogan (https://twitter.com/TheAshtonHogan)
 * @version $Id$
 * @since 0.49.2
 */
public final class AtomicFile {

    /**
     * File that should be written to.
     */
    private final File file;

    /**
     * Ctor.
     *
     * @param file File that should be written to
     */
    public AtomicFile(final File file) {
        this.file = file;
    }

    public synchronized void write(final String string, final Charset charset) throws IOException {
        File tempFile = new File(System.getProperty("java.io.tmpdir") + File.separator + this.file.getName() + "_tmp");
        if ((!this.file.exists()) && (!tempFile.exists())) {
            throw new IOException(this.getClass().getName() + " could not perform write. File and Temp File do not exist:\n" + "File: " + this.file.getAbsolutePath() + "\n" + "Temp File: " + tempFile.getAbsolutePath());
        }
        if ((!this.file.exists()) && (tempFile.exists())) {
            tempFile.renameTo(this.file);
        }
        if ((this.file.exists()) && (tempFile.exists())) {
            tempFile.delete();
        }
        OutputTo outputTo = new OutputTo(tempFile, Boolean.TRUE);
        outputTo.stream().write(string.getBytes(charset));
        this.file.delete();
        tempFile.renameTo(this.file);
    }

}
