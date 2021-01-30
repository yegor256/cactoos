/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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

import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.ScalarOf;
import org.hamcrest.core.AllOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
* Tests for {@link Flattened}.
*
* @since 0.49
* @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
*/
final class FlattenedTest {
    @Test
    void flattens() {
        final Text txt = new TextOf("txt");
        new Assertion<>(
            "must flatten",
            new Flattened(
                new ScalarOf<>(() -> txt)
            ),
            new IsText(txt)
        ).affirm();
    }

    @Test
    void flattensTextThatChanges() {
        final Iterable<Text> txts = new IterableOf<>(
            new TextOf("txt1"),
            new TextOf("txt2")
        );
        new Assertion<>(
            "must flatten a scalar that changes",
            new Flattened(
                new ScalarOf<>(txts.iterator()::next)
            ),
            new AllOf<Text>(new Mapped<>(IsText::new, txts))
        ).affirm();
    }
}
