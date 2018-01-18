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
 * A file that can only be written to in an atomic way.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @author Ashton Hogan (https://twitter.com/TheAshtonHogan)
 * @version $Id$
 * @since 0.49.2
 */
public final class AtomicFile extends File {

    private static final long serialVersionUID = -276474606113419118L;
    private final File temp;

    /**
     * Ctor.
     *
     * @param pathname that should be written to
     */
    public AtomicFile(final String pathname) {
        super(pathname);
        this.temp = new File(System.getProperty("java.io.tmpdir") + File.separator + this.getName() + "_tmp");
    }

    public Boolean printTempExists() {
        return this.temp.exists();
    }

    public synchronized void write() {
        /**
         * @TODO add a variation of overwrite that appends content to a file
         * instead of replacing all content
         */
    }

    public synchronized void overwrite(final String string, final Charset charset) throws IOException {
        if ((!this.exists()) && (!this.temp.exists())) {
            throw new IOException(this.getClass().getName() + " could not perform write. File and Temp File do not exist:\n" + "File: " + this.getAbsolutePath() + "\n" + "Temp File: " + this.temp.getAbsolutePath());
        }
        InterruptedAtomicFile interruptedAtomicFile = new InterruptedAtomicFile(this);
        if (interruptedAtomicFile.interruptedAfterWrite()) {
            this.temp.renameTo(this);
        }
        if (interruptedAtomicFile.interruptedDuringWrite()) {
            this.temp.delete();
        }
        this.temp.getParentFile().mkdirs();
        this.temp.createNewFile();
        OutputTo outputTo = new OutputTo(this.temp, Boolean.FALSE);
        outputTo.stream().write(string.getBytes(charset));
        this.delete();
        this.temp.renameTo(this);
    }

}
