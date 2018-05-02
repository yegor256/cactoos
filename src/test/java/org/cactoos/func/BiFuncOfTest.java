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
package org.cactoos.func;

import java.util.concurrent.atomic.AtomicBoolean;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link BiFuncOf}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.20
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class BiFuncOfTest {

    @Test
    public void convertsFuncIntoBiFunc() throws Exception {
        MatcherAssert.assertThat(
            new BiFuncOf<>(
                input -> 1
            ).apply(1, 2),
            Matchers.equalTo(1)
        );
    }

    @Test
    public void convertsProcIntoBiFunc() throws Exception {
        final AtomicBoolean done = new AtomicBoolean(false);
        MatcherAssert.assertThat(
            new BiFuncOf<String, Integer, Boolean>(
                input -> done.set(true),
                true
            ).apply("hello world", 1),
            Matchers.equalTo(done.get())
        );
    }

    @Test
    public void convertsProcWithNoResultIntoBiFunc() throws Exception {
        final AtomicBoolean done = new AtomicBoolean(false);
        MatcherAssert.assertThat(
            new BiFuncOf<String, Integer, Boolean>(
                input -> {
                    done.set(true);
                }
            ).apply("hello you", 1),
            Matchers.nullValue()
        );
    }

    @Test
    public void convertsRunnableIntoBiFunc() throws Exception {
        final AtomicBoolean done = new AtomicBoolean(false);
        MatcherAssert.assertThat(
            new BiFuncOf<String, Integer, Boolean>(
                () -> done.set(true)
            ).apply("hello, world", 1),
            Matchers.nullValue()
        );
    }

    @Test
    public void convertsValueIntoBiFunc() throws Exception {
        MatcherAssert.assertThat(
            new BiFuncOf<String, Integer, Boolean>(
                true
            ).apply("hello, dude!", 1),
            Matchers.equalTo(true)
        );
    }

}
