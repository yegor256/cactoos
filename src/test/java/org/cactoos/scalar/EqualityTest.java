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
package org.cactoos.scalar;

import org.cactoos.Bytes;
import org.cactoos.matchers.ScalarHasValue;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link Equality}.
 * @author Vedran Vatavuk (123vgv@gmail.com)
 * @version $Id$
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class EqualityTest {

    @Test
    public void notEqualLeft() throws Exception {
        MatcherAssert.assertThat(
            new Equality<>(
                new EqualityTest.Letters("A"), new EqualityTest.Letters("AB")
            ),
            new ScalarHasValue<>(-1)
        );
    }

    @Test
    public void notEqualRight() throws Exception {
        MatcherAssert.assertThat(
            new Equality<>(
                new EqualityTest.Letters("AB"), new EqualityTest.Letters("A")
            ),
            new ScalarHasValue<>(1)
        );
    }

    @Test
    public void notEqualLeftWithSameSize() throws Exception {
        MatcherAssert.assertThat(
            new Equality<>(
                new EqualityTest.Letters("A"), new EqualityTest.Letters("B")
            ),
            new ScalarHasValue<>(-1)
        );
    }

    @Test
    public void notEqualRightWithSameSize() throws Exception {
        MatcherAssert.assertThat(
            new Equality<>(
                new EqualityTest.Letters("B"), new EqualityTest.Letters("A")
            ),
            new ScalarHasValue<>(1)
        );
    }

    @Test
    public void equal() throws Exception {
        MatcherAssert.assertThat(
            new Equality<>(
                new EqualityTest.Letters("A"), new EqualityTest.Letters("A")
            ),
            new ScalarHasValue<>(0)
        );
    }

    @Test
    public void compareEmptyArrays() throws Exception {
        MatcherAssert.assertThat(
            new Equality<>(
                new EqualityTest.Letters(""), new EqualityTest.Letters("")
            ),
            new ScalarHasValue<>(0)
        );
    }

    /**
     * Weight.
     */
    private static final class Letters implements Bytes {

        /**
         * Bytes.
         */
        private final String text;

        /**
         * Ctor.
         * @param txt Text
         */
        Letters(final String txt) {
            this.text = txt;
        }

        @Override
        public byte[] asBytes() {
            return this.text.getBytes();
        }
    }
}
