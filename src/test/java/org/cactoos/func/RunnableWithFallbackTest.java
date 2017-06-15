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

import java.io.IOException;
import org.cactoos.FuncApplies;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link RunnableWithFallback}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.6
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class RunnableWithFallbackTest {

    @Test
    public void usesMainFunc() throws Exception {
        MatcherAssert.assertThat(
            "Can't use the main function if no exception",
            new RunnableAsFunc<>(
                new RunnableWithFallback(
                    () -> {
                    },
                    input -> {
                        throw new IOException(input);
                    }
                )
            ),
            new FuncApplies<>(true, Matchers.nullValue())
        );
    }

    @Test
    public void usesFallback() throws Exception {
        MatcherAssert.assertThat(
            "Can't use the fallback function if there is exception",
            new RunnableAsFunc<>(
                new RunnableWithFallback(
                    () -> {
                        throw new IllegalStateException("intended");
                    },
                    input -> {
                    }
                )
            ),
            new FuncApplies<>(true, Matchers.nullValue())
        );
    }

}
