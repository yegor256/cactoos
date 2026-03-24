/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Text;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link AbbreviatedRight}.
 * @since 0.58.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class AbbreviatedRightTest {

    @Test
    void abbreviatesAnEmptyText() {
        final String msg = "";
        MatcherAssert.assertThat(
            "Must abbreviate an msg text",
            new AbbreviatedRight(msg, 8),
            new IsText(msg)
        );
    }

    @Test
    void abbreviatesText() {
        MatcherAssert.assertThat(
            "Must abbreviate a text",
            new AbbreviatedRight("hello world", 8),
            new IsText("hello...")
        );
    }

    @Test
    void abbreviatesTextOneCharSmaller() {
        MatcherAssert.assertThat(
            "Must abbreviate a text one char smaller",
            new AbbreviatedRight("oo programming", 10),
            new IsText("oo prog...")
        );
    }

    @Test
    void abbreviatesTextWithSameLength() {
        final String msg = "elegant objects";
        MatcherAssert.assertThat(
            "Must abbreviate a text with same length",
            new AbbreviatedRight(msg, 15),
            new IsText(msg)
        );
    }

    @Test
    void abbreviatesTextOneCharBigger() {
        final String msg = "the old mcdonald";
        MatcherAssert.assertThat(
            "Must abbreviate a text one char bigger",
            new AbbreviatedRight(msg, 17),
            new IsText(msg)
        );
    }

    @Test
    void abbreviatesTextTwoCharsBigger() {
        final String msg = "hi everybody!";
        MatcherAssert.assertThat(
            "Must abbreviate a text two chars bigger",
            new AbbreviatedRight(msg, 15),
            new IsText(msg)
        );
    }

    @Test
    void abbreviatesTextWithWidthBiggerThanLength() {
        final String msg = "cactoos framework";
        MatcherAssert.assertThat(
            "Must abbreviate a text with width bigger than length",
            new AbbreviatedRight(msg, 50),
            new IsText(msg)
        );
    }

    @Test
    void abbreviatesTextBiggerThanDefaultMaxWidth() {
        MatcherAssert.assertThat(
            "Must abbreviate a text bigger than default max width",
            new AbbreviatedRight(
                "The quick brown fox jumps over the lazy black dog and after that returned to the cave"
            ),
            new IsText(
                "The quick brown fox jumps over the lazy black dog and after that returned to ..."
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    void abbreviatesTextThatChanges() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Text txt = new TextOf(
            () -> {
                final String result;
                if (counter.getAndIncrement() == 0) {
                    result = "The quick brown fox jumps";
                } else {
                    result = "The lazy black dog";
                }
                return result;
            }
        );
        MatcherAssert.assertThat(
            "Must abbreviate a text that changes",
            new AbbreviatedRight(
                txt,
                15
            ),
            new AllOf<>(
                new IsText(
                    "The quick br..."
                ),
                new IsText(
                    "The lazy bla..."
                )
            )
        );
    }
}
