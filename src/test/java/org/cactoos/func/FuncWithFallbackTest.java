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
 * Test case for {@link FuncWithFallback}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class FuncWithFallbackTest {

    @Test
    public void usesMainFunc() throws Exception {
        MatcherAssert.assertThat(
            "Can't use the main function if no exception",
            new FuncWithFallback<>(
                input -> "It's success",
                ex -> "In case of failure..."
            ),
            new FuncApplies<>(1, Matchers.containsString("success"))
        );
    }

    @Test
    public void usesCallback() throws Exception {
        MatcherAssert.assertThat(
            "Can't use the callback in case of exception",
            new FuncWithFallback<>(
                input -> {
                    throw new IOException("Failure");
                },
                ex -> "Never mind"
            ),
            new FuncApplies<>(1, Matchers.containsString("Never"))
        );
    }

    @Test
    public void usesFollowUp() throws Exception {
        MatcherAssert.assertThat(
            "Can't use the follow-up func",
            new FuncWithFallback<>(
                input -> "works fine",
                ex -> "won't happen",
                input -> "follow up"
            ),
            new FuncApplies<>(1, Matchers.containsString("follow"))
        );
    }

}
