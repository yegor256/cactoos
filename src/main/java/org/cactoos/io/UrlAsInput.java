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
import java.net.URI;
import java.net.URL;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.func.IoCheckedScalar;

/**
 * URL as Input.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class UrlAsInput implements Input {

    /**
     * The URL.
     */
    private final Scalar<URL> source;

    /**
     * Ctor.
     * @param url The URL
     * @since 0.6
     */
    public UrlAsInput(final String url) {
        this(() -> new URL(url));
    }

    /**
     * Ctor.
     * @param url The URL
     * @since 0.6
     */
    public UrlAsInput(final URI url) {
        this(url::toURL);
    }

    /**
     * Ctor.
     * @param url The URL
     */
    public UrlAsInput(final URL url) {
        this(() -> url);
    }

    /**
     * Ctor.
     * @param src Source
     */
    public UrlAsInput(final Scalar<URL> src) {
        this.source = src;
    }

    @Override
    public InputStream stream() throws IOException {
        return new IoCheckedScalar<>(this.source).asValue().openStream();
    }

}
