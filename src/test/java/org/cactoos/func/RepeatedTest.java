/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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

import java.security.SecureRandom;
import java.util.Iterator;
import org.cactoos.Func;
import org.cactoos.iterator.IteratorOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Repeated}.
 *
 * @since 0.13.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class RepeatedTest {

    @Test
    void runsFuncMultipleTimes() throws Exception {
        final Iterator<Integer> iter = new IteratorOf<>(1, 2, 5, 6);
        final Func<Boolean, Integer> func = new Repeated<>(
            input -> iter.next(),
            3
        );
        new Assertion<>(
            "Must be applied 3 times",
            func.apply(true),
            new IsEqual<>(5)
        ).affirm();
    }

    @Test
    void repeatsNullsResults() throws Exception {
        final Func<Boolean, Integer> func = new Repeated<>(
            input -> null,
            2
        );
        new Assertion<>(
            "Must repeat NULL",
            func.apply(true),
            new IsNull<>()
        ).affirm();
    }

    @Test
    void doesntRepeatAny() {
        final Func<Boolean, Integer> func = new Repeated<>(
            input -> new SecureRandom().nextInt(),
            0
        );
        new Assertion<>(
            "Must throw if parameter is not correct",
            () -> func.apply(true),
            new Throws<>(
                IllegalArgumentException.class
            )
        ).affirm();
    }
}
