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
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link StartsWith}.
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public class StartsWithTest {

    @Test
    public void determinesEmptyStartsWithEmpty() {
        MatcherAssert.assertThat(
            "Empty is not prefix of empty",
            new StartsWith(
                new TextOf(""), new TextOf("")
            ),
            new ScalarHasValue<>(Boolean.TRUE)
        );
    }
    
    @Test
    public void determinesTextStartsWithEmpty() {
        MatcherAssert.assertThat(
            "Empty is not prefix of any string",
            new StartsWith(
                new TextOf("Any string"), new TextOf("")
            ),
            new ScalarHasValue<>(Boolean.TRUE)
        );
    }
	
    @Test
    public void determinesTextStartsWithPrefix() {
        MatcherAssert.assertThat(
            "Any is not prefix of any string",
            new StartsWith(
                new TextOf("Any string"), new TextOf("Any")
            ),
            new ScalarHasValue<>(Boolean.TRUE)
        );
    }
    
}
