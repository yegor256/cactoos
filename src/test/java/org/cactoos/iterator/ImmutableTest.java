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
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.cactoos.text.Randomized;
import org.cactoos.text.TextOf;
import org.cactoos.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.TextIs;

/**
 * Test Case for {@link Immutable}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class ImmutableTest {
    @Test(expected = UnsupportedOperationException.class)
    public void doesNotAllowRemovingOfElements() {
        final List<String> list = new LinkedList<>();
        list.add("one");
        final Iterator<String> immutable = new Immutable<>(list.iterator());
        immutable.next();
        immutable.remove();
    }

    @Test
    public void decoratesNext() {
        final int value = new Random().nextInt();
        final Iterator<Integer> immutable = new Immutable<>(
            new IteratorOf<>(value)
        );
        MatcherAssert.assertThat(immutable.next(), new IsEqual<>(value));
    }

    @Test
    public void decoratesHasNext() {
        final int value = new Random().nextInt();
        final Iterator<Integer> immutable = new Immutable<>(
            new IteratorOf<>(value)
        );
        MatcherAssert.assertThat(immutable.hasNext(), new IsEqual<>(true));
        immutable.next();
        MatcherAssert.assertThat(immutable.hasNext(), new IsEqual<>(false));
    }

    @Test
    public void decoratesToString() {
        final String string = new Randomized().asString();
        final Iterator<Object> iterator = new Iterator<Object>() {
            public Object next() {
                return new Object();
            }

            public boolean hasNext() {
                return false;
            }

            public String toString() {
                return string;
            }
        };
        final Iterator<Object> immutable = new Immutable<>(iterator);
        new Assertion<>(
            "must delegate toString to decorated iterator",
            new TextOf(immutable.toString()),
            new TextIs(iterator.toString())
        ).affirm();
    }
}
