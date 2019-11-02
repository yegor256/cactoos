/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.cactoos.collection.CollectionOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.MatcherOf;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Immutable}.
 *
 * @since 1.16
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
public class ImmutableTest {

    @Test
    public void innerListMustNotBeChanged() {
        final List<String> strings = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final List<String> immutable = new Immutable<>(strings);
        final int original = immutable.size();
        strings.add("d");
        new Assertion<>(
            "inner list must not be changed",
            original,
            new IsEqual<>(immutable.size())
        ).affirm();
    }

    @Test
    public void size() {
        new Assertion<>(
            "size() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).size(),
            new IsEqual<>(
                new ListOf<>(1, 2).size()
            )
        ).affirm();
    }

    @Test
    public void isEmpty() {
        new Assertion<>(
            "isEmpty() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).isEmpty(),
            new IsEqual<>(
                new ListOf<>(1, 2).isEmpty()
            )
        ).affirm();
    }

    @Test
    public void contains() {
        new Assertion<>(
            "contains() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b")
            ).contains("b"),
            new IsEqual<>(
                new ListOf<>("a", "b").contains("b")
            )
        ).affirm();
    }

    @Test
    public void iterator() {
        new Assertion<>(
            "iterator() is equal to original",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).iterator(),
            new HasValues<>(1, 2)
        ).affirm();
    }

    @Test
    public void toArray() {
        new Assertion<>(
            "toArray() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b")
            ).toArray(),
            new IsEqual<>(
                new ListOf<>("a", "b").toArray()
            )
        ).affirm();
    }

    @Test
    public void testToArray() {
        new Assertion<>(
            "toArray(T[]) must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b")
            ).toArray(new String[3]),
            new IsEqual<>(
                new ListOf<>("a", "b").toArray(new String[3])
            )
        ).affirm();
    }

    @Test
    public void add() {
        new Assertion<>(
            "add(T) must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).add(3),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#add(T): the list is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void remove() {
        new Assertion<>(
            "remove(Object) must throw exception",
            () -> new Immutable<>(
                new ListOf<>("1", "2")
            ).remove("1"),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#remove(Object): the list is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void containsAll() {
        new Assertion<>(
            "containsAll() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b", "c")
            ).containsAll(new ListOf<>("a", "c")),
            new IsEqual<>(
                new ListOf<>("a", "b", "c").containsAll(
                    new ListOf<>("a", "c")
                )
            )
        ).affirm();
    }

    @Test
    public void addAll() {
        new Assertion<>(
            "addAll(Collection) must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).addAll(new CollectionOf<>(3, 4)),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#addAll(Collection): the list is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void testAddAll() {
        new Assertion<>(
            "addAll(int, Collection) must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).addAll(2, new CollectionOf<>(3, 4)),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#addAll(int, Collection): the list is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void removeAll() {
        new Assertion<>(
            "removeAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).removeAll(new CollectionOf<>(1, 3)),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#removeAll(): the list is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void retainAll() {
        new Assertion<>(
            "retainAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).retainAll(new CollectionOf<>(1, 3)),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#retainAll(): the list is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void clear() {
        new Assertion<>(
            "clear() must throw exception",
            () -> {
                new Immutable<>(
                    new ListOf<>(1, 2, 3)
                ).clear();
                return new Object();
            },
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#clear(): the list is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void get() {
        new Assertion<>(
            "get() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b", "c")
            ).get(2),
            new IsEqual<>(
                new ListOf<>("a", "b", "c").get(2)
            )
        ).affirm();
    }

    @Test
    public void set() {
        new Assertion<>(
            "set() must throw exception",
            () -> new Immutable<>(
                new ListOf<>("a", "b")
            ).set(3, "c"),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#set(): the list is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void testAdd() {
        new Assertion<>(
            "add(int, T) must throw exception",
            () -> {
                new Immutable<>(
                    new ListOf<>("a", "b")
                ).add(3, "c");
                return new Object();
            },
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#add(int, T): the list is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void testRemove() {
        new Assertion<>(
            "remove(int) must throw exception",
            () -> new Immutable<>(
                new ListOf<>("a", "b")
            ).remove(2),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#remove(int): the list is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void indexOf() {
        new Assertion<>(
            "indexOf() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b", "c")
            ).indexOf("b"),
            new IsEqual<>(
                new ListOf<>("a", "b", "c").indexOf("b")
            )
        ).affirm();
    }

    @Test
    public void lastIndexOf() {
        new Assertion<>(
            "lastIndexOf() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b", "c", "b")
            ).lastIndexOf("b"),
            new IsEqual<>(
                new ListOf<>("a", "b", "c", "b").lastIndexOf("b")
            )
        ).affirm();
    }

    @Test
    public void listIterator() {
        new Assertion<>(
            "listIterator() is equal to original",
            () -> new Immutable<>(
                new ListOf<>("a", "b", "c", "b")
            ).listIterator(),
            new HasValues<>("a", "b", "c", "b")
        ).affirm();
    }

    @Test
    public void testListIterator() {
        new Assertion<>(
            "listIterator(int) is equal to original",
            () -> new Immutable<>(
                new ListOf<>("a", "b", "c", "b")
            ).listIterator(2),
            new HasValues<>("c", "b")
        ).affirm();
    }

    @Test
    public void subList() {
        new Assertion<>(
            "subList() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b", "c")
            ).subList(1, 2),
            new IsEqual<>(
                new ListOf<>("a", "b", "c").subList(1, 2)
            )
        ).affirm();
    }

    @Test
    public void immutableSubList() {
        new Assertion<>(
            "subList() result must be immutable",
            () -> new Immutable<>(
                new ListOf<>("a", "b", "c")
            ).subList(0, 2).add("d"),
            new Throws<>(
                new MatcherOf<>(
                    (String msg) -> msg.equals("#add(T): the list is read-only")
                ),
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void notEqualsToObjectOfAnotherType() {
        new Assertion<>(
            "must not equal to object of another type",
            new Immutable<>(),
            new IsNot<>(new IsEqual<>(new Object()))
        ).affirm();
    }

    @Test
    public void notEqualsToListWithDifferentElements() {
        new Assertion<>(
            "must not equal to List with different elements",
            new Immutable<>(1, 2),
            new IsNot<>(new IsEqual<>(new ListOf<>(1, 0)))
        ).affirm();
    }

    @Test
    public void isEqualToItself() {
        final List<Integer> list = new Immutable<>(1, 2);
        new Assertion<>(
            "must be equal to itself",
            list,
            new IsEqual<>(list)
        ).affirm();
    }

    @Test
    public void isEqualToListWithTheSameElements() {
        new Assertion<>(
            "must be equal to List with the same elements",
            new Immutable<>(1, 2),
            new IsEqual<>(new ListOf<>(1, 2))
        ).affirm();
    }

    @Test
    public void equalToEmptyImmutable() {
        new Assertion<>(
            "empty Immutable must be equal to empty Immutable",
            new Immutable<>(),
            new IsEqual<>(new Immutable<>())
        ).affirm();
    }

    @Test
    public void testHashCode() {
        new Assertion<>(
            "hashCode() must be equal to hashCode of the corresponding List",
            new Immutable<>(1, 2, 3).hashCode(),
            new IsEqual<>(
                new Immutable<>(1, 2, 3).hashCode()
            )
        ).affirm();
    }

    @Test
    public void testToString() {
        new Assertion<>(
            "toString() must be concatenation of nested elements",
            new Immutable<>("a", "b", "c").toString(),
            new IsEqual<>("a, b, c")
        ).affirm();
    }
}
