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
package org.cactoos.scalar;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Equals}.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.9
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class EqualsTest {

    @Test
    public void compareEquals() throws Exception {
        MatcherAssert.assertThat(
            "Can't compare if two integers are equals",
            new Equals<>(
                () -> new Integer(1),
                () -> new Integer(1)
            ).value(),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void compareNotEquals() throws Exception {
        MatcherAssert.assertThat(
            "Can't compare if two integers are not equals",
            new Equals<>(
                () -> new Integer(1),
                () -> new Integer(2)
            ).value(),
            Matchers.equalTo(false)
        );
    }

    @Test
    public void compareEqualsText() throws Exception {
        final String str = "hello";
        MatcherAssert.assertThat(
            "Can't compare if two strings are equals",
            new Equals<>(
                () -> str,
                () -> str
            ).value(),
            Matchers.equalTo(true)
        );
    }

    @Test
    public void compareNotEqualsText() throws Exception {
        MatcherAssert.assertThat(
            "Can't compare if two strings are not equals",
            new Equals<>(
                () -> "world",
                () -> "worle"
            ).value(),
            Matchers.equalTo(false)
        );
    }
}
