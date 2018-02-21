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
 * Test case for {@link TeeInput}. Part A.
 *
 * @todo #631 Split this test class on small ones based on source of the
 * TeeInput. I expect a number of small test classes for each source of
 * TeeInput (e.g. TeeInputFromUriTest, TeeInputFromPathTest etc.)
 *
 * @author Roman Proshin (roman@proshin.org)
 * @version $Id$
 * @since 1.0
 * @checkstyle JavadocMethodCheck (1000 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (1000 lines)
 */
@SuppressWarnings(
    {"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals", "PMD.GodClass"}
)
public final class TeeInputPartATest {

    /**
     * Test content for all the tests in this class.
     */
    private static final String CONTENT =
        "Hello, товарищ äÄ üÜ öÖ and ß";

    @Test
    public void copiesFromUrlToPath() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                input
                    .toUri()
                    .toURL(),
                output
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromUrlToFile() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                input
                    .toUri()
                    .toURL(),
                output.toFile()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromUrlToOutput() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                input.toUri()
                    .toURL(),
                new OutputTo(output)
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromUriToPath() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                input.toUri(),
                output
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromUriToFile() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                input.toUri(),
                output.toFile()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromUriToOutput() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                input.toUri(),
                new OutputTo(output)
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromPathToPath() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                input,
                output
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromPathToFile() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                input,
                output.toFile()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromPathToOutput() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                input,
                new OutputTo(output)
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromFileToFile() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                input.toFile(),
                output.toFile()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromFileToPath() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                input.toFile(),
                output
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromFileToOutput() throws IOException {
        final Path input = Files.createTempFile("input", ".txt");
        Files.write(
            input,
            TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8)
        );
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                input.toFile(),
                new OutputTo(output)
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromCharSequenceToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartATest.CONTENT,
                output.toFile()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromCharSequenceWithCharsetToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartATest.CONTENT,
                output.toFile(),
                StandardCharsets.UTF_8
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromCharSequenceWithCharsetByNameToFile()
        throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartATest.CONTENT,
                output.toFile(),
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromCharSequenceToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartATest.CONTENT,
                output
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromCharSequenceWithCharsetToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartATest.CONTENT,
                output,
                StandardCharsets.UTF_8
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromCharSequenceWithCharsetByNameToPath()
        throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartATest.CONTENT,
                output,
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromCharSequenceToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                TeeInputPartATest.CONTENT,
                new OutputTo(output)
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromCharSequenceWithCharsetToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                TeeInputPartATest.CONTENT,
                new OutputTo(output),
                StandardCharsets.UTF_8
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromCharSequenceWithCharsetByNameToOutput()
        throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                TeeInputPartATest.CONTENT,
                new OutputTo(output),
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromReaderToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output.toFile()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderWithSizeToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output.toFile(),
                TeeInputPartATest.CONTENT.length()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderWithCharsetToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output.toFile(),
                StandardCharsets.UTF_8
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderWithCharsetAndSizeToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output.toFile(),
                StandardCharsets.UTF_8,
                TeeInputPartATest.CONTENT.length()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderWithCharsetByNameToFile() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output.toFile(),
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderWithCharsetByNameAndSizeToFile()
        throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output.toFile(),
                StandardCharsets.UTF_8.name(),
                TeeInputPartATest.CONTENT.length()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderWithSizeToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output,
                TeeInputPartATest.CONTENT.length()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderWithCharsetToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output,
                StandardCharsets.UTF_8
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderWithCharsetAndSizeToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output,
                StandardCharsets.UTF_8,
                TeeInputPartATest.CONTENT.length()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderWithCharsetByNameToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output,
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderWithCharsetByNameAndSizeToPath()
        throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                output,
                StandardCharsets.UTF_8.name(),
                TeeInputPartATest.CONTENT.length()
            ),
            TeeInputPartATest.CONTENT,
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
    public void copiesFromReaderToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                new OutputTo(output)
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromReaderWithSizeToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                new OutputTo(output),
                TeeInputPartATest.CONTENT.length()
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                new OutputTo(output),
                StandardCharsets.UTF_8
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetAndSizeToOutput()
        throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                new OutputTo(output),
                StandardCharsets.UTF_8,
                TeeInputPartATest.CONTENT.length()
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetByNameToOutput() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                new OutputTo(output),
                StandardCharsets.UTF_8.name()
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromReaderWithCharsetByNameAndSizeToOutput()
        throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.assertThat(
            new TeeInput(
                new ReaderOf(TeeInputPartATest.CONTENT),
                new OutputTo(output),
                StandardCharsets.UTF_8.name(),
                TeeInputPartATest.CONTENT.length()
            ),
            TeeInputPartATest.CONTENT,
            () -> new String(
                output.toByteArray(),
                StandardCharsets.UTF_8
            )
        );
    }

    @Test
    public void copiesFromByteArrayToPath() throws IOException {
        final Path output = Files.createTempFile("output", ".txt");
        this.assertThat(
            new TeeInput(
                TeeInputPartATest.CONTENT.getBytes(StandardCharsets.UTF_8),
                output
            ),
            TeeInputPartATest.CONTENT,
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
