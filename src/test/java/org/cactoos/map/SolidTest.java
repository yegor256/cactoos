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

import java.util.Map;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.SubText;
import org.cactoos.text.TextOf;
import org.cactoos.text.UpperText;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.MatcherOf;
import org.llorllale.cactoos.matchers.RunsInThreads;

/**
 * Test case for {@link Solid}.
 *
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class SolidTest {

    @Test
    public void behavesAsMap() {
        MatcherAssert.assertThat(
            "Can't behave as a map",
            new Solid<Integer, Integer>(
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
                MatcherAssert.assertThat(
                    "Can't behave as a map in thread",
                    map,
                    new BehavesAsMap<>(0, 1)
                );
                return true;
            },
            new RunsInThreads<>(
                new Solid<Integer, Integer>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            )
        );
    }

    @Test
    public void mapsToSameObjects() throws Exception {
        final Map<Integer, Scalar<Integer>> map = new Solid<>(
            input -> new MapEntry<>(input, () -> input),
            1, -1, 0, 1
        );
        MatcherAssert.assertThat(
            "Can't map only once",
            map.get(0), new IsEqual<>(map.get(0))
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void extendsExistingMapWithArrayOfEntries() {
        final Solid<Integer, Integer> map = new Solid<>(
            new MapEntry<>(0, 10),
            new MapEntry<>(1, 11)
        );
        MatcherAssert.assertThat(
            "Does not extends existing map with array of entries",
            new Solid<>(
                map,
                new MapEntry<>(2, 12),
                new MapEntry<>(3, 13)
            ).size(),
            new IsEqual<>(4)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void acceptsEmptyArray() {
        MatcherAssert.assertThat(
            "Accepts empty array of entries",
            new Solid<Integer, Integer>(
                new Solid<>(
                    new MapEntry<>(0, 10),
                    new MapEntry<>(1, 11)
                ),
                (Map.Entry<Integer, Integer>[]) new Map.Entry<?, ?>[0]
            ).size(),
            new IsEqual<>(2)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void mapsIterableWithKeyFuncAndValueFunc() {
        final Solid<String, String> map = new Solid<>(
            key -> new SubText(new TextOf(key), 0, 1).asString(),
            value -> new UpperText(new TextOf(value)).asString(),
            new IterableOf<>("aa", "bb")
        );
        MatcherAssert.assertThat(
            "Functions are not applied to key and value",
            map,
            new AllOf<>(
                new IterableOf<>(
                    new IsMapContaining<>(
                        new IsEqual<>("a"),
                        new IsEqual<>("AA")
                    ),
                    new IsMapContaining<>(
                        new IsEqual<>("b"),
                        new IsEqual<>("BB")
                    ),
                    new MatcherOf<>(m -> m.size() == 2)
                )
            )
        );
    }

    @Test
    public void mapsEmptyIterableWithKeyFuncAndValueFunc() {
        final Solid<String, String> map = new Solid<>(
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
    @SuppressWarnings("unchecked")
    public void mapsIterableWithMapEntryFunc() {
        MatcherAssert.assertThat(
            "Function are not applied to entry",
            new Solid<>(
                entry -> new MapEntry<>(
                    new SubText(new TextOf(entry), 0, 1).asString(),
                    new UpperText(new TextOf(entry)).asString()
                ),
                new IterableOf<>("aa", "bb")
            ),
            new AllOf<>(
                new IterableOf<>(
                    new IsMapContaining<>(
                        new IsEqual<>("a"),
                        new IsEqual<>("AA")
                    ),
                    new IsMapContaining<>(
                        new IsEqual<>("b"),
                        new IsEqual<>("BB")
                    ),
                    new MatcherOf<>(m -> m.size() == 2)
                )
            )
        );
    }

    @Test
    public void mapsEmptyIterableWithMapEntryFunc() {
        MatcherAssert.assertThat(
            "Empty Iterable cannot be accepted for MapEntry mapping",
            new Solid<>(
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
