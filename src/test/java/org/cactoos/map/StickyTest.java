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
package org.cactoos.map;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterator.Repeated;
import org.cactoos.text.FormattedText;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringEndsWith;
import org.junit.Test;

/**
 * Test case for {@link Sticky}.
 *
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class StickyTest {

    @Test
    public void behavesAsMap() {
        MatcherAssert.assertThat(
            "Can't behave as a map",
            new Sticky<Integer, Integer>(
                new MapEntry<>(0, -1),
                new MapEntry<>(1, 1)
            ),
            new BehavesAsMap<>(0, 1)
        );
    }

    @Test
    public void ignoresChangesInMap() throws Exception {
        final AtomicInteger size = new AtomicInteger(2);
        final Map<Integer, Integer> map = new Sticky<>(
            new MapOf<>(
                () -> new Repeated<>(
                    size.incrementAndGet(),
                    () -> new MapEntry<>(
                        new SecureRandom().nextInt(),
                        1
                    )
                )
            )
        );
        MatcherAssert.assertThat(
            "Can't ignore the changes in the underlying map",
            map.size(),
            new IsEqual<>(map.size())
        );
    }

    @Test
    public void decoratesEntries() throws Exception {
        MatcherAssert.assertThat(
            "Can't decorate a list of entries",
            new Sticky<String, String>(
                new MapEntry<>("first", "Jeffrey"),
                new MapEntry<>("last", "Lebowski")
            ),
            new IsMapContaining<>(
                new IsAnything<>(),
                new StringEndsWith("ski")
            )
        );
    }

    @Test
    public void extendsExistingMap() throws Exception {
        MatcherAssert.assertThat(
            "Can't extend an existing map",
            new Sticky<String, String>(
                new Sticky<String, String>(
                    new MapEntry<>("make", "Mercedes-Benz"),
                    new MapEntry<>("cost", "$95,000")
                ),
                new MapEntry<>("year", "2017"),
                new MapEntry<>("mileage", "12,000")
            ),
            new IsMapContaining<>(
                new IsAnything<>(),
                new StringEndsWith(",000")
            )
        );
    }

    @Test
    public void extendsExistingMapWithFunc() throws Exception {
        MatcherAssert.assertThat(
            "Can't transform and decorate a list of entries",
            new Sticky<>(
                color -> new MapEntry<>(
                    color, color.toUpperCase(Locale.ENGLISH)
                ),
                new Sticky<String, String>(
                    new MapEntry<>("black", "BLACK"),
                    new MapEntry<>("white", "WHITE")
                ),
                new IterableOf<>("yellow", "red", "blue")
            ),
            new IsMapContaining<>(
                new IsAnything<>(),
                new IsEqual<>("BLUE")
            )
        );
    }

    @Test
    public void extendsExistingMapWithTwoFuncs() throws Exception {
        MatcherAssert.assertThat(
            "Can't transform and decorate a list of entries with two funcs",
            new Sticky<>(
                color -> new FormattedText("[%s]", color).asString(),
                String::toUpperCase,
                new Sticky<String, String>(
                    new MapEntry<>("black!", "Black!"),
                    new MapEntry<>("white!", "White!")
                ),
                new IterableOf<>("yellow!", "red!", "blue!")
            ),
            new IsMapContaining<>(
                new IsAnything<>(),
                new IsEqual<>("BLUE!")
            )
        );
    }
}
