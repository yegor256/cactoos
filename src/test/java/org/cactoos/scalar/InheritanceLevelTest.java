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

import java.io.FileNotFoundException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link InheritanceLevel}.
 *
 * @author Vedran Vatavuk (123vgv@gmail.com)
 * @version $Id$
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class InheritanceLevelTest {

    @Test
    public void twoInheritanceLevelsBetweenClasses() {
        final int expected = 2;
        MatcherAssert.assertThat(
            new InheritanceLevel(
                FileNotFoundException.class, Exception.class
            ).value(),
            Matchers.equalTo(expected)
        );
    }

    @Test
    public void classesAreNotRelated() {
        MatcherAssert.assertThat(
            new InheritanceLevel(
                FileNotFoundException.class, RuntimeException.class
            ).value(),
            Matchers.equalTo(Integer.MAX_VALUE)
        );
    }

    @Test
    public void classesAreIdentical() {
        final int expected = 0;
        MatcherAssert.assertThat(
            new InheritanceLevel(
                FileNotFoundException.class, FileNotFoundException.class
            ).value(),
            Matchers.equalTo(expected)
        );
    }

}
