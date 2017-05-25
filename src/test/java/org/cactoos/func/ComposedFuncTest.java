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
package org.cactoos.func;

import java.io.IOException;
import org.cactoos.list.ArrayAsIterable;
import org.cactoos.list.FilteredIterable;
import org.cactoos.list.LengthOfIterable;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link ComposedFuncTest}.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class ComposedFuncTest {

    /**
     * ComposedFunc can composite two func.
     *
     * @throws IOException If some problem inside
     */
    @Test
    public void apply() throws IOException {
        MatcherAssert.assertThat(
            new LengthOfIterable(
                new FilteredIterable<>(
                    new ArrayAsIterable<>("the", "quick", "brown", "fox"),
                    new ComposedFunc<>(
                        input -> input.concat("_test"),
                        // @checkstyle MagicNumber (1 line)
                        input -> input.length() <= 8
                    )
                )
            ).asValue(),
            // @checkstyle MagicNumber (1 line)
            Matchers.equalTo(2)
        );
    }

}
