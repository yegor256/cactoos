/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
package org.cactoos.io;

import java.io.IOException;
import java.util.Arrays;
import org.cactoos.Text;
import org.cactoos.bytes.BytesOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.StartsWith;

/**
 * Test case for {@link ResourceOf}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ResourceOfTest {

    @Test
    public void readsBinaryResource() throws Exception {
        new Assertion<>(
            "Can't read bytes from a classpath resource",
            Arrays.copyOfRange(
                new BytesOf(
                    new ResourceOf(
                        "org/cactoos/io/ResourceOfTest.class"
                    )
                ).asBytes(),
                0,
                4
            ),
            new IsEqual<>(
                new byte[]{
                    (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE,
                }
            )
        ).affirm();
    }

    @Test
    public void readsTextResource() throws Exception {
        new Assertion<>(
            "Must read a text resource from classpath",
            ResourceOfTest.large(),
            new EndsWith("est laborum.\n")
        ).affirm();
    }

    @Test
    public void readsTextResourceThroughClassloader() throws Exception {
        new Assertion<>(
            "Must read a text resource from classloader",
            ResourceOfTest.large(),
            new EndsWith(" laborum.\n")
        ).affirm();
    }

    @Test
    public void readAbsentResourceTest() throws Exception {
        new Assertion<>(
            "Can't replace an absent resource with a text",
            new TextOf(
                new BytesOf(
                    new ResourceOf(
                        "foo/this-resource-is-definitely-absent.txt",
                        "the replacement"
                    )
                )
            ),
            new EndsWith("replacement")
        ).affirm();
    }

    @Test(expected = IOException.class)
    public void throwsWhenResourceIsAbsent() throws Exception {
        new TextOf(
            new ResourceOf(
                "bar/this-resource-is-definitely-absent.txt"
            )
        ).asString();
    }

    @Test
    public void acceptsTextAsResourceName() throws Exception {
        new Assertion<>(
            "Can't accept Text as resource name",
            new TextOf(
                new ResourceOf(
                    new TextOf("org/cactoos/small-text.txt")
                )
            ),
            new EndsWith("ex ea commodo")
        ).affirm();
    }

    @Test
    public void acceptsTextsAsResourceNameAndFallback() throws Exception {
        new Assertion<>(
            "Can't use Texts as parameters",
            new TextOf(
                new ResourceOf(
                    new FormattedText("%s/absent.txt", "baz"),
                    new TextOf("another replacement")
                )
            ),
            new StartsWith("another")
        ).affirm();
    }

    /**
     * Large text resource.
     * @return The content of the large resource
     */
    private static Text large() {
        return new TextOf(
            new ResourceOf(
                "org/cactoos/large-text.txt",
                ResourceOfTest.class
            )
        );
    }

}
