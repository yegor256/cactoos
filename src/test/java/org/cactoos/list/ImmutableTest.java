/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Immutable}.
 *
 * @since 1.16
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.UnnecessaryLocalRule"})
final class ImmutableTest {

    @Test
    void innerListIsDecorated() {
        final List<String> strings = new ArrayList<>(Arrays.asList("a", "b", "c"));
        final List<String> immutable = new Immutable<>(strings);
        strings.add("d");
        MatcherAssert.assertThat(
            "Must reflect inner list in the decorator",
            immutable,
            new IsEqual<>(strings)
        );
    }

    @Test
    void returnsListIteratorWithUnsupportedRemove() {
        MatcherAssert.assertThat(
            "Must return a list iterator that does not support remove()",
            () -> {
                final List<String> list = new Immutable<>(new ListOf<>("alpha"));
                final ListIterator<String> iterator = list.listIterator();
                iterator.next();
                iterator.remove();
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }

    @Test
    void returnsListIteratorWithUnsupportedAdd() {
        MatcherAssert.assertThat(
            "Must return a list iterator that does not support add()",
            () -> {
                final List<String> list = new Immutable<>(new ListOf<>("beta"));
                final ListIterator<String> iterator = list.listIterator();
                iterator.next();
                iterator.add("gamma");
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }

    @Test
    void subListReturnsListIteratorWithUnsupportedRemove() {
        MatcherAssert.assertThat(
            "Must return a subtlist with a list iterator that does not support remove()",
            () -> {
                final List<String> list = new Immutable<>(new ListOf<>("delta"));
                final ListIterator<String> iterator = list.subList(0, 1).listIterator();
                iterator.next();
                iterator.remove();
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }

    @Test
    void subListReturnsListIteratorWithUnsupportedAdd() {
        MatcherAssert.assertThat(
            "Must return a subtlist with a list iterator that does not support add()",
            () -> {
                final List<String> list = new Immutable<>(new ListOf<>("epsilon"));
                final ListIterator<String> iterator = list.subList(0, 1).listIterator();
                iterator.next();
                iterator.add("zeta");
                return true;
            },
            new Throws<>(UnsupportedOperationException.class)
        );
    }

    @Test()
    void subListReturnsListIteratorWithUnsupportedSet() {
        MatcherAssert.assertThat(
            "subList.listIterator().set() must throw exception",
            () -> {
                final ListIterator<String> iterator = new Immutable<>(
                    new ListOf<>("eta", "theta", "iota")
                ).subList(0, 2).listIterator(0);
                iterator.next();
                iterator.set("kappa");
                return new Object();
            },
            new Throws<>(
                "List Iterator is read-only and doesn't allow rewriting items",
                UnsupportedOperationException.class
            )
        );
    }

    @Test()
    void returnsSubListWithUnsupportedSet() {
        MatcherAssert.assertThat(
            "subList.set() must throw exception",
            () -> new Immutable<>(new ListOf<>("lambda")).subList(0, 1).set(0, "mu"),
            new Throws<>(
                "#set(): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void size() {
        MatcherAssert.assertThat(
            "size() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).size(),
            new IsEqual<>(
                new ListOf<>(1, 2).size()
            )
        );
    }

    @Test
    void isEmpty() {
        MatcherAssert.assertThat(
            "isEmpty() must be equals to original",
            new Immutable<>(
                new ListOf<>(1, 2)
            ).isEmpty(),
            new IsEqual<>(
                new ListOf<>(1, 2).isEmpty()
            )
        );
    }

    @Test
    void contains() {
        MatcherAssert.assertThat(
            "contains() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b")
            ).contains("b"),
            new IsEqual<>(
                new ListOf<>("a", "b").contains("b")
            )
        );
    }

    @Test
    void iterator() {
        MatcherAssert.assertThat(
            "iterator() is equal to original",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).iterator(),
            new HasValues<>(1, 2)
        );
    }

    @Test
    void toArray() {
        MatcherAssert.assertThat(
            "toArray() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b")
            ).toArray(),
            new IsEqual<>(
                new ListOf<>("a", "b").toArray()
            )
        );
    }

    @Test
    void testToArray() {
        MatcherAssert.assertThat(
            "toArray(T[]) must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b")
            ).toArray(new String[0]),
            new IsEqual<>(
                new ListOf<>("a", "b").toArray(new String[0])
            )
        );
    }

    @Test
    void add() {
        MatcherAssert.assertThat(
            "add(T) must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).add(3),
            new Throws<>(
                "#add(T): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void remove() {
        MatcherAssert.assertThat(
            "remove(Object) must throw exception",
            () -> new Immutable<>(
                new ListOf<>("1", "2")
            ).remove("1"),
            new Throws<>(
                "#remove(Object): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void containsAll() {
        MatcherAssert.assertThat(
            "containsAll() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b", "c")
            ).containsAll(new ListOf<>("a", "c")),
            new IsEqual<>(
                new ListOf<>("a", "b", "c").containsAll(
                    new ListOf<>("a", "c")
                )
            )
        );
    }

    @Test
    void addAll() {
        MatcherAssert.assertThat(
            "addAll(Collection) must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).addAll(new ListOf<>(3, 4)),
            new Throws<>(
                "#addAll(Collection): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void testAddAll() {
        MatcherAssert.assertThat(
            "addAll(int, Collection) must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2)
            ).addAll(2, new ListOf<>(3, 4)),
            new Throws<>(
                "#addAll(int, Collection): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void removeAll() {
        MatcherAssert.assertThat(
            "removeAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).removeAll(new ListOf<>(1, 3)),
            new Throws<>(
                "#removeAll(): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void retainAll() {
        MatcherAssert.assertThat(
            "retainAll() must throw exception",
            () -> new Immutable<>(
                new ListOf<>(1, 2, 3)
            ).retainAll(new ListOf<>(1, 3)),
            new Throws<>(
                "#retainAll(): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void clear() {
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void get() {
        MatcherAssert.assertThat(
            "get() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b", "c")
            ).get(2),
            new IsEqual<>(
                new ListOf<>("a", "b", "c").get(2)
            )
        );
    }

    @Test
    void set() {
        MatcherAssert.assertThat(
            "set() must throw exception",
            () -> new Immutable<>(
                new ListOf<>("a", "b")
            ).set(3, "c"),
            new Throws<>(
                "#set(): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void testAdd() {
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void testRemove() {
        MatcherAssert.assertThat(
            "remove(int) must throw exception",
            () -> new Immutable<>(
                new ListOf<>("a", "b")
            ).remove(2),
            new Throws<>(
                "#remove(int): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void indexOf() {
        MatcherAssert.assertThat(
            "indexOf() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b", "c")
            ).indexOf("b"),
            new IsEqual<>(
                new ListOf<>("a", "b", "c").indexOf("b")
            )
        );
    }

    @Test
    void lastIndexOf() {
        MatcherAssert.assertThat(
            "lastIndexOf() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b", "c", "b")
            ).lastIndexOf("b"),
            new IsEqual<>(
                new ListOf<>("a", "b", "c", "b").lastIndexOf("b")
            )
        );
    }

    @Test
    void listIterator() {
        MatcherAssert.assertThat(
            "listIterator() is equal to original",
            () -> new Immutable<>(
                new ListOf<>("a", "b", "c", "b")
            ).listIterator(),
            new HasValues<>("a", "b", "c", "b")
        );
    }

    @Test
    void testListIterator() {
        MatcherAssert.assertThat(
            "listIterator(int) is equal to original",
            () -> new Immutable<>(
                new ListOf<>("a", "b", "c", "b")
            ).listIterator(2),
            new HasValues<>("c", "b")
        );
    }

    @Test
    void subList() {
        MatcherAssert.assertThat(
            "subList() must be equals to original",
            new Immutable<>(
                new ListOf<>("a", "b", "c")
            ).subList(1, 2),
            new IsEqual<>(
                new ListOf<>("a", "b", "c").subList(1, 2)
            )
        );
    }

    @Test
    void immutableSubList() {
        MatcherAssert.assertThat(
            "subList() result must be immutable",
            () -> new Immutable<>(
                new ListOf<>("a", "b", "c")
            ).subList(0, 2).add("d"),
            new Throws<>(
                "#add(T): the list is read-only",
                UnsupportedOperationException.class
            )
        );
    }

    @Test
    void notEqualsToObjectOfAnotherType() {
        MatcherAssert.assertThat(
            "must not equal to object of another type",
            new Immutable<>(new ListOf<>()),
            new IsNot<>(new IsEqual<>(new Object()))
        );
    }

    @Test
    void notEqualsToListWithDifferentElements() {
        MatcherAssert.assertThat(
            "must not equal to List with different elements",
            new Immutable<>(new ListOf<>(1, 2)),
            new IsNot<>(new IsEqual<>(new ListOf<>(1, 0)))
        );
    }

    @Test
    void isEqualToItself() {
        final List<Integer> list = new Immutable<>(new ListOf<>(1, 2));
        MatcherAssert.assertThat(
            "must be equal to itself",
            list,
            new IsEqual<>(list)
        );
    }

    @Test
    void isEqualToListWithTheSameElements() {
        MatcherAssert.assertThat(
            "must be equal to List with the same elements",
            new Immutable<>(new ListOf<>(1, 2)),
            new IsEqual<>(new ListOf<>(1, 2))
        );
    }

    @Test
    void equalToEmptyImmutable() {
        MatcherAssert.assertThat(
            "empty Immutable must be equal to empty Immutable",
            new Immutable<>(new ListOf<>()),
            new IsEqual<>(new Immutable<>(new ListOf<>()))
        );
    }

    @Test
    void testHashCode() {
        MatcherAssert.assertThat(
            "hashCode() must be equal to hashCode of the corresponding List",
            new Immutable<>(new ListOf<>(1, 2)).hashCode(),
            new IsEqual<>(
                new ListOf<>(1, 2).hashCode()
            )
        );
    }

    @Test
    void testToString() {
        MatcherAssert.assertThat(
            "toString() must be equal to toString of the corresponding List",
            new Immutable<>(new ListOf<>("a", "b", "c")).toString(),
            new IsEqual<>(new ListOf<>("a", "b", "c").toString())
        );
    }

    @Test
    void subListReturnsListIteratorWithSupportedSet() {
        MatcherAssert.assertThat(
            "subList's iterator must be immutable",
            () -> {
                final ListIterator<String> iterator = new Immutable<>(
                    new ListOf<>("nu", "xi", "omicron")
                )
                    .subList(0, 2)
                    .listIterator(0);
                iterator.next();
                iterator.set("pi");
                return new Object();
            },
            new Throws<>(
                "List Iterator is read-only and doesn't allow rewriting items",
                UnsupportedOperationException.class
            )
        );
    }

}
