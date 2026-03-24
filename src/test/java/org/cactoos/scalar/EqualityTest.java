/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Bytes;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link Equality}.
 * @since 0.31
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class EqualityTest {

    @Test
    void notEqualLeft() {
        MatcherAssert.assertThat(
            "Must compare smaller to greater",
            new Equality<>(
                new EqualityTest.Letters("A"), new EqualityTest.Letters("AB")
            ),
            new HasValue<>(-1)
        );
    }

    @Test
    void notEqualRight() {
        MatcherAssert.assertThat(
            "Must compare greater to smaller",
            new Equality<>(
                new EqualityTest.Letters("AB"), new EqualityTest.Letters("A")
            ),
            new HasValue<>(1)
        );
    }

    @Test
    void notEqualLeftWithSameSize() {
        MatcherAssert.assertThat(
            "Must compare smaller to smaller with same size",
            new Equality<>(
                new EqualityTest.Letters("A"), new EqualityTest.Letters("B")
            ),
            new HasValue<>(-1)
        );
    }

    @Test
    void notEqualRightWithSameSize() {
        MatcherAssert.assertThat(
            "Must compare greater to smaller with same size",
            new Equality<>(
                new EqualityTest.Letters("B"), new EqualityTest.Letters("A")
            ),
            new HasValue<>(1)
        );
    }

    @Test
    void equal() {
        MatcherAssert.assertThat(
            "Must compare equals",
            new Equality<>(
                new EqualityTest.Letters("A"), new EqualityTest.Letters("A")
            ),
            new HasValue<>(0)
        );
    }

    @Test
    void compareEmptyArrays() {
        MatcherAssert.assertThat(
            "Must compare empty",
            new Equality<>(
                new EqualityTest.Letters(""), new EqualityTest.Letters("")
            ),
            new HasValue<>(0)
        );
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
