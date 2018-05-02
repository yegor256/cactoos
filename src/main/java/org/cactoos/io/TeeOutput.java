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
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.cactoos.Output;

/**
 * Output to Output copying pipe.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.16
 */
public final class TeeOutput implements Output {

    /**
     * The target.
     */
    private final Output target;

    /**
     * The copy.
     */
    private final Output copy;

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     * @param charset The charset
     */
    public TeeOutput(final Output tgt, final Writer cpy,
        final Charset charset) {
        this(tgt, new OutputTo(cpy, charset));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     */
    public TeeOutput(final Output tgt, final Writer cpy) {
        this(tgt, new OutputTo(cpy));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     */
    public TeeOutput(final Output tgt, final Path cpy) {
        this(tgt, new OutputTo(cpy));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     */
    public TeeOutput(final Output tgt, final File cpy) {
        this(tgt, new OutputTo(cpy));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     */
    public TeeOutput(final Output tgt, final OutputStream cpy) {
        this(tgt, new OutputTo(cpy));
    }

    /**
     * Ctor.
     * @param tgt The target
     * @param cpy The copy destination
     */
    public TeeOutput(final Output tgt, final Output cpy) {
        this.target = tgt;
        this.copy = cpy;
    }

    @Override
    public OutputStream stream() throws IOException {
        return new TeeOutputStream(
            this.target.stream(), this.copy.stream()
        );
    }

}
