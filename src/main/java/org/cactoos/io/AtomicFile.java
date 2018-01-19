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
import java.nio.file.Files;
import org.cactoos.text.JoinedText;

/**
 * A file that can only be written to in an atomic way.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @author Ashton Hogan (info@ashtonhogan.com)
 * @version $Id$
 * @since 0.49.2
 */
public final class AtomicFile extends File {

    private static final long serialVersionUID = -276474606113419118L;

    /**
     * Temp file absolute path.
     */
    private final JoinedText joinedText;

    /**
     * Ctor.
     *
     * @param pathname Written to and replaced
     */
    public AtomicFile(final String pathname) {
        super(pathname);
        this.joinedText = new JoinedText(
                    "",
                System.getProperty("java.io.tmpdir"),
                this.getName(),
                "_tmp"
        );
    }

    public Boolean printTempExists() throws IOException {
        return new File(this.joinedText.asString()).exists();
    }

    /**
     * @TODO Appends content instead of overwriting it
     */
    public static synchronized void write() {
        
    }

    public synchronized void overwrite(final String content,
            final Charset charset) throws IOException, InterruptedException {
        final File tmp = new File(this.joinedText.asString());
        final InterruptedAtomicFile interrupted
                    = new InterruptedAtomicFile(this);
        if (interrupted.interruptedAfterWrite()) {
            tmp.renameTo(this);
        }
        if (interrupted.interruptedDuringWrite()) {
            tmp.delete();
        }
        if (interrupted.interruptedBeforeCreation()) {
            this.getParentFile().mkdirs();
            this.createNewFile();
        }
        tmp.getParentFile().mkdirs();
        tmp.createNewFile();
        final OutputTo outputTo = new OutputTo(tmp, Boolean.FALSE);
        outputTo.stream().write(content.getBytes(charset));
        this.move(tmp, this);
    }

    public boolean move(final File file1, final File file2) throws IOException,
            InterruptedException {
        try {
            Files.move(file1.toPath(), file2.toPath(), 
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (final java.nio.file.FileSystemException ex) {
            /**
             * @TODO this method should be removed
             * but for some reason java takes
             * like 20 seconds to release the lock on tmp 
             * after writing content to it
             */
            int sleepDuration = 100;
            Thread.sleep(sleepDuration);
            this.move(file1, file2);
        }
        return true;
    }

}
