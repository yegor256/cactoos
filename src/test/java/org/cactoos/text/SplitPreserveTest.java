/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;

/**
 * Testing correctness of SplitPreserveAllTokens.
 * Compare with Split class in specified cases.
 * @since 0.0
 */
final class SplitPreserveTest {

    @Test
    void splitAdjacentSeparators() {
        final List<Text> array = new ArrayList<>(4);
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        MatcherAssert.assertThat(
            "Split must not preserve adjacent separators",
            this.getLength(
                new Split(
                    new TextOf("aaa"),
                    new TextOf("a")
                ).iterator()
            ),
            IsNot.not(
                Matchers.equalTo(
                    this.getLength(
                        new IterableOf<Text>(
                            array.iterator()
                        ).iterator()
                    )
                )
            )
        );
    }

    @Test
    void splitSpaceSeparators() {
        final List<Text> array = new ArrayList<>(3);
        array.add(new TextOf(""));
        array.add(new TextOf("how"));
        array.add(new TextOf(""));
        MatcherAssert.assertThat(
            "Split must not preserve space separators",
            this.getLength(
                new Split(
                    new TextOf(" how "),
                    new TextOf(" ")
                ).iterator()
            ),
            IsNot.not(
                Matchers.equalTo(
                    this.getLength(
                        new IterableOf<Text>(
                            array.iterator()
                        ).iterator()
                    )
                )
            )
        );
    }

    int getLength(final Iterator<Text> iter) {
        int count = 0;
        while (iter.hasNext()) {
            iter.next();
            count += 1;
        }
        return count;
    }

    @Test
    void splitPreserveTokensAdjacent() {
        final List<Text> array = new ArrayList<>(4);
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        MatcherAssert.assertThat(
            "SplitPreserve must create empty elements for adjacent separators",
            this.getLength(
                new SplitPreserveAllTokens(
                    new TextOf("aaa"),
                    new TextOf("a")
                ).iterator()
            ),
            Matchers.equalTo(
                this.getLength(
                    new IterableOf<Text>(
                        array.iterator()
                    ).iterator()
                )
            )
        );
    }

    @Test
    void splitPreserveTokensWithBackslash() {
        final List<Text> array = new ArrayList<>(4);
        array.add(new TextOf("lol\\"));
        array.add(new TextOf(""));
        array.add(new TextOf("/"));
        array.add(new TextOf("dude"));
        MatcherAssert.assertThat(
            "SplitPreserve must handle backslash and spaces correctly",
            this.getLength(
                new SplitPreserveAllTokens(
                    new TextOf("lol\\  / dude"),
                    new TextOf(" ")
                ).iterator()
            ),
            Matchers.equalTo(
                this.getLength(
                    new IterableOf<Text>(
                        array.iterator()
                    ).iterator()
                )
            )
        );
    }

    @Test
    void splitPreserveTokensWithSpaces() {
        final List<Text> array = new ArrayList<>(3);
        array.add(new TextOf(""));
        array.add(new TextOf("how"));
        array.add(new TextOf(""));
        MatcherAssert.assertThat(
            "SplitPreserve must preserve leading and trailing spaces",
            this.getLength(
                new SplitPreserveAllTokens(
                    new TextOf(" how "),
                    new TextOf(" ")
                ).iterator()
            ),
            Matchers.equalTo(
                this.getLength(
                    new IterableOf<Text>(
                        array.iterator()
                    ).iterator()
                )
            )
        );
    }
}
