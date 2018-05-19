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
package org.cactoos.collection;

import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.LengthOf;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;

/**
 * Test case for {@link org.cactoos.collection.Filtered}.
 *
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 line)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class FilteredTest {

    @Test
    public void behavesAsCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't behave as a collection",
            new Filtered<Integer>(i -> i < 2, 1, 2, 0, -1),
            new BehavesAsCollection<>(-1)
        );
    }

    @Test
    public void filterList() {
        MatcherAssert.assertThat(
            new LengthOf(
                new Filtered<String>(
                    input -> input.length() > 4,
                    new IterableOf<>("hello", "world", "друг")
                )
            ).intValue(),
            new IsEqual<>(2)
        );
    }

    @Test
    public void filterEmptyList() {
        MatcherAssert.assertThat(
            new Filtered<String>(
                input -> input.length() > 4,
                new ListOf<>()
            ),
            new IsEmptyCollection<>()
        );
    }

    @Test
    public void size() throws Exception {
        MatcherAssert.assertThat(
            new Filtered<String>(
                input -> input.length() >= 4,
                new IterableOf<>("some", "text", "yes")
            ).size(),
            new IsEqual<>(2)
        );
    }

    @Test
    public void withItemsNotEmpty() throws Exception {
        MatcherAssert.assertThat(
            new Filtered<String>(
                input -> input.length() > 4,
                new IterableOf<>("first", "second")
            ),
            new IsNot<>(new IsEmptyCollection<>())
        );
    }

    @Test
    public void withoutItemsIsEmpty() throws Exception {
        MatcherAssert.assertThat(
            new Filtered<String>(
                input -> input.length() > 16,
                new IterableOf<>("third", "fourth")
            ),
            new IsEmptyCollection<>()
        );
    }
}
