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
package org.cactoos.map;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Scalar;
import org.cactoos.iterator.Repeated;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link MapOf}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class MapOfTest {

    @Test
    public void behavesAsMap() {
        MatcherAssert.assertThat(
            "Can't behave as a map",
            new MapNoNulls<>(
                new MapOf<Integer, Integer>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            ),
            new BehavesAsMap<>(0, 1)
        );
    }

    @Test
    public void convertsIterableToMap() {
        MatcherAssert.assertThat(
            "Can't convert iterable to map",
            new MapOf<Integer, String>(
                new MapEntry<>(0, "hello, "),
                new MapEntry<>(1, "world!")
            ),
            Matchers.hasEntry(
                Matchers.equalTo(0),
                Matchers.startsWith("hello")
            )
        );
    }

    @Test
    public void sensesChangesInMap() throws Exception {
        final AtomicInteger size = new AtomicInteger(2);
        final Map<Integer, Integer> map = new MapOf<>(
            () -> new Repeated<>(
                size.incrementAndGet(), () -> new MapEntry<>(
                    new SecureRandom().nextInt(),
                    1
                )
            )
        );
        MatcherAssert.assertThat(
            "Can't sense the changes in the underlying map",
            map.size(),
            Matchers.not(Matchers.equalTo(map.size()))
        );
    }

    @Test
    public void createsMapWithFunctions() {
        MatcherAssert.assertThat(
            "Can't create a map with functions as values",
            new MapOf<Integer, Scalar<Boolean>>(
                new MapEntry<>(0, () -> true),
                new MapEntry<>(
                    1,
                    () -> {
                        throw new IOException("oops");
                    }
                )
            ),
            Matchers.hasKey(0)
        );
    }

}
