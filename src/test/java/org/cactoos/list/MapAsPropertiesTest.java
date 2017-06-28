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

import java.security.SecureRandom;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.ScalarHasValue;
import org.cactoos.func.FuncAsMatcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link MapAsProperties}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class MapAsPropertiesTest {

    @Test
    public void convertsMapToProperties() {
        MatcherAssert.assertThat(
            "Can't convert map to properties",
            new MapAsProperties(
                new StickyMap<>(
                    new IterableAsMap<Integer, String>(
                        new MapEntry<>(0, "hello, world"),
                        new MapEntry<>(1, "how are you?")
                    )
                )
            ),
            new ScalarHasValue<>(
                new FuncAsMatcher<Properties>(
                    props -> props.getProperty("0").endsWith(", world")
                )
            )
        );
    }

    @Test
    public void sensesChangesInMap() throws Exception {
        final AtomicInteger size = new AtomicInteger(2);
        final MapAsProperties props = new MapAsProperties(
            new IterableAsMap<>(
                () -> new RepeatIterator<>(
                    () -> new MapEntry<>(
                        new SecureRandom().nextInt(),
                        1
                    ),
                    size.incrementAndGet()
                )
            )
        );
        MatcherAssert.assertThat(
            "Can't sense the changes in the underlying map",
            props.value().size(),
            Matchers.not(Matchers.equalTo(props.value().size()))
        );
    }

}
