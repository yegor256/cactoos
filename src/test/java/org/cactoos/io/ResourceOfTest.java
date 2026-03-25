/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.util.Arrays;
import org.cactoos.Text;
import org.cactoos.bytes.BytesOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.StartsWith;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link ResourceOf}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ResourceOfTest {

    @Test
    void readsBinaryResource() throws Exception {
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void readsTextResource() {
        MatcherAssert.assertThat(
            "Must read a text resource from classpath",
            ResourceOfTest.large(),
            new EndsWith("est laborum.\n")
        );
    }

    @Test
    void readsTextResourceThroughClassloader() {
        MatcherAssert.assertThat(
            "Must read a text resource from classloader",
            ResourceOfTest.large(),
            new EndsWith(" laborum.\n")
        );
    }

    @Test
    void readAbsentResourceTest() {
        MatcherAssert.assertThat(
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
        );
    }

    @Test
    void throwsWhenResourceIsAbsent() {
        MatcherAssert.assertThat(
            "Doesn't fail for absent resource",
            () -> new TextOf(
                new ResourceOf(
                    "bar/this-resource-is-definitely-absent.txt"
                )
            ).asString(),
            new Throws<>(IOException.class)
        );
    }

    @Test
    void acceptsTextAsResourceName() {
        MatcherAssert.assertThat(
            "Can't accept Text as resource name",
            new TextOf(
                new ResourceOf(
                    new TextOf("org/cactoos/small-text.txt")
                )
            ),
            new EndsWith("ex ea commodo\n")
        );
    }

    @Test
    void acceptsTextsAsResourceNameAndFallback() {
        MatcherAssert.assertThat(
            "Can't use Texts as parameters",
            new TextOf(
                new ResourceOf(
                    new FormattedText("%s/absent.txt", "baz"),
                    new TextOf("another replacement")
                )
            ),
            new StartsWith("another")
        );
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
