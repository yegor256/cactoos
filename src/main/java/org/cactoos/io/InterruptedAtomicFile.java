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

import java.io.IOException;

/**
 * A file that may have been interrupted
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @author Ashton Hogan (info@ashtonhogan.com)
 * @version $Id$
 * @since 0.49.2
 */
public final class InterruptedAtomicFile {

    private static final long serialVersionUID = -1682046414065640315L;

   /**
    * File that can be checked for previous interruptions.
    */
    private final AtomicFile atomicFile;

    /**
     * Ctor.
     *
     * @param atomicFile File that can be checked for previous interruptions
     */
    public InterruptedAtomicFile(final AtomicFile atomicFile) {
        this.atomicFile = atomicFile;
    }

    public Boolean interruptedAfterWrite() throws IOException {
        return ((!this.atomicFile.exists())
                && (this.atomicFile.printTempExists()));
    }

    public Boolean interruptedDuringWrite() throws IOException {
        return ((this.atomicFile.exists())
                && (this.atomicFile.printTempExists()));
    }

    public Boolean interruptedBeforeCreation() throws IOException {
        return ((!this.atomicFile.exists())
                && (!this.atomicFile.printTempExists()));
    }

}
