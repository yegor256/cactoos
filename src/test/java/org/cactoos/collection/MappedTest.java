/**
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

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.Collections;
import java.util.Iterator;
import org.cactoos.Text;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterator.Endless;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.UpperText;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Mapped}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.14
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class MappedTest {

    @Test
    public void behavesAsCollection() throws Exception {
        MatcherAssert.assertThat(
            "Can't behave as a collection",
            new Mapped<Integer, Integer>(
                i -> i + 1,
                new IterableOf<>(-1, 1, 2)
            ),
            new BehavesAsCollection<>(0)
        );
    }

    @Test
    public void transformsList() throws IOException {
        MatcherAssert.assertThat(
            "Can't transform an iterable",
            new Mapped<String, Text>(
                input -> new UpperText(new TextOf(input)),
                new IterableOf<>("hello", "world", "друг")
            ).iterator().next().asString(),
            Matchers.equalTo("HELLO")
        );
    }

    @Test
    public void transformsEmptyList() {
        MatcherAssert.assertThat(
            "Can't transform an empty iterable",
            new Mapped<String, Text>(
                input -> new UpperText(new TextOf(input)),
                Collections.emptyList()
            ),
            Matchers.emptyIterable()
        );
    }

    @Test
    public void string() {
        MatcherAssert.assertThat(
            "Can't convert to string",
            new Mapped<Integer, Integer>(
                x -> x * 2,
                new ListOf<>(1, 2, 3)
            ).toString(),
            Matchers.equalTo("2, 4, 6")
        );
    }

    @Test
    public void transformsEndlessCollection() {
        MatcherAssert.assertThat(
            new Mapped<>(
                String::trim,
                new AbstractCollection<String>() {
                    @Override
                    public Iterator<String> iterator() {
                        return new Endless<>("something");
                    }
                    @Override
                    public int size() {
                        return 1;
                    }
                }
            ).size(),
            Matchers.equalTo(1)
        );
    }

}
