/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.io.IOException;
import org.cactoos.Text;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.StringContains;
import org.hamcrest.object.HasToString;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link UncheckedText}.
 *
 * @since 0.3
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class UncheckedTextTest {
    @Test
    void rethrowsCheckedToUncheckedException() {
        final String msg = "intended";
        new Assertion<>(
            "Must throw an exception when something goes wrong",
            new UncheckedText(
                () -> {
                    throw new IOException(msg);
                }
            )::asString,
            new Throws<>(
                new StringContains(msg),
                RuntimeException.class
            )
        ).affirm();
    }

    @Test
    void toStringMustReturnSameOfAsString() {
        final String text = "one";
        new Assertion<>(
            "Must implement #toString which returns the same of #asString",
            new UncheckedText(
                new TextOf(text)
            ),
            new HasToString<>(
                new IsEqual<>(text)
            )
        ).affirm();
    }

    @Test
    void equalsToTheSameTextObject() {
        final Text text = new TextOf("anything");
        new Assertion<>(
            "Must match text representing the same value",
            new UncheckedText(text),
            new IsEqual<>(text)
        ).affirm();
    }

    @Test
    void equalsOtherTextRepresentingTheSameValue() {
        new Assertion<>(
            "Must match another text representing the same value",
            new UncheckedText(
                new TextOf("abcdefghijkl")
            ),
            new IsEqual<>(
                new Concatenated("ab", "cde", "fghi", "j", "kl")
            )
        ).affirm();
    }

    @Test
    void equalsNonTextObject() {
        new Assertion<>(
            "Must does not match another object which is not a string",
            new UncheckedText(
                new TextOf("is not equals to null")
            ),
            new IsNot<>(
                new IsEqual<>(new Object())
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("PMD.EqualsNull")
    void notEqualsWhenAnObjectIsNull() {
        new Assertion<>(
            "Must match equals null",
            new UncheckedText(
                new TextOf("is not equals to not Text object")
            ).equals(null),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void matchTheSameHashCode() {
        final String text = "hashCode";
        new Assertion<>(
            "Must match its represented String hashcode",
            new UncheckedText(
                new TextOf(text)
            ).hashCode(),
            new IsEqual<>(text.hashCode())
        ).affirm();
    }
}
