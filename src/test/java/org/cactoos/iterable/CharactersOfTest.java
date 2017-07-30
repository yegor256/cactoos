/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
package org.cactoos.iterable;

import org.cactoos.LengthOf;
import org.cactoos.ScalarHasValue;
import org.cactoos.io.BytesOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link CharactersOf}.
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class CharactersOfTest {

    @Test
    public void convertsStringToIterableOfChars() {
        MatcherAssert.assertThat(
            "Can't convert string to a iterable of Characters",
            new LengthOf(
                new CharactersOf<>(
                    "abc"
                )
            ),
            // @checkstyle MagicNumber (1 line)
            new ScalarHasValue<>(3)
        );
    }

    @Test
    public void convertsTextToIterableOfChars() {
        MatcherAssert.assertThat(
            "Can't convert text to a iterable of Characters",
            new LengthOf(
                new CharactersOf<>(
                    new TextOf("abcd")
                )
            ),
            // @checkstyle MagicNumber (1 line)
            new ScalarHasValue<>(4)
        );
    }

    @Test
    public void convertsScalarCharsToIterableOfChars() {
        MatcherAssert.assertThat(
            "Can't convert scalars to a iterable of Characters",
            new LengthOf(
                new CharactersOf<>(
                    new char[]{'a', 'b', 'c'}
                )
            ),
            // @checkstyle MagicNumber (1 line)
            new ScalarHasValue<>(3)
        );
    }

    @Test
    public void convertsBytesToCharacters() {
        MatcherAssert.assertThat(
            "Can't convert bytes to a iterable of Characters",
            new LengthOf(
                new CharactersOf<>(
                    new BytesOf("Hello, друг?")
                )
            ),
            // @checkstyle MagicNumber (1 line)
            new ScalarHasValue<>(12)
        );
    }

}

