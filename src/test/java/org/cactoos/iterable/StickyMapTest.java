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
package org.cactoos.iterable;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.iterator.Repeated;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link StickyMap}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class StickyMapTest {

    @Test
    public void ignoresChangesInMap() throws Exception {
        final AtomicInteger size = new AtomicInteger(2);
        final Map<Integer, Integer> map = new StickyMap<>(
            new MapOf<>(
                () -> new Repeated<>(
                    () -> new MapEntry<>(
                        new SecureRandom().nextInt(),
                        1
                    ),
                    size.incrementAndGet()
                )
            )
        );
        MatcherAssert.assertThat(
            "Can't ignore the changes in the underlying map",
            map.size(),
            Matchers.equalTo(map.size())
        );
    }

    @Test
    public void decoratesEntries() throws Exception {
        MatcherAssert.assertThat(
            "Can't decorate a list of entries",
            new StickyMap<String, String>(
                new MapEntry<>("first", "Jeffrey"),
                new MapEntry<>("last", "Lebowski")
            ),
            Matchers.hasValue(Matchers.endsWith("ski"))
        );
    }

    @Test
    public void extendsExistingMap() throws Exception {
        MatcherAssert.assertThat(
            "Can't extend an existing map",
            new StickyMap<String, String>(
                new StickyMap<String, String>(
                    new MapEntry<>("make", "Mercedes-Benz"),
                    new MapEntry<>("cost", "$95,000")
                ),
                new MapEntry<>("year", "2017"),
                new MapEntry<>("mileage", "12,000")
            ),
            Matchers.hasValue(Matchers.endsWith(",000"))
        );
    }

    @Test
    public void extendsExistingMapWithFunc() throws Exception {
        MatcherAssert.assertThat(
            "Can't transform and decorate a list of entries",
            new StickyMap<String, String>(
                new StickyMap<String, String>(
                    new MapEntry<>("black", "BLACK"),
                    new MapEntry<>("white", "WHITE")
                ),
                new IterableOf<>("yellow", "red", "blue"),
                color -> new MapEntry<>(
                    color, color.toUpperCase(Locale.ENGLISH)
                )
            ),
            Matchers.hasValue(Matchers.equalTo("BLUE"))
        );
    }

    @Test
    public void extendsExistingMapWithTwoFuncs() throws Exception {
        MatcherAssert.assertThat(
            "Can't transform and decorate a list of entries with two funcs",
            new StickyMap<String, String>(
                new StickyMap<String, String>(
                    new MapEntry<>("black!", "Black!"),
                    new MapEntry<>("white!", "White!")
                ),
                new IterableOf<>("yellow!", "red!", "blue!"),
                color -> String.format("[%s]", color),
                String::toUpperCase
            ),
            Matchers.hasValue(Matchers.equalTo("BLUE!"))
        );
    }

}
