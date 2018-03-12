/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
package org.cactoos.matchers;

import java.io.File;
import org.cactoos.Text;
import org.cactoos.io.TeeInput;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Equals;
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.ComparableText;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher for the {@link TeeInput}'s results.
 *
 * @author Roman Proshin (roman@proshin.org)
 * @version $Id$
 * @since 1.0
 */
public final class TeeInputHasResult extends TypeSafeMatcher<TeeInput> {

    /**
     * Value that is expected to be returned from the given TeeInput.
     */
    private final ComparableText expected;
    /**
     * Value that is expected to be copied into the output of TeeInput.
     */
    private final ComparableText copied;
    /**
     * Actual value returned from the given TeeInput.
     */
    private ComparableText actual;

    /**
     * Ctor.
     * @param expected Value that is expected to be returned from the TeeInput
     * @param file Value that is expected to be copied into the output of
     *  the TeeInput
     */
    public TeeInputHasResult(
        final String expected,
        final File file) {
        this(
            new TextOf(expected),
            new TextOf(file)
        );
    }

    /**
     * Ctor.
     * @param expected Value that is expected to be returned from the TeeInput
     * @param copied Value that is expected to be copied into the output of
     *  the TeeInput
     */
    public TeeInputHasResult(
        final String expected,
        final Text copied) {
        this(
            new TextOf(expected),
            copied
        );
    }

    /**
     * Ctor.
     * @param expected Value that is expected to be returned from the TeeInput
     * @param copied Value that is expected to be copied into the output of
     *  the TeeInput
     */
    public TeeInputHasResult(
        final Text expected,
        final Text copied) {
        super();
        this.expected = new ComparableText(expected);
        this.copied = new ComparableText(copied);
        this.actual = new ComparableText(new TextOf(""));
    }

    @Override
    public boolean matchesSafely(final TeeInput item) {
        this.actual = new ComparableText(new TextOf(item));
        return
            new UncheckedScalar<>(
                new And(
                    new Equals<>(
                        this.expected::asString,
                        this.actual::asString
                    ),
                    new Equals<>(
                        this.expected::asString,
                        this.copied::asString
                    )
                )
            ).value();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(
            new UncheckedText(
                new FormattedText(
                    "TeeInput with result \"%1$s\" and copied value \"%1$s\"",
                    new UncheckedText(this.expected).asString()
                )
            ).asString()
        );
    }

    @Override
    public void describeMismatchSafely(
        final TeeInput item,
        final Description description) {
        this.describeTo(description);
    }
}
