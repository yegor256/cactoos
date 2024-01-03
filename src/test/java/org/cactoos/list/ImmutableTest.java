/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
import java.util.ListIterator;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Immutable}.
 *
 * @since 1.16
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
class ImmutableTest {

    @Test
    void innerListIsDecorated() {
        final List<String> strings = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final List<String> immutable = new Immutable<>(strings);
        strings.add("d");
        new Assertion<>(
            "Must reflect inner list in the decorator",
            immutable,
            new IsEqual<>(strings)
        ).affirm();
    }

    @Test
    void returnsListIteratorWithUnsupportedRemove() {
        new Assertion<>(
            "Must return a list iterator that does not support remove()",
            () -> {
                final List<String> list = new Immutable<>(new ListOf<>("one"));
                final ListIterator<String> iterator = list.listIterator();
                iterator.next();
                iterator.remove();
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void returnsListIteratorWithUnsupportedAdd() {
        new Assertion<>(
            "Must return a list iterator that does not support add()",
            () -> {
                final List<String> list = new Immutable<>(new ListOf<>("one"));
                final ListIterator<String> iterator = list.listIterator();
                iterator.next();
                iterator.add("two");
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void subListReturnsListIteratorWithUnsupportedRemove() {
        new Assertion<>(
            "Must return a subtlist with a list iterator that does not support remove()",
            () -> {
                final List<String> list = new Immutable<>(new ListOf<>("one"));
                final ListIterator<String> iterator = list.subList(0, 1).listIterator();
                iterator.next();
                iterator.remove();
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test
    void subListReturnsListIteratorWithUnsupportedAdd() {
        new Assertion<>(
            "Must return a subtlist with a list iterator that does not support add()",
            () -> {
                final List<String> list = new Immutable<>(new ListOf<>("one"));
                final ListIterator<String> iterator = list.subList(0, 1).listIterator();
                iterator.next();
                iterator.add("two");
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        ).affirm();
    }

    @Test()
    void subListReturnsListIteratorWithUnsupportedSet() {
        new Assertion<>(
            "subList.listIterator().set() must throw exception",
            () -> {
                final ListIterator<String> iterator = new Immutable<>(
                    new ListOf<>("one", "two", "three")
                ).subList(0, 2).listIterator(0);
                iterator.next();
                iterator.set("zero");
                return new Object();
            },
            new Throws<>(
                "List Iterator is read-only and doesn't allow rewriting items",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test()
    void returnsSubListWithUnsupportedSet() {
        new Assertion<>(
            "subList.set() must throw exception",
            () -> new Immutable<>(new ListOf<>("one")).subList(0, 1).set(0, "zero"),
            new Throws<>(
                "#set(): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void size() {
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
    void isEmpty() {
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
    void contains() {
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
    void iterator() {
        new Assertion<>(
            "iterator() is equal to original",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).iterator(),
            new HasValues<>(1, 2)
        ).affirm();
    }

    @Test
    void toArray() {
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
    void testToArray() {
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
    void add() {
        new Assertion<>(
            "add(T) must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).add(3),
            new Throws<>(
                "#add(T): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void remove() {
        new Assertion<>(
            "remove(Object) must throw exception",
            () -> new Immutable<>(
                new ListOf<>("1", "2")
            ).remove("1"),
            new Throws<>(
                "#remove(Object): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void containsAll() {
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
    void addAll() {
        new Assertion<>(
            "addAll(Collection) must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).addAll(new ListOf<>(3, 4)),
            new Throws<>(
                "#addAll(Collection): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void testAddAll() {
        new Assertion<>(
            "addAll(int, Collection) must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).addAll(2, new ListOf<>(3, 4)),
            new Throws<>(
                "#addAll(int, Collection): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void removeAll() {
        new Assertion<>(
            "removeAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).removeAll(new ListOf<>(1, 3)),
            new Throws<>(
                "#removeAll(): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void retainAll() {
        new Assertion<>(
            "retainAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).retainAll(new ListOf<>(1, 3)),
            new Throws<>(
                "#retainAll(): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void clear() {
        new Assertion<>(
            "clear() must throw exception",
            () -> {
                new Immutable<>(
                    new ListOf<>(1, 2, 3)
                ).clear();
                return new Object();
            },
            new Throws<>(
                "#clear(): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void get() {
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
    void set() {
        new Assertion<>(
            "set() must throw exception",
            () -> new Immutable<>(
                new ListOf<>("a", "b")
            ).set(3, "c"),
            new Throws<>(
                "#set(): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void testAdd() {
        new Assertion<>(
            "add(int, T) must throw exception",
            () -> {
                new Immutable<>(
                    new ListOf<>("a", "b")
                ).add(3, "c");
                return new Object();
            },
            new Throws<>(
                "#add(int, T): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void testRemove() {
        new Assertion<>(
            "remove(int) must throw exception",
            () -> new Immutable<>(
                new ListOf<>("a", "b")
            ).remove(2),
            new Throws<>(
                "#remove(int): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void indexOf() {
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
    void lastIndexOf() {
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
    void listIterator() {
        new Assertion<>(
            "listIterator() is equal to original",
            () -> new Immutable<>(
                new ListOf<>("a", "b", "c", "b")
            ).listIterator(),
            new HasValues<>("a", "b", "c", "b")
        ).affirm();
    }

    @Test
    void testListIterator() {
        new Assertion<>(
            "listIterator(int) is equal to original",
            () -> new Immutable<>(
                new ListOf<>("a", "b", "c", "b")
            ).listIterator(2),
            new HasValues<>("c", "b")
        ).affirm();
    }

    @Test
    void subList() {
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
    void immutableSubList() {
        new Assertion<>(
            "subList() result must be immutable",
            () -> new Immutable<>(
                new ListOf<>("a", "b", "c")
            ).subList(0, 2).add("d"),
            new Throws<>(
                "#add(T): the list is read-only",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    void notEqualsToObjectOfAnotherType() {
        new Assertion<>(
            "must not equal to object of another type",
            new Immutable<>(new ListOf<>()),
            new IsNot<>(new IsEqual<>(new Object()))
        ).affirm();
    }

    @Test
    void notEqualsToListWithDifferentElements() {
        new Assertion<>(
            "must not equal to List with different elements",
            new Immutable<>(new ListOf<>(1, 2)),
            new IsNot<>(new IsEqual<>(new ListOf<>(1, 0)))
        ).affirm();
    }

    @Test
    void isEqualToItself() {
        final List<Integer> list = new Immutable<>(new ListOf<>(1, 2));
        new Assertion<>(
            "must be equal to itself",
            list,
            new IsEqual<>(list)
        ).affirm();
    }

    @Test
    void isEqualToListWithTheSameElements() {
        new Assertion<>(
            "must be equal to List with the same elements",
            new Immutable<>(new ListOf<>(1, 2)),
            new IsEqual<>(new ListOf<>(1, 2))
        ).affirm();
    }

    @Test
    void equalToEmptyImmutable() {
        new Assertion<>(
            "empty Immutable must be equal to empty Immutable",
            new Immutable<>(new ListOf<>()),
            new IsEqual<>(new Immutable<>(new ListOf<>()))
        ).affirm();
    }

    @Test
    void testHashCode() {
        new Assertion<>(
            "hashCode() must be equal to hashCode of the corresponding List",
            new Immutable<>(new ListOf<>(1, 2)).hashCode(),
            new IsEqual<>(
                new ListOf<>(1, 2).hashCode()
            )
        ).affirm();
    }

    @Test
    void testToString() {
        new Assertion<>(
            "toString() must be equal to toString of the corresponding List",
            new Immutable<>(new ListOf<>("a", "b", "c")).toString(),
            new IsEqual<>(new ListOf<>("a", "b", "c").toString())
        ).affirm();
    }

    @Test
    void subListReturnsListIteratorWithSupportedSet() {
        new Assertion<>(
            "subList's iterator must be immutable",
            () -> {
                final ListIterator<String> iterator = new Immutable<>(
                    new ListOf<>("one", "two", "three")
                )
                    .subList(0, 2)
                    .listIterator(0);
                iterator.next();
                iterator.set("zero");
                return new Object();
            },
            new Throws<>(
                "List Iterator is read-only and doesn't allow rewriting items",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

}
