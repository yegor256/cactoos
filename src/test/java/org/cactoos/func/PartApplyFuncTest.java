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
package org.cactoos.func;

import org.cactoos.Scalar;
import org.cactoos.ScalarHasValue;
import org.cactoos.Text;
import org.cactoos.TextHasString;
import org.cactoos.scalar.MaxOf;
import org.cactoos.scalar.MinOf;
import org.cactoos.scalar.SumOf;
import org.cactoos.text.SubText;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link PartApplyFunc}.
 *
 * @author Vseslav Sekorin (vssekorin@gmail.com)
 * @version $Id$
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 line)
 */
public final class PartApplyFuncTest {

    @Test
    public void applyInteger() throws Exception {
        MatcherAssert.assertThat(
            new PartApplyFunc<Integer, Integer, Integer>(
                (fst, snd) -> fst * snd,
                2
            ).apply(3),
            Matchers.equalTo(6)
        );
    }

    @Test
    public void applyText() throws Exception {
        MatcherAssert.assertThat(
            new PartApplyFunc<Text, Scalar<Double>, Text>(
                (txt, nmb) -> new SubText(txt, nmb.value().intValue()),
                () -> "applyText"
            ).apply(() -> 5.5),
            new TextHasString("Text")
        );
    }

    @Test
    public void applyComplex() throws Exception {
        MatcherAssert.assertThat(
            new PartApplyFunc<Number, Text, Scalar<Boolean>>(
                (nmb, txt) -> () -> txt.asString().length() > nmb.intValue(),
                new SumOf(
                    new MaxOf(2, 3).intValue(),
                    new MinOf(-1, 1).intValue(),
                    7
                )
            ).apply(() -> "PartApplyFuncTest"),
            new ScalarHasValue<>(true)
        );
    }
}
