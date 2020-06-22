/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
import org.cactoos.text.Sub;
import org.cactoos.text.TextOf;
import org.cactoos.text.Upper;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
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
        new Assertion<>(
            "Can't behave as a map",
            new Solid<Integer, Integer>(
                new MapEntry<>(0, -1),
                new MapEntry<>(1, 1)
            ),
            new BehavesAsMap<Integer, Integer>(1, 1)
        ).affirm();
    }

    @Test
    public void worksInThreads() {
        new Assertion<>(
            "Can't behave as a map in multiple threads",
            map -> {
                new Assertion<>(
                    "Can't behave as a map in thread",
                    map,
                    new BehavesAsMap<Integer, Integer>(1, 1)
        ).affirm();
                return true;
            },
            new RunsInThreads<Solid<Integer, Integer>>(
                new Solid<Integer, Integer>(
                    new MapEntry<>(0, -1),
                    new MapEntry<>(1, 1)
                )
            )
        ).affirm();
    }

    @Test
    public void mapsToSameObjects() throws Exception {
        final Map<Integer, Scalar<Integer>> map = new Solid<>(
            input -> new MapEntry<>(input, () -> input),
            1, -1, 0, 1
        );
        new Assertion<>(
            "Can't map only once",
            map.get(0),
            new IsEqual<Scalar<Integer>>(map.get(0))
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void extendsExistingMapWithArrayOfEntries() {
        final Solid<Integer, Integer> map = new Solid<>(
            new MapEntry<>(0, 10),
            new MapEntry<>(1, 11)
        );
        new Assertion<>(
            "Does not extends existing map with array of entries",
            new Solid<>(
                map,
                new MapEntry<>(2, 12),
                new MapEntry<>(3, 13)
            ).size(),
            new IsEqual<Integer>(4)
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void acceptsEmptyArray() {
        new Assertion<>(
            "Accepts empty array of entries",
            new Solid<Integer, Integer>(
                new Solid<>(
                    new MapEntry<>(0, 10),
                    new MapEntry<>(1, 11)
                ),
                (Map.Entry<Integer, Integer>[]) new Map.Entry<?, ?>[0]
            ).size(),
            new IsEqual<Integer>(2)
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void mapsIterableWithKeyFuncAndValueFunc() {
        final Solid<String, String> map = new Solid<>(
            key -> new Sub(new TextOf(key), 0, 1).asString(),
            value -> new Upper(new TextOf(value)).asString(),
            new IterableOf<>("aa", "bb")
        );
        new Assertion<>(
            "Functions are not applied to key and value",
            map,
            new AllOf<Solid<String, String>>(
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
        ).affirm();
    }

    @Test
    public void mapsEmptyIterableWithKeyFuncAndValueFunc() {
        final Solid<String, String> map = new Solid<>(
            key -> new Sub(new TextOf(key), 0, 1).asString(),
            value -> new Upper(new TextOf(value)).asString(),
            new IterableOf<String>()
        );
        new Assertion<>(
            "Empty Iterable cannot be accepted for key and value mapping",
            map,
            new MatcherOf<Solid<String, String>>(m -> m.size() == 0)
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void mapsIterableWithMapEntryFunc() {
        new Assertion<>(
            "Function are not applied to entry",
            new Solid<String, String>(
                entry -> new MapEntry<>(
                    new Sub(new TextOf(entry), 0, 1).asString(),
                    new Upper(new TextOf(entry)).asString()
                ),
                new IterableOf<>("aa", "bb")
            ),
            new AllOf<Solid<String, String>>(
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
        ).affirm();
    }

    @Test
    public void mapsEmptyIterableWithMapEntryFunc() {
        new Assertion<>(
            "Empty Iterable cannot be accepted for MapEntry mapping",
            new Solid<String, String>(
                entry -> new MapEntry<>(
                    new Sub(new TextOf(entry), 0, 1).asString(),
                    new Upper(new TextOf(entry)).asString()
                ),
                new IterableOf<String>()
            ),
            new MatcherOf<Solid<String, String>>(m -> m.size() == 0)
        ).affirm();
    }
}
