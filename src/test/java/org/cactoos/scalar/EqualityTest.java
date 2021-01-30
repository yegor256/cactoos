/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Equality}.
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
final class EqualityTest {

    @Test
    void notEqualLeft() throws Exception {
        new Assertion<>(
            "Must compare smaller to greater",
            new Equality<>(
                new EqualityTest.Letters("A"), new EqualityTest.Letters("AB")
            ),
            new HasValue<>(-1)
        ).affirm();
    }

    @Test
    void notEqualRight() throws Exception {
        new Assertion<>(
            "Must compare greater to smaller",
            new Equality<>(
                new EqualityTest.Letters("AB"), new EqualityTest.Letters("A")
            ),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void notEqualLeftWithSameSize() throws Exception {
        new Assertion<>(
            "Must compare smaller to smaller with same size",
            new Equality<>(
                new EqualityTest.Letters("A"), new EqualityTest.Letters("B")
            ),
            new HasValue<>(-1)
        ).affirm();
    }

    @Test
    void notEqualRightWithSameSize() throws Exception {
        new Assertion<>(
            "Must compare greater to smaller with same size",
            new Equality<>(
                new EqualityTest.Letters("B"), new EqualityTest.Letters("A")
            ),
            new HasValue<>(1)
        ).affirm();
    }

    @Test
    void equal() throws Exception {
        new Assertion<>(
            "Must compare equals",
            new Equality<>(
                new EqualityTest.Letters("A"), new EqualityTest.Letters("A")
            ),
            new HasValue<>(0)
        ).affirm();
    }

    @Test
    void compareEmptyArrays() throws Exception {
        new Assertion<>(
            "Must compare empty",
            new Equality<>(
                new EqualityTest.Letters(""), new EqualityTest.Letters("")
            ),
            new HasValue<>(0)
        ).affirm();
    }

    /**
     * Weight.
     * @since 0.31
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
