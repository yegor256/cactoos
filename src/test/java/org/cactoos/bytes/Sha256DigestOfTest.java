/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
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
 * Test case for {@link Sha256DigestOf}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class Sha256DigestOfTest {

    @Test
    void checksumOfEmptyString() {
        new Assertion<>(
            "Can't calculate the empty string's SHA-256 checksum",
            new HexOf(
                new Sha256DigestOf(
                    new InputOf("")
                )
            ),
            new HasString(
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
            )
        ).affirm();
    }

    @Test
    void checksumOfString() {
        new Assertion<>(
            "Can't calculate the string's SHA-256 checksum",
            new HexOf(
                new Sha256DigestOf(
                    new InputOf("Hello World!")
                )
            ),
            new HasString(
                "7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069"
            )
        ).affirm();
    }

    @Test
    void checksumFromFile() throws Exception {
        new Assertion<>(
            "Can't calculate the file's SHA-256 checksum",
            new HexOf(
                new Sha256DigestOf(
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
                "6b9da8f528f2c6523f292c2aa37be6befdd560ff917ed60836d63778ec04b46b"
            )
        ).affirm();
    }

}
