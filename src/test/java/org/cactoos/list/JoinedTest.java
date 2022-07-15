/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link Joined}.
 *
 * @since 0.20
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 line)
 * @checkstyle DiamondOperatorCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class JoinedTest {

    /**
     * Literal ONE value.
     */
    private static final String LITERAL_ONE = "ONE";

    /**
     * Literal TWO value.
     */
    private static final String LITERAL_TWO = "TWO";

    /**
     * Literal THREE value.
     */
    private static final String LITERAL_THREE = "THREE";

    /**
     * Literal FOUR value.
     */
    private static final String LITERAL_FOUR = "FOUR";

    @Test
    public void behavesAsCollection() {
        new Assertion<>(
            "Can't behave as a list",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ),
            new BehavesAsList<>(JoinedTest.LITERAL_TWO)
        ).affirm();
    }

    @Test
    public void size() {
        new Assertion<>(
            "Must evaluate the size of the joined list",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).size(),
            new IsEqual<>(4)
        ).affirm();
    }

    @Test
    public void isEmpty() {
        new Assertion<>(
            "Must be evaluated as an empty list",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).isEmpty(),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @Test
    public void contains() {
        new Assertion<>(
            "Must contain element specified",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).contains(JoinedTest.LITERAL_THREE),
            new IsTrue()
        ).affirm();
    }

    @Test
    public void iterator() {
        new Assertion<>(
            "Joined Iterator Must return next element equal to the first added",
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
            "Must be able to add element specified",
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
            "Must be able to remove element specified",
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
            "Must contain all elements",
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
            "Must be able to addAll elements specified",
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
            "Must be able to addAll elements in front",
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
            "Must be able to removeAll elements specified",
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
            "Must be able to retain all",
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
            "Must be able to clear",
            joined.size(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void get() {
        new Assertion<>(
            "Must get element",
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
            "Must be able to set element by specified index",
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
            "Must be able to add element by specified index",
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
            "Must be able to remove element by specified index",
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
            "Must be able to remove element by specified element",
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
            "Must be able to to get sub list",
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
            "Must be able to join element with a list",
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

    @Test
    public void infersCorrectly() {
        new Assertion<>(
            "Must be able to infer type of elements",
            new Joined<>(
                Integer.valueOf(1),
                new ListOf<>(
                    Double.valueOf(2),
                    Double.valueOf(3)
                )
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Number>>(
                    new IsInstanceOf(Integer.class),
                    new IsInstanceOf(Double.class),
                    new IsInstanceOf(Double.class)
                )
            )
        ).affirm();
    }
}
