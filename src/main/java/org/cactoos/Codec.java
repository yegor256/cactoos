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
package org.cactoos;

import java.io.IOException;

/**
 * Codec.
 *
 * <p>All implementations of this interface must be immutable and thread-safe.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.12
 */
public interface Codec {

    /**
     * Encode text into bytes.
     * @param input The input
     * @return Bytes
     * @throws IOException If fails
     */
    Bytes encode(Text input) throws IOException;

    /**
     * Decode text from byte array (or throw
     * {@link org.cactoos.codec.DecodingException}).
     *
     * <p>This method may throw
     * {@link org.cactoos.codec.DecodingException}, if it's not
     * possible to decode the incoming byte array.
     *
     * @param input The Input
     * @return Text
     * @throws IOException If fails
     */
    Text decode(Bytes input) throws IOException;

}
