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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import org.cactoos.Bytes;
import org.cactoos.Codec;
import org.cactoos.Text;
import org.cactoos.text.ArrayAsBytes;

/**
 * Base64 codec.
 * <p>
 * <p>The class is immutable and thread-safe.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.12
 */
public final class Base64Codec implements Codec {

    /**
     * All legal Base64 chars.
     */
    private static final String BASE64CHARS =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    /**
     * Original codec.
     */
    private final Codec origin;

    /**
     * Ctor.
     *
     * @param codec Original codec
     */
    public Base64Codec(final Codec codec) {
        this.origin = codec;
    }

    @Override
    public Bytes encode(final Text input) throws IOException {
        final byte[] encode = Base64.getEncoder().encode(this.origin.encode(
            input
            ).asBytes()
        );
        return new ArrayAsBytes(encode);
    }

    @Override
    public Text decode(final Bytes bytes) throws IOException {
        final byte[] illegal = Base64Codec.checkIllegalCharacters(bytes
            .asBytes()
        );
        if (illegal.length > 0) {
            throw new DecodingException(
                String.format(
                    "Illegal character in Base64 encoded data. %s",
                    Arrays.toString(illegal)
                )
            );
        }
        return this.origin.decode(
            new ArrayAsBytes(
            Base64.getDecoder().decode(
            bytes.asBytes()
            )
            )
        );
    }

    /**
     * Check the byte array for non-Base64 characters.
     *
     * @param bytes The values to check
     * @return An array of the found non-Base64 characters.
     */
    private static byte[] checkIllegalCharacters(final byte[] bytes) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int pos = 0; pos < bytes.length; ++pos) {
            if (BASE64CHARS.indexOf(bytes[pos]) < 0) {
                out.write(bytes[pos]);
            }
        }
        return out.toByteArray();
    }

}
