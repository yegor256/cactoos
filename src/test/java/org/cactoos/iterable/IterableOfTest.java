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
package org.cactoos.iterable;

import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link IterableOf}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Ix (ixmanuel@yahoo.com)
 * @author Nikita Salomatin (nsalomatin@hotmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class IterableOfTest {

    @Test
    public void convertsScalarsToIterable() {
        MatcherAssert.assertThat(
            "Can't convert scalars to iterable",
            new LengthOf(
                new IterableOf<>(
                    "a", "b", "c"
                )
            ).intValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(3)
        );
    }

    @Test
    public void convertsArrayOfIntsToIterable() {
        MatcherAssert.assertThat(
            "Can't convert int scalars to iterable",
            new IterableOf<>(1, 2, 0, 2),
            Matchers.hasItem(0)
        );
    }

    @Test
    public void convertsObjectsToIterable() {
        MatcherAssert.assertThat(
            "Can't convert objects to iterable",
            new LengthOf(
                new IterableOf<>(
                    new TextOf("a"), new TextOf("b"), new TextOf("c")
                )
            ).intValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(3)
        );
    }

    @Test
    public void doubleIterateWithIterator() {
        final LengthOf length = new LengthOf(
            new IterableOf<>(
                new ListOf<>("a", "b", "c").iterator()
            )
        );
        length.intValue();
        MatcherAssert.assertThat(
            "Can't get value from iterator twice",
            length.intValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(3)
        );
    }

    @Test
    public void doubleIterateWithVarargs() {
        final LengthOf length = new LengthOf(
            new IterableOf<>(
                "a", "b", "c"
            )
        );
        length.intValue();
        MatcherAssert.assertThat(
            "Can't get value from varargs twice",
            length.intValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(3)
        );
    }
}

