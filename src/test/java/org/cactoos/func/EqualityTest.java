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
package org.cactoos.func;

import java.nio.ByteBuffer;
import org.cactoos.Bytes;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Equality}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class EqualityTest {
    @Test
    public void notEqualLeft() throws Exception {
        MatcherAssert.assertThat(
            "Can't compare if first object is not equal to second object",
            new Equality<>(
                // @checkstyle MagicNumberCheck (2 line)
                new Weight(400),
                new Weight(500)
            ).value(),
            Matchers.equalTo(-1)
        );
    }

    @Test
    public void notEqualRight() throws Exception {
        MatcherAssert.assertThat(
            "Can't compare if second object is not equal to first object",
            new Equality<>(
                // @checkstyle MagicNumberCheck (2 line)
                new Weight(500),
                new Weight(400)
            ).value(),
            Matchers.equalTo(1)
        );
    }

    @Test
    public void equal() throws Exception {
        MatcherAssert.assertThat(
            "Can't compare if two objects are equals",
            new Equality<>(
                // @checkstyle MagicNumberCheck (2 line)
                new Weight(500),
                new Weight(500)
            ).value(),
            Matchers.equalTo(0)
        );
    }

    /**
     * Support for EqualityTest.
     *
     * @author Yegor Bugayenko (yegor256@gmail.com)
     * @author Fabricio Cabral (fabriciofx@gmail.com)
     * @version $Id$
     * @since 0.12
     */
    private final class Weight implements Bytes {
        /**
         * Kilos.
         */
        private final int kilos;
        /**
         * Ctor.
         * @param kls Kilos
         */
        Weight(final int kls) {
            this.kilos = kls;
        }
        @Override
        public byte[] asBytes() {
            // @checkstyle MagicNumberCheck (1 line)
            return ByteBuffer.allocate(4).putInt(this.kilos).array();
        }
    }

}
