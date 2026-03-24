/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.io.IOException;
import org.cactoos.Text;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.StringContains;
import org.hamcrest.object.HasToString;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedText}.
 *
 * @since 0.51
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class IoCheckedTextTest {

    @Test
    void rethrowsCheckedIoException() {
        final String msg = "intended IO error";
        MatcherAssert.assertThat(
            "Must throw same IO exception",
            new IoCheckedText(
                () -> {
                    throw new IOException(msg);
                }
            )::asString,
            new Throws<>(
                new StringContains(msg),
                IOException.class
            )
        );
    }

    @Test
    void rethrowsCheckedToUncheckedException() {
        final String msg = "intended";
        MatcherAssert.assertThat(
            "Must throw an exception when something goes wrong",
            new IoCheckedText(
                () -> {
                    throw new Exception(msg);
                }
            )::asString,
            new Throws<>(
                new StringContains(msg),
                RuntimeException.class
            )
        );
    }

    @Test
    void toStringMustReturnSameOfAsString() {
        final String text = "one";
        MatcherAssert.assertThat(
            "Must implement #toString which returns the same of #asString",
            new IoCheckedText(
                new TextOf(text)
            ),
            new HasToString<>(
                new IsEqual<>(text)
            )
        );
    }

    @Test
    void equalsToTheSameTextObject() {
        final Text text = new TextOf("anything");
        MatcherAssert.assertThat(
            "Must match text representing the same value",
            new IoCheckedText(text),
            new IsEqual<>(text)
        );
    }

    @Test
    void equalsOtherTextRepresentingTheSameValue() {
        MatcherAssert.assertThat(
            "Must match another text representing the same value",
            new IoCheckedText(
                new TextOf("abcdefghijkl")
            ),
            new IsEqual<>(
                new Concatenated("ab", "cde", "fghi", "j", "kl")
            )
        );
    }

    @Test
    void equalsNonTextObject() {
        MatcherAssert.assertThat(
            "Must does not match another object which is not a string",
            new IoCheckedText(
                new TextOf("is not equals to null")
            ),
            new IsNot<>(
                new IsEqual<>(new Object())
            )
        );
    }

    @Test
    void notEqualsWhenAnObjectIsNull() {
        MatcherAssert.assertThat(
            "Must match equals null",
            new IoCheckedText(
                new TextOf("is not equals to not Text object")
            ),
            new IsNot<>(new IsEqual<>(null))
        );
    }

    @Test
    void matchTheSameHashCode() {
        final String text = "hashCode";
        MatcherAssert.assertThat(
            "Must match its represented String hashcode",
            new IoCheckedText(
                new TextOf(text)
            ).hashCode(),
            new IsEqual<>(text.hashCode())
        );
    }
}
