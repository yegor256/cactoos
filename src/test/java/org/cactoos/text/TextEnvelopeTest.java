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
package org.cactoos.text;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link TextEnvelope}.
 * @author Paulo Lobo (pauloeduardolobo@gmail.com)
 * @version $Id$
 * @since 0.31
 */
public final class TextEnvelopeTest {
    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert that
     * the enveloped {@link TextOf} value is equal to its string.
     */
    @Test
    public void testEquals() {
        final String equals = "equals";
        MatcherAssert.assertThat(
            "Enveloped value does not match its represented String value",
            new TextEnvelope(new TextOf(equals)),
            Matchers.is(equals)
        );
    }
    /**
     * Test for {@link TextEnvelope#hashCode()} method. Must assert that the
     * {@link TextEnvelope} hashCode is equals to the hashCode of the String
     * it represents.
     */
    @Test
    public void testHashCode() {
        final String hash = "hashCode";
        MatcherAssert.assertThat(
            "Enveloped hashCode does not match its represented String hashcode",
            new TextEnvelope(new TextOf(hash)).hashCode(),
            Matchers.is(hash.hashCode())
        );
    }
}
