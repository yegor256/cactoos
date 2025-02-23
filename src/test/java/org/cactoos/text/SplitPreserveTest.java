/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.text;

import java.util.ArrayList;
import java.util.Iterator;
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Testing correctness of SplitPreserveAllTokens.
 * Compare with Split class in specified cases.
 *
 * @since 0.0
 */
final class SplitPreserveTest {
    @Test
    void checkingSplit() {
        String txt = "aaa";
        final String msg = "Adjacent separators must create an empty element";
        ArrayList<Text> array = new ArrayList<>(4);
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        new Assertion<>(
            msg,
            this.getLength(
                new Split(
                    new TextOf(txt),
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
        ).affirm();
        txt = " how ";
        array = new ArrayList<>(3);
        array.add(new TextOf(""));
        array.add(new TextOf("how"));
        array.add(new TextOf(""));
        new Assertion<>(
            msg,
            this.getLength(
                new Split(
                    new TextOf(txt),
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
        ).affirm();
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
    void checkingSplitPreserveTokens() {
        String txt = "aaa";
        final String msg = "Adjacent separators must create an empty element";
        ArrayList<Text> array = new ArrayList<>(4);
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        array.add(new TextOf(""));
        new Assertion<>(
            msg,
            this.getLength(
                new SplitPreserveAllTokens(
                    new TextOf(txt),
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
        ).affirm();
        txt = "lol\\  / dude";
        array = new ArrayList<>(4);
        array.add(new TextOf("lol\\"));
        array.add(new TextOf(""));
        array.add(new TextOf("/"));
        array.add(new TextOf("dude"));
        new Assertion<>(
            msg,
            this.getLength(
                new SplitPreserveAllTokens(
                    new TextOf(txt),
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
        ).affirm();
        txt = " how ";
        array = new ArrayList<>(3);
        array.add(new TextOf(""));
        array.add(new TextOf("how"));
        array.add(new TextOf(""));
        new Assertion<>(
            msg,
            this.getLength(
                new SplitPreserveAllTokens(
                    new TextOf(txt),
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
        ).affirm();
    }
}
