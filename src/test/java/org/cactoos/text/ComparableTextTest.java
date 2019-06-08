/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.TextIs;

/**
 * Test case for {@link FormattedText}.
 *
 * @since 0.27
 * @checkstyle JavadocMethodCheck (100 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class ComparableTextTest {

    @Test
    public void comparesWithASubtext() {
        new Assertion<>(
            "Can't compare sub texts",
            new ComparableText(
                new TextOf(
                    "here to there"
                )
            ).compareTo(
                // @checkstyle MagicNumberCheck (2 lines)
                new ComparableText(
                    new Sub("from here to there", 5)
                )
            ),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void comparesToUncheckedText() {
        final String txt = "foobar";
        new Assertion<>(
            "These UncheckedText are not equal",
            new ComparableText(
                new UncheckedText(
                    new TextOf(txt)
                )
            ).compareTo(
                new ComparableText(new TextOf(txt))
            ),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void equalsToItself() {
        final Text text = new TextOf("text");
        new Assertion<>(
            "Does not equal to itself",
            text,
            new TextIs(text)
        ).affirm();
    }

    @Test
    public void equalsAndHashCodeOfComparableOfTheSameText() {
        final Text text = new TextOf("my text");
        final Text actual = new ComparableText(text);
        final Text expected = new ComparableText(text);
        new Assertion<>(
            "Does not equal to a comparable text made from the same Text",
            actual,
            new IsEqual<>(expected)
        ).affirm();
        new Assertion<>(
            "Hash codes of the equal objects are not equal",
            actual.hashCode(),
            new IsEqual<>(
                expected.hashCode()
            )
        ).affirm();
    }

    @Test
    public void equalsOfDifferentText() {
        final Text text = new ComparableText(
            new TextOf("my value")
        );
        new Assertion<>(
            "Is equal to the completely different object",
            text,
            new IsNot<>(
                new IsEqual<>(
                    "The string is ignored"
                )
            )
        ).affirm();
        new Assertion<>(
            "Is equal to the completely different text",
            text,
            new IsNot<>(
                new TextIs(
                    "The text is ignored"
                )
            )
        ).affirm();
        new Assertion<>(
            "Is equal to the different ComparableText",
            text,
            new IsNot<>(
                new IsEqual<>(
                    new ComparableText(
                        new TextOf("A different text")
                    )
                )
            )
        ).affirm();
        new Assertion<>(
            "The string is equal to the different ComparableText",
            text,
            new IsNot<>(
                new TextIs(
                    new ComparableText(
                        new TextOf("A different value")
                    )
                )
            )
        ).affirm();
    }
}
