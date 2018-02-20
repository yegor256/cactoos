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
package org.cactoos.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link TeeInput}. Part B.
 * @author Roman Proshin (roman@proshin.org)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (650 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (650 lines)
 */
@SuppressWarnings(
    {"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals", "PMD.GodClass"}
)
public final class TeeInputPartBTest {

    /**
     * Test content for all the tests in this class.
     */
    private static final String CONTENT =
        "Hello, товарищ äÄ üÜ öÖ and ß";

    @Test
    public void copiesFromCharArrayWithCharsetToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartBTest.CONTENT.toCharArray(),
                output.toFile(),
                StandardCharsets.UTF_8
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromCharArrayWithCharsetByNameToFile()
        throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartBTest.CONTENT.toCharArray(),
                output.toFile(),
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromCharArrayToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                TeeInputPartBTest.CONTENT.toCharArray(),
                new OutputTo(output)
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromCharArrayWithCharsetToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                TeeInputPartBTest.CONTENT.toCharArray(),
                new OutputTo(output),
                StandardCharsets.UTF_8
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromCharArrayWithCharsetByNameToOutput()
        throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                TeeInputPartBTest.CONTENT.toCharArray(),
                new OutputTo(output),
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromTextToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new TextOf(TeeInputPartBTest.CONTENT),
                output
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromTextWithCharsetToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new TextOf(TeeInputPartBTest.CONTENT),
                output,
                StandardCharsets.UTF_8
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromTextWithCharsetByNameToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new TextOf(TeeInputPartBTest.CONTENT),
                output,
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromTextToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new TextOf(TeeInputPartBTest.CONTENT),
                output.toFile()
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromTextWithCharsetToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new TextOf(TeeInputPartBTest.CONTENT),
                output.toFile(),
                StandardCharsets.UTF_8
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromTextWithCharsetByNameToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new TextOf(TeeInputPartBTest.CONTENT),
                output.toFile(),
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromTextToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new TextOf(TeeInputPartBTest.CONTENT),
                new OutputTo(output)
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromTextWithCharsetToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new TextOf(TeeInputPartBTest.CONTENT),
                new OutputTo(output),
                StandardCharsets.UTF_8
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromTextWithCharsetByNameToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new TextOf(TeeInputPartBTest.CONTENT),
                new OutputTo(output),
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromBytesToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new BytesOf(TeeInputPartBTest.CONTENT),
                output
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromBytesToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new BytesOf(TeeInputPartBTest.CONTENT),
                output.toFile()
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromBytesToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new BytesOf(TeeInputPartBTest.CONTENT),
                new OutputTo(output)
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromInputToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new InputOf(TeeInputPartBTest.CONTENT),
                output
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromInputToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new InputOf(TeeInputPartBTest.CONTENT),
                output.toFile()
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromInputToWriter() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new InputOf(TeeInputPartBTest.CONTENT),
                new WriterTo(output)
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromInputWithSizeToWriter() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new InputOf(TeeInputPartBTest.CONTENT),
                new WriterTo(output),
                TeeInputPartBTest.CONTENT.length()
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromInputWithCharsetToWriter() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new InputOf(TeeInputPartBTest.CONTENT),
                new WriterTo(output),
                StandardCharsets.UTF_8
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromInputWithCharsetAndSizeToWriter() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new InputOf(TeeInputPartBTest.CONTENT),
                new WriterTo(output),
                StandardCharsets.UTF_8,
                TeeInputPartBTest.CONTENT.length()
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromInputWithCharsetByNameToWriter() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new InputOf(TeeInputPartBTest.CONTENT),
                new WriterTo(output),
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromInputWithCharsetByNameAndSizeToWriter()
        throws Exception {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new InputOf(TeeInputPartBTest.CONTENT),
                new WriterTo(output),
                StandardCharsets.UTF_8.name(),
                TeeInputPartBTest.CONTENT.length()
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromCharArrayToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartBTest.CONTENT.toCharArray(),
                output
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromCharArrayWithCharsetToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartBTest.CONTENT.toCharArray(),
                output,
                StandardCharsets.UTF_8
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromCharArrayWithCharsetByNameToPath()
        throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartBTest.CONTENT.toCharArray(),
                output,
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromCharArrayToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartBTest.CONTENT.toCharArray(),
                output.toFile()
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromByteArrayToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartBTest.CONTENT.getBytes(StandardCharsets.UTF_8),
                output.toFile()
            ),
            TeeInputPartBTest.CONTENT,
            () -> {
                try {
                    return new TextOf(output).asString();
                } catch (final IOException exception) {
                    throw new AssertionError(
                        "There was an IOException",
                        exception
                    );
                }
            }
        );
    }

    @Test
    public void copiesFromByteArrayToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                TeeInputPartBTest.CONTENT.getBytes(StandardCharsets.UTF_8),
                new OutputTo(output)
            ),
            TeeInputPartBTest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    /**
     * Asserts returned strings of the given TeeInput instance.
     *
     * @param input Instance of {@link TeeInput} class
     * @param result String, that must be returned
     * @param copied String, that must be copied into output
     * @throws IOException if any IO error occurs
     */
    private void assertThat(final TeeInput input, final String result,
        final Supplier<String> copied) throws IOException {
        final String text = new TextOf(input).asString();
        MatcherAssert.assertThat(
            "TeeInput result is different, than the input",
            text,
            Matchers.is(result)
        );
        MatcherAssert.assertThat(
            "Copied string is different, than the input string",
            text,
            Matchers.is(copied.get())
        );
    }
}
