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
package org.cactoos.list;

import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link org.cactoos.collection.Joined}.
 *
 * @since 0.20
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 line)
 * @checkstyle DiamondOperatorCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
public final class JoinedTest {

    @Test
    public void behavesAsCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't behave as a list",
            new Joined<Integer>(
                new ListOf<>(1, 2),
                new ListOf<>(3, 4)
            ),
            new BehavesAsList<>(2)
        );
    }

    @Test
    public void size() throws Exception {
        MatcherAssert.assertThat(
            new Joined<Integer>(
                new ListOf<>(1, 2),
                new ListOf<>(3, 4)
            ).size(),
            new IsEqual<>(4)
        );
    }

    @Test
    public void isEmpty() throws Exception {
        MatcherAssert.assertThat(
            new Joined<Integer>(
                new ListOf<Integer>(5, 6)
            ).isEmpty(),
            new IsEqual<>(false)
        );
    }

    @Test
    public void contains() throws Exception {
        final int element = 0;
        MatcherAssert.assertThat(
            new Joined<Integer>(
                new ListOf<>(7, 8),
                new ListOf<>(9, element)
            ).contains(element),
            new IsTrue()
        );
    }

    @Test
    public void iterator() throws Exception {
        final String element = "element";
        MatcherAssert.assertThat(
            new Joined<String>(
                new ListOf<>(element, "first"),
                new ListOf<>("second", "third")
            ).iterator().next(),
            new IsEqual<>(element)
        );
    }

    @Test
    public void add() throws Exception {
        final List<String> joined = new Joined<String>(
            new ListOf<>("One"),
            new ListOf<>("Two")
        );
        joined.add("Three");
        new Assertion<>(
            "must be able to add element specified",
            joined.size(),
            new IsEqual<>(3)
        ).affirm();
    }

    @Test
    public void remove() throws Exception {
        final List<String> joined = new Joined<String>(
            new ListOf<>("One"),
            new ListOf<>("Two")
        );
        joined.remove("Two");
        new Assertion<>(
            "must be able to remove element specified",
            joined.size(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    public void containsAll() throws Exception {
        final String first = "item1";
        final String second = "item2";
        MatcherAssert.assertThat(
            new Joined<String>(
                new ListOf<>(first, "item3"),
                new ListOf<>(second, "item4")
            ).containsAll(new ListOf<>(first, second)),
            new IsEqual<>(true)
        );
    }

    @Test
    public void addAll() throws Exception {
        final List<String> joined = new Joined<String>(
            new ListOf<>("One"),
            new ListOf<>("Two")
        );
        joined.addAll(
            new ListOf<>("Three")
        );
        new Assertion<>(
            "must be able to addAll elements specified",
            joined.get(2),
            new IsEqual<>("Three")
        ).affirm();
    }

    @Test
    public void addAllInFront() throws Exception {
        final List<String> joined = new Joined<String>(
            new ListOf<>("One"),
            new ListOf<>("Two")
        );
        joined.addAll(
            0,
            new ListOf<>("Three")
        );
        new Assertion<>(
            "must be able to addAll elements specified",
            joined.get(0),
            new IsEqual<>("Three")
        ).affirm();
    }

    @Test
    public void removeAll() throws Exception {
        final List<String> joined = new Joined<String>(
            new ListOf<>("One"),
            new ListOf<>("Two")
        );
        joined.removeAll(
            new ListOf<>("Two")
        );
        new Assertion<>(
            "must be able to removeAll elements specified",
            joined.size(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    public void retainAll() throws Exception {
        final List<String> joined = new Joined<String>(
            new ListOf<>("One"),
            new ListOf<>("Two", "Three")
        );
        joined.retainAll(
            new ListOf<>("One", "Two")
        );
        new Assertion<>(
            "must be able to retain all",
            joined.size(),
            new IsEqual<>(2)
        ).affirm();
    }

    @Test
    public void clear() throws Exception {
        final List<String> joined = new Joined<String>(
            new ListOf<>("One"),
            new ListOf<>("Two")
        );
        joined.clear();
        new Assertion<>(
            "must be able to clear",
            joined.size(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void get() throws Exception {
        final String element = "element2";
        MatcherAssert.assertThat(
            new Joined<String>(
                new ListOf<>("element1"),
                new ListOf<>(element, "element3")
            ).get(1),
            new IsEqual<>(element)
        );
    }

    @Test
    public void set() throws Exception {
        final List<String> joined = new Joined<String>(
            new ListOf<>("One"),
            new ListOf<>("Two")
        );
        joined.set(0, "Three");
        new Assertion<>(
            "must be able to set element by specified index",
            joined.get(0),
            new IsEqual<>("Three")
        ).affirm();
    }

    @Test
    public void addByIndex() throws Exception {
        final List<String> joined = new Joined<String>(
            new ListOf<>("One"),
            new ListOf<>("Two")
        );
        joined.add(0, "Three");
        new Assertion<>(
            "must be able to add element by specified index",
            joined.get(0),
            new IsEqual<>("Three")
        ).affirm();
    }

    @Test
    public void removeByIndex() throws Exception {
        final List<String> joined = new Joined<String>(
            new ListOf<>("One"),
            new ListOf<>("Two")
        );
        joined.remove(0);
        new Assertion<>(
            "must be able to remove element by specified index",
            joined.get(0),
            new IsEqual<>("Two")
        ).affirm();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void listIteratorSecond() throws Exception {
        new Joined<Integer>().listIterator(66);
    }

    @Test
    public void subList() throws Exception {
        final String element = "elem2";
        MatcherAssert.assertThat(
            new Joined<String>(
                new ListOf<>("elem1", element),
                new ListOf<>("elem3", "elem4")
            ).subList(1, 3).iterator().next(),
            new IsEqual<>(element)
        );
    }

    @Test
    public void itemAndList() {
        MatcherAssert.assertThat(
            new Joined<>(
                0,
                new ListOf<>(1, 2, 3)
            ),
            new IsEqual<>(new ListOf<>(0, 1, 2, 3))
        );
    }
}
