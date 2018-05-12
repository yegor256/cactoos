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
package org.cactoos.list;

import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link SyncList}.
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class SyncListTest {

    @Test
    public void behavesAsCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't behave as a list",
            new SyncList<>(1, 0, -1, -1, 2),
            new BehavesAsList<>(0)
        );
    }

    @Test
    public void worksInThreads() {
        MatcherAssert.assertThat(
            list -> !list.iterator().hasNext(),
            new RunsInThreads<>(new SyncList<>(Collections.emptyList()))
        );
        MatcherAssert.assertThat(
            list -> {
                MatcherAssert.assertThat(list, new BehavesAsList<>(0));
                return true;
            },
            new RunsInThreads<>(new SyncList<>(1, 0, -1, -1, 2))
        );
    }

}
