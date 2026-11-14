/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link AbbreviatedLeft}.
 *
 * @since 0.58.0
 */
final class AbbreviatedLeftTest {

    @Test
    void abbreviatesTextFromLeft() throws Exception {
        MatcherAssert.assertThat(
            "Can't abbreviate a text from the left",
            new AbbreviatedLeft(
                "The quick brown fox jumps over the lazy dog",
                20
            ).asString(),
            new IsEqual<>("...over the lazy dog")
        );
    }

    @Test
    void abbreviatesTextFromLeftWithDefaultWidth() throws Exception {
        final String text = "The quick brown fox jumps over the lazy dog";
        MatcherAssert.assertThat(
            "Can't abbreviate a text from the left with default width",
            new AbbreviatedLeft(text).asString(),
            new IsEqual<>(text)
        );
    }

    @Test
    void doesNotAbbreviateShortText() throws Exception {
        final String text = "Short text";
        MatcherAssert.assertThat(
            "Can't preserve a short text",
            new AbbreviatedLeft(text, 20).asString(),
            new IsEqual<>(text)
        );
    }

    @Test
    void abbreviatesTextWithExactMaxWidth() throws Exception {
        final String text = "Exactly twenty chars";
        MatcherAssert.assertThat(
            "Can't handle text with exact max width",
            new AbbreviatedLeft(text, 20).asString(),
            new IsEqual<>(text)
        );
    }

    @Test
    void handlesEmptyText() throws Exception {
        MatcherAssert.assertThat(
            "Can't handle empty text",
            new AbbreviatedLeft("", 20).asString(),
            new IsEqual<>("")
        );
    }
}
