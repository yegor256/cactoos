/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link LSInputOf}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle AbbreviationAsWordInNameCheck (5 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class LSInputOfTest {

    @Test
    void readsSimpleInput() {
        new Assertion<>(
            "Can't read simple input",
            new LSInputOf(
                new InputOf("hello, world!")
            ).getStringData(),
            new StringContains("world!")
        ).affirm();
    }

    @Test
    void readsBiggerInput() {
        final int size = 400_000;
        new Assertion<>(
            "Can't read bigger input",
            new LSInputOf(
                new InputOf(
                    new SlowInputStream(size)
                )
            ).getStringData().length(),
            new IsEqual<>(size)
        ).affirm();
    }

    @Test
    void countsBytesInBiggerInput() {
        final int size = 300_000;
        new Assertion<>(
            "Can't count bytes in a bigger input",
            new LSInputOf(
                new InputOf(
                    new SlowInputStream(size)
                )
            ).getStringData().length(),
            new IsEqual<>(size)
        ).affirm();
    }

}
