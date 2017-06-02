/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.cactoos.Proc;
import org.cactoos.ScalarHasValue;
import org.cactoos.func.AlwaysTrueFunc;
import org.cactoos.func.FuncAsMatcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link AllOf}.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class AllOfTest {

    @Test
    public void iteratesList() {
        final List<String> list = new LinkedList<>();
        MatcherAssert.assertThat(
            "Can't iterate a list with a procedure",
            new AllOf(
                new TransformedIterable<>(
                    new ArrayAsIterable<>("hello", "world"),
                    list::add
                )
            ),
            new ScalarHasValue<>(
                Matchers.allOf(
                    Matchers.equalTo(true),
                    new FuncAsMatcher<>(
                        value -> list.size() == 2
                    )
                )
            )
        );
    }

    @Test
    public void iteratesEmptyList() {
        final List<String> list = new LinkedList<>();
        MatcherAssert.assertThat(
            "Can't iterate a list",
            new AllOf(
                new IterableAsBooleans<>(
                    Collections.emptyList(),
                    new AlwaysTrueFunc<>(
                        (Proc<String>) list::add
                    )
                )
            ),
            new ScalarHasValue<>(
                Matchers.allOf(
                    Matchers.equalTo(true),
                    new FuncAsMatcher<>(
                        value -> list.isEmpty()
                    )
                )
            )
        );
    }

}
