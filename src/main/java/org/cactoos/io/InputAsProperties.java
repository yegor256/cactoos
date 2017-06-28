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
import java.util.Properties;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.text.StringAsText;
import org.cactoos.text.TextAsBytes;

/**
 * Input as {@link Properties}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.8
 */
public final class InputAsProperties implements Scalar<Properties> {

    /**
     * The input.
     */
    private final Input source;

    /**
     * Ctor.
     * @param text Text
     * @since 0.9
     */
    public InputAsProperties(final String text) {
        this(new StringAsText(text));
    }

    /**
     * Ctor.
     * @param text Text
     * @since 0.9
     */
    public InputAsProperties(final Text text) {
        this(new TextAsBytes(text));
    }

    /**
     * Ctor.
     * @param bytes Bytes
     * @since 0.9
     */
    public InputAsProperties(final Bytes bytes) {
        this(new BytesAsInput(bytes));
    }

    /**
     * Ctor.
     * @param input The input
     */
    public InputAsProperties(final Input input) {
        this.source = input;
    }

    @Override
    public Properties value() throws IOException {
        final Properties props = new Properties();
        try (final InputStream input = this.source.stream()) {
            props.load(input);
        }
        return props;
    }
}
