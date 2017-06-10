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
package org.cactoos.text;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * URL as String.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.4
 */
public final class UrlAsString implements Scalar<String> {

    /**
     * The source.
     */
    private final Text source;

    /**
     * The encoding.
     */
    private final Charset encoding;

    /**
     * Ctor.
     * @param url The URL as String
     */
    public UrlAsString(final String url) {
        this(url, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param url The URL as Text
     */
    public UrlAsString(final Text url) {
        this(url, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param url The URL as String
     * @param encoding The encoding
     */
    public UrlAsString(final String url, final Charset encoding) {
        this(new StringAsText(url), encoding);
    }

    /**
     * Ctor.
     * @param url The URL as Text
     * @param encoding The encoding
     */
    public UrlAsString(final Text url, final Charset encoding) {
        this.source = url;
        this.encoding = encoding;
    }

    @Override
    public String asValue() {
        try {
            return URLDecoder.decode(
                this.source.asString(),
                this.encoding.name()
            );
        } catch (final IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

}
