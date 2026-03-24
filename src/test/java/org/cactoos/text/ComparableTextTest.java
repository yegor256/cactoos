/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link FormattedText}.
 *
 * @since 0.27
 * @checkstyle JavadocMethodCheck (100 lines)
 */
final class ComparableTextTest {

    @Test
    void comparesWithASubtext() {
        MatcherAssert.assertThat(
            "Can't compare sub texts",
            new ComparableText(
                new TextOf(
                    "here to there"
                )
            ).compareTo(
                new ComparableText(
                    new Sub("from here to there", 5)
                )
            ),
            new IsEqual<>(0)
        );
    }

    @Test
    void comparesToUncheckedText() {
        final String txt = "foobar";
        MatcherAssert.assertThat(
            "These UncheckedText are not equal",
            new ComparableText(
                new UncheckedText(
                    new TextOf(txt)
                )
            ).compareTo(
                new ComparableText(new TextOf(txt))
            ),
            new IsEqual<>(0)
        );
    }

    @Test
    void equalsToItself() {
        final Text text = new TextOf("text");
        MatcherAssert.assertThat(
            "Does not equal to itself",
            text,
            new IsText(text)
        );
    }

    @Test
    void equalsAndHashCodeOfComparableOfTheSameText() {
        final Text text = new TextOf("my text");
        final Text actual = new ComparableText(text);
        final Text expected = new ComparableText(text);
        MatcherAssert.assertThat(
            "Does not equal to a comparable text made from the same Text",
            actual,
            new IsEqual<>(expected)
        );
        MatcherAssert.assertThat(
            "Hash codes of the equal objects are not equal",
            actual.hashCode(),
            new IsEqual<>(
                expected.hashCode()
            )
        );
    }

    @Test
    void equalsOfDifferentText() {
        final Text text = new ComparableText(
            new TextOf("my value")
        );
        MatcherAssert.assertThat(
            "Is equal to the completely different object",
            text,
            new IsNot<>(
                new IsEqual<>(
                    "The string is ignored"
                )
            )
        );
        MatcherAssert.assertThat(
            "Is equal to the completely different text",
            text,
            new IsNot<>(
                new IsText(
                    "The text is ignored"
                )
            )
        );
        MatcherAssert.assertThat(
            "Is equal to the different ComparableText",
            text,
            new IsNot<>(
                new IsEqual<>(
                    new ComparableText(
                        new TextOf("A different text")
                    )
                )
            )
        );
        MatcherAssert.assertThat(
            "The string is equal to the different ComparableText",
            text,
            new IsNot<>(
                new IsText(
                    new ComparableText(
                        new TextOf("A different value")
                    )
                )
            )
        );
    }
}
