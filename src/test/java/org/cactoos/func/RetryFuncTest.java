/*
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

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link RetryFunc}.
 *
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 line)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class RetryFuncTest {

    @Test
    public void runsFuncMultipleTimes() throws Exception {
        MatcherAssert.assertThat(
            new RetryFunc<>(
                input -> {
                    if (new SecureRandom().nextDouble() > 0.3d) {
                        throw new IllegalArgumentException("May happen");
                    }
                    return 0;
                },
                Integer.MAX_VALUE
            ).apply(true),
            Matchers.equalTo(0)
        );
    }

    @Test
    public void runsProcMultipleTimes() throws Exception {
        MatcherAssert.assertThat(
            new RetryFunc<>(
                input -> {
                    if (new SecureRandom().nextDouble() > 0.3d) {
                        throw new IllegalArgumentException("May happen");
                    }
                },
                Integer.MAX_VALUE
            ).apply(true),
            Matchers.nullValue()
        );
    }

    @Test
    public void runsProcDefaultMultipleTimes() throws Exception {
        final AtomicBoolean fail = new AtomicBoolean(true);
        MatcherAssert.assertThat(
            new RetryFunc<>(
                input -> {
                    if (fail.getAndSet(false)) {
                        throw new IllegalArgumentException("May happen");
                    }
                }
            ).apply(true),
            Matchers.nullValue()
        );
    }

    @Test
    public void runsProcConditionMultipleTimes() throws Exception {
        MatcherAssert.assertThat(
            new RetryFunc<>(
                input -> {
                    if (new SecureRandom().nextDouble() > 0.3d) {
                        throw new IllegalArgumentException("May happen");
                    }
                },
                count -> count == Integer.MAX_VALUE
            ).apply(true),
            Matchers.nullValue()
        );
    }
}
