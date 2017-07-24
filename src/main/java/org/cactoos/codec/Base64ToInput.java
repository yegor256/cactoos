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
package org.cactoos.codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import org.cactoos.Input;
import org.cactoos.io.InputAsBytes;
import org.cactoos.text.BytesAsText;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;

/**
 * Base64 to Input.
 * <p>
 * <p>The class is immutable and thread-safe.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.12
 */
public final class Base64ToInput implements Input {

    /**
     * All legal Base64 chars.
     */
    private static final String BASE64CHARS =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    /**
     * Original input.
     */
    private final Input origin;

    /**
     * Ctor.
     *
     * @param input Original
     */
    public Base64ToInput(final Input input) {
        this.origin = input;
    }

    @Override
    public InputStream stream() throws IOException {
        final byte[] bytes = new InputAsBytes(this.origin).asBytes();
        final byte[] illegal = Base64ToInput.getIllegalCharacters(bytes);
        if (illegal.length > 0) {
            throw new DecodingException(
                new FormattedText(
                    "Illegal character in Base64 encoded data. %s",
                    1, new UncheckedText(new BytesAsText(illegal)).asString()
                ).asString()
            );
        }
        return new ByteArrayInputStream(Base64.getDecoder().decode(bytes));
    }

    /**
     * Get the byte array for non-Base64 characters.
     * @param bytes Byte Array
     * @return An array of the found non-Base64 characters.
     * @throws IOException If fails
     */
    private static byte[] getIllegalCharacters(final byte[] bytes) throws
        IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (int pos = 0; pos < bytes.length; ++pos) {
            if (BASE64CHARS.indexOf(bytes[pos]) < 0) {
                output.write(bytes[pos]);
            }
        }
        return output.toByteArray();
    }

}
