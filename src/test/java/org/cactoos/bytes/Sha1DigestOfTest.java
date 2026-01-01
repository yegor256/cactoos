/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import org.cactoos.io.InputOf;
import org.cactoos.io.ResourceOf;
import org.cactoos.io.Sticky;
import org.cactoos.text.HexOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasString;

/**
 * Test case for {@link Sha1DigestOf}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class Sha1DigestOfTest {

    @Test
    void checksumOfEmptyString() {
        new Assertion<>(
            "Can't calculate the empty string's SHA-1 checksum",
            new HexOf(
                new Sha1DigestOf(
                    new InputOf("")
                )
            ),
            new HasString(
                "da39a3ee5e6b4b0d3255bfef95601890afd80709"
            )
        ).affirm();
    }

    @Test
    void checksumOfString() {
        new Assertion<>(
            "Can't calculate the string's SHA-1 checksum",
            new HexOf(
                new Sha1DigestOf(
                    new InputOf("Hello World!")
                )
            ),
            new HasString(
                "2ef7bde608ce5404e97d5f042f95f89f1c232871"
            )
        ).affirm();
    }

    @Test
    void checksumFromFile() throws Exception {
        new Assertion<>(
            "Can't calculate the file's SHA-1 checksum",
            new HexOf(
                new Sha1DigestOf(
                    new Sticky(
                        new InputOf(
                            new ResourceOf(
                                "org/cactoos/digest-calculation.txt"
                            ).stream()
                        )
                    )
                )
            ),
            new HasString(
                "1ef83a80a51e14e72c4face0c928951d88b96acc"
            )
        ).affirm();
    }

}
