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
package org.cactoos.func;

import java.security.SecureRandom;
import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.iterator.Sticky;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link RepeatedFunc}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.13.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 line)
 */
public final class RepeatedFuncTest {

    @Test
    public void runsFuncMultipleTimes() throws Exception {
        final Iterator<Integer> iter = new Sticky<>(1, 2, 5, 6);
        final Func<Boolean, Integer> func = new RepeatedFunc<>(
            input -> {
                return iter.next();
            },
            3
        );
        MatcherAssert.assertThat(
            func.apply(true),
            Matchers.equalTo(5)
        );
    }

    @Test
    public void repeatsNullsResults() throws Exception {
        final Func<Boolean, Integer> func = new RepeatedFunc<>(
            input -> {
                return null;
            },
            2
        );
        MatcherAssert.assertThat(
            func.apply(true),
            Matchers.equalTo(null)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesntRepeatAny() throws Exception {
        final Func<Boolean, Integer> func = new RepeatedFunc<>(
            input -> {
                return new SecureRandom().nextInt();
            },
            0
        );
        MatcherAssert.assertThat(
            func.apply(true),
            Matchers.equalTo(func.apply(true))
        );
    }
}
