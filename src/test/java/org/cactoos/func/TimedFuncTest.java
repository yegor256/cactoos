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

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import org.cactoos.iterable.Endless;
import org.cactoos.scalar.And;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link TimedFunc}.
 * @author Vedran Vatavuk (123vgv@gmail.com)
 * @version $Id$
 * @since 0.29.3
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class TimedFuncTest {

    @Test(expected = TimeoutException.class)
    public void functionGetsInterrupted() throws Exception {
        final long period = 100L;
        new TimedFunc<Boolean, Boolean>(
            input -> {
                return new And(
                    new Endless<>(() -> input)
                ).value();
            },
            period
        ).apply(true);
    }

    @Test(expected = TimeoutException.class)
    public void procGetsInterrupted() throws Exception {
        final long period = 100L;
        new TimedFunc<Boolean, Boolean>(
            input -> {
                new And(
                    new Endless<>(() -> input)
                ).value();
            },
            period
        ).apply(true);
    }

    @Test
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public void futureTaskIsCancelled() {
        final long period = 50L;
        final long time = 2000L;
        final Future<Boolean> future = Executors.newSingleThreadExecutor()
            .submit(
                () -> {
                    Thread.sleep(time);
                    return true;
                }
            );
        try {
            new TimedFunc<Boolean, Boolean>(
                period,
                input -> future
            ).apply(true);
                // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception exp) {
            MatcherAssert.assertThat(
                future.isCancelled(),
                Matchers.equalTo(true)
            );
        }
    }

    @Test
    public void functionIsExecuted() throws Exception {
        final long period = 3000L;
        MatcherAssert.assertThat(
            new TimedFunc<Boolean, Boolean>(
                input -> true,
                period
            ).apply(true),
            Matchers.equalTo(true)
        );
    }
}
