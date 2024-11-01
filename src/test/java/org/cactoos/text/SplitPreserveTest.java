/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
        String txt = "   ";
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
        String txt = "   ";
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
