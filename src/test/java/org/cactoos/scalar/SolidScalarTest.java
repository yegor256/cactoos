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
package org.cactoos.scalar;

import java.security.SecureRandom;
import org.cactoos.Scalar;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link SolidScalar}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class SolidScalarTest {

    @Test
    public void cachesScalarResults() throws Exception {
        final Scalar<Integer> scalar = new SolidScalar<>(
            () -> new SecureRandom().nextInt()
        );
        MatcherAssert.assertThat(
            scalar.value() + scalar.value(),
            Matchers.equalTo(scalar.value() + scalar.value())
        );
    }

    @Test
    public void worksInThreads() {
        MatcherAssert.assertThat(
            "Can't work well in multiple threads",
            scalar -> {
                MatcherAssert.assertThat(
                    scalar.value(),
                    Matchers.equalTo(scalar.value())
                );
                return true;
            },
            new RunsInThreads<>(
                new UncheckedScalar<>(
                    new SolidScalar<>(() -> new ListOf<>(1, 2))
                )
            )
        );
    }

}
