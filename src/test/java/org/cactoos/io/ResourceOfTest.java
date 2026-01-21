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
import org.cactoos.text.Split;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.StartsWith;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link ResourceOf}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert", "PMD.TooManyMethods"})
final class ResourceOfTest {

    @Test
    void readsBinaryResource() throws Exception {
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
    void readsTextResource() {
        new Assertion<>(
            "Must read a text resource from classpath",
            ResourceOfTest.large(),
            new EndsWith("est laborum.\n")
        ).affirm();
    }

    @Test
    void readsTextResourceThroughClassloader() {
        new Assertion<>(
            "Must read a text resource from classloader",
            ResourceOfTest.large(),
            new EndsWith(" laborum.\n")
        ).affirm();
    }

    @Test
    void readAbsentResourceTest() {
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

    @Test
    void throwsWhenResourceIsAbsent() {
        new Assertion<>(
            "Doesn't fail for absent resource",
            () -> new TextOf(
                new ResourceOf(
                    "bar/this-resource-is-definitely-absent.txt"
                )
            ).asString(),
            new Throws<>(IOException.class)
        ).affirm();
    }

    @Test
    void readTextFromJar() {
        new Assertion<>(
            "Can't to read file from jar",
            new TextOf(
                new BytesOf(
                    new ResourceOf(
                        "org/cactoos/io/small-text-file.txt",
                        "the replacement"
                    )
                )
            ),
            new EndsWith("parent directory.")
        ).affirm();
    }

    @Test
    void readDirectoryFromJar() {
        new Assertion<>(
            "Unable to read file names from jar directory",
            new Split(
                new TextOf(
                    new BytesOf(
                        new ResourceOf(
                            "org/cactoos/io/dir/",
                            "the replacement"
                        )
                    )
                ),
                new TextOf("\\n")
            ),
            new HasValues<>(
                new TextOf("second-text-file.txt"),
                new TextOf("small-file-in-dir.txt")
            )
        ).affirm();
    }

    @Test
    void readDirectory() {
        new Assertion<>(
            "Unable to read file names from directory",
            new Split(
                new TextOf(
                    new BytesOf(
                        new ResourceOf(
                            "org/cactoos/",
                            "the replacement"
                        )
                    )
                ),
                new TextOf("\\n")
            ),
            new HasValues<>(
                new TextOf("digest-calculation.txt"),
                new TextOf("small-text.txt")
            )
        ).affirm();
    }

    @Test
    void acceptsTextAsResourceName() {
        new Assertion<>(
            "Can't accept Text as resource name",
            new TextOf(
                new ResourceOf(
                    new TextOf("org/cactoos/small-text.txt")
                )
            ),
            new EndsWith("ex ea commodo\n")
        ).affirm();
    }

    @Test
    void acceptsTextsAsResourceNameAndFallback() {
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
