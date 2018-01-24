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
package org.cactoos.scalar;

import java.util.Collections;
import java.util.NoSuchElementException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LowestOf}.
 *
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @author Eduard Balovnev (bedward70@mail.ru)
 * @version $Id$
 * @since 0.10
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class LowestOfTest {

    @Test(expected = NoSuchElementException.class)
    public void minAmongEmptyTest() throws Exception {
        new LowestOf<>(() -> Collections.emptyIterator()).value();
    }

    @Test
    public void minAmongOneTest() throws Exception {
        final int num = 10;
        MatcherAssert.assertThat(
            "Can't find the smaller among one",
            new LowestOf<Integer>(() -> new Integer(num)).value(),
            Matchers.equalTo(num)
        );
    }

    @Test
    public void minAmongManyTest() throws Exception {
        final int num = -1;
        MatcherAssert.assertThat(
            "Can't find the smaller among many",
            new LowestOf<Integer>(
                () -> new Integer(1),
                () -> new Integer(0),
                () -> new Integer(num),
                () -> new Integer(2)
             ).value(),
            Matchers.equalTo(num)
        );
    }

    @Test
    public void lowestStringTest() throws Exception {
        final String lowest = "Apple";
        final String highest = "Orange";
        MatcherAssert.assertThat(
            "Can't find the lowest string among many",
            new LowestOf<String>(() -> lowest, () -> highest).value(),
            Matchers.equalTo(lowest)
        );
    }

    @Test
    public void lowestCharTest() throws Exception {
        final char lowest = 'A';
        final char highest = 'B';
        MatcherAssert.assertThat(
            "Can't find the lowest char among many",
            new LowestOf<Character>(() -> lowest, () -> highest).value(),
            Matchers.equalTo(lowest)
        );
    }
}
