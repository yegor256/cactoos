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
package org.cactoos.proc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.cactoos.text.FormattedText;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link ProcOf}.
 *
 * @since 0.3
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ProcOfTest {

    @Test
    void worksWithRunnable() throws Exception {
        final String str = "test string";
        final List<String> list = new ArrayList<>(1);
        new ProcOf<String>(
            new Runnable() {
                @Override
                public void run() {
                    list.add(str);
                }
            }
        ).exec("You can use any input in a Runnable.");
        new Assertion<>(
            new FormattedText(
                "Wrong result when created with a Runnable. Expected %s",
                str
            ).asString(),
            list,
            new HasValues<>(str)
        ).affirm();
    }

    @Test
    void worksWithCallable() throws Exception {
        final String str = "test str";
        final List<String> list = new ArrayList<>(1);
        new ProcOf<String>(
            new Callable<String>() {
                @Override
                public String call() throws Exception {
                    list.add(str);
                    return "Any return value can be used.";
                }
            }
        ).exec("You can use any input in a Callable");
        new Assertion<>(
            new FormattedText(
                "Wrong result when created with a Callable. Expected %s",
                str
            ).asString(),
            list,
            new HasValues<>(str)
        ).affirm();
    }

    @Test
    void worksWithFunc() throws Exception {
        final String str = "test input";
        final List<String> list = new ArrayList<>(1);
        new ProcOf<String>(
            input -> {
                list.add(input);
                return list.size();
            }
        ).exec(str);
        new Assertion<>(
            new FormattedText(
                "Wrong result when created with a Func. Expected %s",
                str
            ).asString(),
            list,
            new HasValues<>(str)
        ).affirm();
    }
}
