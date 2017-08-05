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
package org.cactoos.iterator;

import java.util.Collections;
import java.util.NoSuchElementException;
import org.cactoos.ScalarHasValue;
import org.cactoos.iterable.IterableOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test Case for {@link Cycled}.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class CycledTest {

    @Test
    public void repeatIteratorTest() throws Exception {
        final String expected = "two";
        MatcherAssert.assertThat(
            "Can't repeat iterator",
            new ItemAt<>(
                new Cycled<>(
                    new IterableOf<>(
                        "one", expected, "three"
                    )
                ),
                // @checkstyle MagicNumberCheck (1 line)
                7
            ),
            new ScalarHasValue<>(
                expected
            )
        );
    }

    @Test(expected = NoSuchElementException.class)
    public void notCycledEmptyTest() throws Exception {
        new Cycled<>(
            Collections::emptyIterator
        ).next();
    }
}
