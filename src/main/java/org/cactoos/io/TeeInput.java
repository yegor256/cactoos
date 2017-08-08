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
package org.cactoos.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Output;
import org.cactoos.Text;

/**
 * Input to Output copying pipe.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class TeeInput implements Input {

    /**
     * The source.
     */
    private final Input source;

    /**
     * The destination.
     */
    private final Output target;

    /**
     * Ctor.
     * @param url The source
     * @param path The output file
     * @since 0.13.3
     */
    public TeeInput(final URL url, final Path path) {
        this(new InputOf(url), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param url The source
     * @param file The output file
     * @since 0.13.3
     */
    public TeeInput(final URL url, final File file) {
        this(new InputOf(url), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param url The source
     * @param output The output file
     * @since 0.13.3
     */
    public TeeInput(final URL url, final Output output) {
        this(new InputOf(url), output);
    }

    /**
     * Ctor.
     * @param uri The source
     * @param path The output file
     * @since 0.13.3
     */
    public TeeInput(final URI uri, final Path path) {
        this(new InputOf(uri), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param uri The source
     * @param file The output file
     * @since 0.13.3
     */
    public TeeInput(final URI uri, final File file) {
        this(new InputOf(uri), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param uri The source
     * @param output The output file
     * @since 0.13.3
     */
    public TeeInput(final URI uri, final Output output) {
        this(new InputOf(uri), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param path The output file
     * @since 0.5
     */
    public TeeInput(final Path input, final Path path) {
        this(new InputOf(input), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.5
     */
    public TeeInput(final Path input, final File file) {
        this(new InputOf(input), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The output
     * @since 0.13.3
     */
    public TeeInput(final Path input, final Output output) {
        this(new InputOf(input), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.5
     */
    public TeeInput(final File input, final File file) {
        this(new InputOf(input), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param path The output file
     * @since 0.5
     */
    public TeeInput(final File input, final Path path) {
        this(new InputOf(input), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The output
     * @since 0.13.3
     */
    public TeeInput(final File input, final Output output) {
        this(new InputOf(input), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.5
     */
    public TeeInput(final CharSequence input, final File file) {
        this(new InputOf(input), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final CharSequence input, final File file,
        final Charset charset) {
        this(new InputOf(input, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final CharSequence input, final File file,
        final CharSequence charset) {
        this(new InputOf(input, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.5
     */
    public TeeInput(final CharSequence input, final Path file) {
        this(new InputOf(input), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final CharSequence input, final Path file,
        final Charset charset) {
        this(new InputOf(input, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final CharSequence input, final Path file,
        final CharSequence charset) {
        this(new InputOf(input, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     * @since 0.5
     */
    public TeeInput(final CharSequence input, final Output output) {
        this(new InputOf(input), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final CharSequence input, final Output output,
        final Charset charset) {
        this(new InputOf(input, charset), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final CharSequence input, final Output output,
        final CharSequence charset) {
        this(new InputOf(input, charset), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final File file) {
        this(new InputOf(input), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param size Reading buffer size
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final File file, final int size) {
        this(new InputOf(input, size), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final File file,
        final Charset charset) {
        this(new InputOf(input, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @param size Reading buffer size
     * @since 0.13.3
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public TeeInput(final Reader input, final File file,
        final Charset charset, final int size) {
        this(new InputOf(input, charset, size), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final File file,
        final CharSequence charset) {
        this(new InputOf(input, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @param size Reading buffer size
     * @since 0.13.3
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public TeeInput(final Reader input, final File file,
        final CharSequence charset, final int size) {
        this(new InputOf(input, charset, size), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final Path file) {
        this(new InputOf(input), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param size Reading buffer size
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final Path file, final int size) {
        this(new InputOf(input, size), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final Path file,
        final Charset charset) {
        this(new InputOf(input, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @param size Reading buffer size
     * @since 0.13.3
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public TeeInput(final Reader input, final Path file,
        final Charset charset, final int size) {
        this(new InputOf(input, charset, size), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final Path file,
        final CharSequence charset) {
        this(new InputOf(input, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset The charset
     * @param size Reading buffer size
     * @since 0.13.3
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public TeeInput(final Reader input, final Path file,
        final CharSequence charset, final int size) {
        this(new InputOf(input, charset, size), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final Output output) {
        this(new InputOf(input), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     * @param size Reading buffer size
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final Output output, final int size) {
        this(new InputOf(input, size), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final Output output,
        final Charset charset) {
        this(new InputOf(input, charset), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     * @param charset The charset
     * @param size Reading buffer size
     * @since 0.13.3
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public TeeInput(final Reader input, final Output output,
        final Charset charset, final int size) {
        this(new InputOf(input, charset, size), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final Reader input, final Output output,
        final CharSequence charset) {
        this(new InputOf(input, charset), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     * @param charset The charset
     * @param size Reading buffer size
     * @since 0.13.3
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public TeeInput(final Reader input, final Output output,
        final CharSequence charset, final int size) {
        this(new InputOf(input, charset, size), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param path The output path
     * @since 0.5
     */
    public TeeInput(final byte[] input, final Path path) {
        this(new InputOf(input), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.5
     */
    public TeeInput(final byte[] input, final File file) {
        this(new InputOf(input), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The output file
     * @since 0.5
     */
    public TeeInput(final byte[] input, final Output output) {
        this(new InputOf(input), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param path The output path
     * @since 0.5
     */
    public TeeInput(final char[] input, final Path path) {
        this(new InputOf(input), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param input The source
     * @param path The output path
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final char[] input, final Path path,
        final Charset charset) {
        this(new InputOf(input, charset), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param input The source
     * @param path The output path
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final char[] input, final Path path,
        final CharSequence charset) {
        this(new InputOf(input, charset), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.5
     */
    public TeeInput(final char[] input, final File file) {
        this(new InputOf(input), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final char[] input, final File file,
        final Charset charset) {
        this(new InputOf(input, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final char[] input, final File file,
        final CharSequence charset) {
        this(new InputOf(input, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The output file
     * @since 0.5
     */
    public TeeInput(final char[] input, final Output output) {
        this(new InputOf(input), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The output file
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final char[] input, final Output output,
        final Charset charset) {
        this(new InputOf(input, charset), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The output file
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final char[] input, final Output output,
        final CharSequence charset) {
        this(new InputOf(input, charset), output);
    }

    /**
     * Ctor.
     * @param text The source
     * @param path The output path
     * @since 0.13.3
     */
    public TeeInput(final Text text, final Path path) {
        this(new InputOf(text), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param text The source
     * @param path The output path
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final Text text, final Path path, final Charset charset) {
        this(new InputOf(text, charset), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param text The source
     * @param path The output path
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final Text text, final Path path,
        final CharSequence charset) {
        this(new InputOf(text, charset), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param text The source
     * @param file The output file
     * @since 0.13.3
     */
    public TeeInput(final Text text, final File file) {
        this(new InputOf(text), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param text The source
     * @param file The output file
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final Text text, final File file, final Charset charset) {
        this(new InputOf(text, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param text The source
     * @param file The output file
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final Text text, final File file,
        final CharSequence charset) {
        this(new InputOf(text, charset), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param text The source
     * @param output The output
     * @since 0.13.3
     */
    public TeeInput(final Text text, final Output output) {
        this(new InputOf(text), output);
    }

    /**
     * Ctor.
     * @param text The source
     * @param output The output
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final Text text, final Output output,
        final Charset charset) {
        this(new InputOf(text, charset), output);
    }

    /**
     * Ctor.
     * @param text The source
     * @param output The output
     * @param charset Charset
     * @since 0.13.3
     */
    public TeeInput(final Text text, final Output output,
        final CharSequence charset) {
        this(new InputOf(text, charset), output);
    }

    /**
     * Ctor.
     * @param bytes The source
     * @param path The output path
     * @since 0.13.3
     */
    public TeeInput(final Bytes bytes, final Path path) {
        this(new InputOf(bytes), new OutputTo(path));
    }

    /**
     * Ctor.
     * @param bytes The source
     * @param file The output file
     * @since 0.13.3
     */
    public TeeInput(final Bytes bytes, final File file) {
        this(new InputOf(bytes), new OutputTo(file));
    }

    /**
     * Ctor.
     * @param bytes The source
     * @param output The output
     * @since 0.13.3
     */
    public TeeInput(final Bytes bytes, final Output output) {
        this(new InputOf(bytes), output);
    }

    /**
     * Ctor.
     * @param input The source
     * @param path The output path
     * @since 0.13.3
     */
    public TeeInput(final Input input, final Path path) {
        this(input, new OutputTo(path));
    }

    /**
     * Ctor.
     * @param input The source
     * @param file The output file
     * @since 0.13.3
     */
    public TeeInput(final Input input, final File file) {
        this(input, new OutputTo(file));
    }

    /**
     * Ctor.
     * @param input The source
     * @param writer The output
     * @since 0.13.3
     */
    public TeeInput(final Input input, final Writer writer) {
        this(input, new OutputTo(writer));
    }

    /**
     * Ctor.
     * @param input The source
     * @param writer The output
     * @param size Writing buffer size
     * @since 0.13.3
     */
    public TeeInput(final Input input, final Writer writer, final int size) {
        this(input, new OutputTo(writer, size));
    }

    /**
     * Ctor.
     * @param input The source
     * @param writer The output
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final Input input, final Writer writer,
        final Charset charset) {
        this(input, new OutputTo(writer, charset));
    }

    /**
     * Ctor.
     * @param input The source
     * @param writer The output
     * @param charset The charset
     * @param size Writing buffer size
     * @since 0.13.3
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public TeeInput(final Input input, final Writer writer,
        final Charset charset, final int size) {
        this(input, new OutputTo(writer, charset, size));
    }

    /**
     * Ctor.
     * @param input The source
     * @param writer The output
     * @param charset The charset
     * @since 0.13.3
     */
    public TeeInput(final Input input, final Writer writer,
        final CharSequence charset) {
        this(input, new OutputTo(writer, charset));
    }

    /**
     * Ctor.
     * @param input The source
     * @param writer The output
     * @param charset The charset
     * @param size Writing buffer size
     * @since 0.13.3
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public TeeInput(final Input input, final Writer writer,
        final CharSequence charset, final int size) {
        this(input, new OutputTo(writer, charset, size));
    }

    /**
     * Ctor.
     * @param input The source
     * @param output The target
     */
    public TeeInput(final Input input, final Output output) {
        this.source = input;
        this.target = output;
    }

    @Override
    public InputStream stream() throws IOException {
        return new TeeInputStream(
            this.source.stream(), this.target.stream()
        );
    }

}
