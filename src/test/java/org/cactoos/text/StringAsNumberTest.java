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
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link StringAsNumber}.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.2
 */
public final class StringAsNumberTest {

    /**
     * Test {@link Integer}.
     */
    @Test
    public void intTest() {
        MatcherAssert.assertThat(
            new StringAsNumber("-198815458").intValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(-198815458)
        );
    }

    /**
     * Test {@link Long}.
     */
    @Test
    public void longTest() {
        MatcherAssert.assertThat(
            new StringAsNumber("9145723601264654").longValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(9145723601264654L)
        );
    }

    /**
     * Test {@link Float}.
     */
    @Test
    public strictfp void floatTest() {
        MatcherAssert.assertThat(
            new StringAsNumber("765923.24").floatValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(765923.24F)
        );
    }

    /**
     * Test {@link Double}.
     */
    @Test
    public strictfp void doubleTest() {
        MatcherAssert.assertThat(
            new StringAsNumber("0.25323464151").doubleValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(0.25323464151)
        );
    }
}
