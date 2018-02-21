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

import java.util.Map;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.matchers.MatcherOf;
import org.cactoos.matchers.RunsInThreads;
import org.cactoos.text.SubText;
import org.cactoos.text.TextOf;
import org.cactoos.text.UpperText;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link SolidMap}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Stanislav Myachenkov (s.myachenkov@gmail.com)
 * @version $Id$
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class SolidMapTest {

    @Test
    public void behavesAsMap() {
        MatcherAssert.assertThat(
            "Can't behave as a map",
            new SolidMap<Integer, Integer>(
                new MapEntry<>(0, -1),
                new MapEntry<>(1, 1)
            ),
            new BehavesAsMap<>(0, 1)
        );
    }

    @Test
    public void worksInThreads() {
        MatcherAssert.assertThat(
            "Can't behave as a map in multiple threads",
            map -> {
                MatcherAssert.assertThat(map, new BehavesAsMap<>(0, 1));
                return true;
            },
            new RunsInThreads<>(
                new SolidMap<Integer, Integer>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            )
        );
    }

    @Test
    public void mapsToSameObjects() throws Exception {
        final Map<Integer, Scalar<Integer>> map = new SolidMap<>(
            input -> new MapEntry<>(input, () -> input),
            1, -1, 0, 1
        );
        MatcherAssert.assertThat(
            "Can't map only once",
            map.get(0), Matchers.equalTo(map.get(0))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void extendsExistingMapWithArrayOfEntries() {
        final SolidMap<Integer, Integer> map = new SolidMap<>(
            new MapEntry<>(0, 10),
            new MapEntry<>(1, 11)
        );
        MatcherAssert.assertThat(
            "Does not extends existing map with array of entries",
            new SolidMap<>(
                map,
                new MapEntry<>(2, 12),
                new MapEntry<>(3, 13)
            ).size(),
            Matchers.equalTo(4)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void acceptsEmptyArray() {
        MatcherAssert.assertThat(
            "Accepts empty array of entries",
            new SolidMap<>(
                new SolidMap<>(
                    new MapEntry<>(0, 10),
                    new MapEntry<>(1, 11)
                ),
                (Map.Entry<Integer, Integer>[]) new Map.Entry<?, ?>[0]
            ).size(),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void mapsIterableWithKeyFuncAndValueFunc() {
        final SolidMap<String, String> map = new SolidMap<>(
            key -> new SubText(new TextOf(key), 0, 1).asString(),
            value -> new UpperText(new TextOf(value)).asString(),
            new IterableOf<>("aa", "bb")
        );
        MatcherAssert.assertThat(
            "Functions are not applied to key and value",
            map,
            Matchers.allOf(
                Matchers.hasEntry(
                    Matchers.equalTo("a"),
                    Matchers.equalTo("AA")
                ),
                Matchers.hasEntry(
                    Matchers.equalTo("b"),
                    Matchers.equalTo("BB")
                ),
                new MatcherOf<>(m -> m.size() == 2)
            )
        );
    }

    @Test
    public void mapsEmptyIterableWithKeyFuncAndValueFunc() {
        final SolidMap<String, String> map = new SolidMap<>(
            key -> new SubText(new TextOf(key), 0, 1).asString(),
            value -> new UpperText(new TextOf(value)).asString(),
            new IterableOf<String>()
        );
        MatcherAssert.assertThat(
            "Empty Iterable cannot be accepted for key and value mapping",
            map,
            new MatcherOf<>(m -> m.size() == 0)
        );
    }

    @Test
    public void mapsIterableWithMapEntryFunc() {
        MatcherAssert.assertThat(
            "Function are not applied to entry",
            new SolidMap<>(
                entry -> new MapEntry<>(
                    new SubText(new TextOf(entry), 0, 1).asString(),
                    new UpperText(new TextOf(entry)).asString()
                ),
                new IterableOf<>("aa", "bb")
            ),
            Matchers.allOf(
                Matchers.hasEntry(
                    Matchers.equalTo("a"),
                    Matchers.equalTo("AA")
                ),
                Matchers.hasEntry(
                    Matchers.equalTo("b"),
                    Matchers.equalTo("BB")
                ),
                new MatcherOf<>(m -> m.size() == 2)
            )
        );
    }

    @Test
    public void mapsEmptyIterableWithMapEntryFunc() {
        MatcherAssert.assertThat(
            "Empty Iterable cannot be accepted for MapEntry mapping",
            new SolidMap<>(
                entry -> new MapEntry<>(
                    new SubText(new TextOf(entry), 0, 1).asString(),
                    new UpperText(new TextOf(entry)).asString()
                ),
                new IterableOf<String>()
            ),
            new MatcherOf<>(m -> m.size() == 0)
        );
    }
}
