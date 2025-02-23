/*
 * The MIT License (MIT)
 *
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
 * Test case for {@link Md5DigestOf}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class Md5DigestOfTest {

    @Test
    void checksumOfEmptyString() {
        new Assertion<>(
            "Can't calculate the empty string's MD5 checksum",
            new HexOf(
                new Md5DigestOf(
                    new InputOf("")
                )
            ),
            new HasString(
                "d41d8cd98f00b204e9800998ecf8427e"
            )
        ).affirm();
    }

    @Test
    void checksumOfString() {
        new Assertion<>(
            "Can't calculate the string's MD5 checksum",
            new HexOf(
                new Md5DigestOf(
                    new InputOf("Hello World!")
                )
            ),
            new HasString(
                "ed076287532e86365e841e92bfc50d8c"
            )
        ).affirm();
    }

    @Test
    void checksumFromFile() throws Exception {
        new Assertion<>(
            "Can't calculate the file's MD5 checksum",
            new HexOf(
                new Md5DigestOf(
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
                "e41bf9d7cd310eabd8e1c03fc4a52d32"
            )
        ).affirm();
    }

}
