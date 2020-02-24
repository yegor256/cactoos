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
@SuppressWarnings({"PMD.TooManyMethods"})
public final class JoinedTest {
    private static final String LITERAL_ONE = "ONE";
    private static final String LITERAL_TWO = "TWO";
    private static final String LITERAL_THREE = "THREE";
    private static final String LITERAL_FOUR = "FOUR";

    @Test
    public void behavesAsCollection() {
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
    public void size() {
        MatcherAssert.assertThat(
            new Joined<Integer>(
                new ListOf<>(1, 2),
                new ListOf<>(3, 4)
            ).size(),
            new IsEqual<>(4)
        );
    }

    @Test
    public void isEmpty() {
        MatcherAssert.assertThat(
            new Joined<Integer>(
                new ListOf<Integer>(5, 6)
            ).isEmpty(),
            new IsEqual<>(false)
        );
    }

    @Test
    public void contains() {
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
    public void iterator() {
        new Assertion<>(
            "Joined Iterator must return next element equal to the first added",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).iterator().next(),
            new IsEqual<>(
                JoinedTest.LITERAL_ONE
            )
        ).affirm();
    }

    @Test
    public void add() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.add(JoinedTest.LITERAL_THREE);
        new Assertion<>(
            "must be able to add element specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
    }

    @Test
    public void remove() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.remove(JoinedTest.LITERAL_TWO);
        new Assertion<>(
            "must be able to remove element specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE
                )
            )
        ).affirm();
    }

    @Test
    public void containsAll() {
        new Assertion<>(
            "must contain all elements",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_THREE),
                new ListOf<>(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_FOUR)
            ).containsAll(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO
                )
            ),
            new IsTrue()
        ).affirm();
    }

    @Test
    public void addAll() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.addAll(
            new ListOf<>(
                JoinedTest.LITERAL_THREE,
                JoinedTest.LITERAL_FOUR
            )
        );
        new Assertion<>(
            "must be able to addAll elements specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE,
                    JoinedTest.LITERAL_FOUR
                )
            )
        ).affirm();
    }

    @Test
    public void addAllInFront() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.addAll(
            0,
            new ListOf<>(
                JoinedTest.LITERAL_THREE,
                JoinedTest.LITERAL_FOUR
            )
        );
        new Assertion<>(
            "must be able to addAll elements in front",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_THREE,
                    JoinedTest.LITERAL_FOUR,
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO
                )
            )
        ).affirm();
    }

    @Test
    public void removeAll() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(
                JoinedTest.LITERAL_ONE,
                JoinedTest.LITERAL_TWO
            ),
            new ListOf<>(JoinedTest.LITERAL_THREE)
        );
        joined.removeAll(
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            )
        );
        new Assertion<>(
            "must be able to removeAll elements specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE
                )
            )
        ).affirm();
    }

    @Test
    public void retainAll() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            )
        );
        joined.retainAll(
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            )
        );
        new Assertion<>(
            "must be able to retain all",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
    }

    @Test
    public void clear() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            ),
            new ListOf<>(
                JoinedTest.LITERAL_ONE
            )
        );
        joined.clear();
        new Assertion<>(
            "must be able to clear",
            joined.size(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void get() {
        new Assertion<>(
            "must get element",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_ONE
                )
            ).get(1),
            new IsEqual<>(JoinedTest.LITERAL_THREE)
        ).affirm();
    }

    @Test
    public void set() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.set(0, JoinedTest.LITERAL_THREE);
        new Assertion<>(
            "must be able to set element by specified index",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_THREE)
        ).affirm();
    }

    @Test
    public void addByIndex() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.add(0, JoinedTest.LITERAL_THREE);
        new Assertion<>(
            "must be able to add element by specified index",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_THREE)
        ).affirm();
    }

    @Test
    public void removeByIndex() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.remove(0);
        new Assertion<>(
            "must be able to remove element by specified index",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_TWO)
        ).affirm();
    }

    @Test
    public void removeByElement() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.remove(JoinedTest.LITERAL_ONE);
        new Assertion<>(
            "must be able to remove element by specified element",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_TWO)
        ).affirm();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void listIteratorSecond() {
        new Joined<Integer>().listIterator(66);
    }

    @Test
    public void subList() {
        new Assertion<>(
            "must be able to to get sub list",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_THREE)
            ).subList(1, 3),
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
    }

    @Test
    public void itemAndList() {
        new Assertion<>(
            "must be able to join element with a list",
            new Joined<>(
                JoinedTest.LITERAL_ONE,
                new ListOf<>(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_THREE)
            ),
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        ).affirm();
    }
}
