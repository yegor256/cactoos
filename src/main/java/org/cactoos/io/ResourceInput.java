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

import java.io.IOException;
import java.io.InputStream;
import org.cactoos.Input;
import org.cactoos.text.Sprintf;

/**
 * Jar class resource.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @see ClassLoader#getResource(String)
 * @since 0.1
 */
public final class ResourceInput implements Input {

    /**
     * Resource name.
     */
    private final String res;

    /**
     * Resource class loader.
     */
    private final ClassLoader loader;

    /**
     * New resource input with current context {@link ClassLoader}.
     *
     * @param res Resource name
     */
    public ResourceInput(final String res) {
        this(
            res,
            Thread.currentThread().getContextClassLoader()
        );
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     *
     * @param res Resource name
     * @param loader Resource class loader
     */
    public ResourceInput(final String res, final ClassLoader loader) {
        this.res = res;
        this.loader = loader;
    }

    @Override
    public InputStream open() throws IOException {
        final InputStream input = this.loader.getResourceAsStream(this.res);
        if (input == null) {
            throw new IOException(
                new Sprintf(
                    "Resource '%s' was not found",
                    this.res
                ).toString()
            );
        }
        return input;
    }
}
