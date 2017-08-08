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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Func;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link SyncFunc}.
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class SyncFuncTest {

    @Test
    public void testSyncFunc() throws Exception {
        final Object lockobject = new Object();
        final int delta = 3;
        final int result = 9;
        final long mainwait = 10L;
        final long threadwait = 150L;
        final AtomicInteger resource = new AtomicInteger(5);
        final Func<Integer, Integer> func = new SyncFunc<>(
            input -> {
                TimeUnit.MILLISECONDS.sleep(threadwait);
                return resource.addAndGet(input);
            }, lockobject
        );
        new Thread(
            () -> {
                synchronized (lockobject) {
                    resource.addAndGet(delta);
                }
            }
        ).start();
        TimeUnit.MILLISECONDS.sleep(mainwait);
        MatcherAssert.assertThat(
            "Can't run in the sync",
            func.apply(1),
            Matchers.equalTo(result)
        );
    }

}
