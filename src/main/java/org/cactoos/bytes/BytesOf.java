/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
package org.cactoos.bytes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Text;
import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;

/**
 * A {@link Bytes} that encapsulates other sources of data.
 *
 * <p>This class provides an implementation of {@link Bytes} that allows
 * converting various sources (e.g., strings, streams, files, readers)
 * into byte arrays. This is useful for working with binary data in a unified way.</p>
 *
 * <p>Example usage:</p>
 *
 * <pre>{@code
 * // Convert a string to bytes
 * byte[] bytes = new BytesOf("example").asBytes();
 *
 * // Convert an InputStream to bytes
 * InputStream input = new ByteArrayInputStream("example".getBytes());
 * byte[] streamBytes = new BytesOf(input).asBytes();
 *
 * // Convert a file to bytes
 * File file = new File("path/to/file.txt");
 * byte[] fileBytes = new BytesOf(file).asBytes();
 *
 * // Convert a Throwable (exception) stack trace to bytes
 * Throwable error = new RuntimeException("Something went wrong");
 * byte[] errorBytes = new BytesOf(error).asBytes();
 * }</pre>
 *
 * <p>Thread safety is not guaranteed.</p>
 *
 * @since 0.12
 */
@SuppressWarnings("PMD.OnlyOneConstructorShouldDoInitialization")
public final class BytesOf implements Bytes {

    /**
     * The bytes.
     */
    private final Bytes origin;

    /**
     * Constructs a {@link BytesOf} instance from an {@link Input}.
     *
     * <p>This constructor reads bytes from a generic {@link Input} source,
     * such as a file, string, or any custom input implementation.
     * The resulting bytes can be retrieved using {@link #asBytes()}.</p>
     *
     * <p>Example usage:</p>
     *
     * <pre>{@code
     * Input input = new InputOf("Hello, world!");
     * byte[] bytes = new BytesOf(input).asBytes();
     * }</pre>
     *
     * @param input The input source to read bytes from.
     */
    public BytesOf(final Input input) {
        this(new InputAsBytes(input));
    }

    /**
     * Constructs a {@link BytesOf} instance from an {@link InputStream}.
     *
     * <p>This constructor reads bytes from an {@link InputStream},
     * such as a file or socket stream.</p>
     *
     * <p>Example usage:</p>
     *
     * <pre>{@code
     * InputStream stream = new ByteArrayInputStream("data".getBytes());
     * byte[] bytes = new BytesOf(stream).asBytes();
     * }</pre>
     *
     * @param input The {@link InputStream} to read bytes from.
     * @since 0.29.2
     */
    public BytesOf(final InputStream input) {
        this(new InputAsBytes(new InputOf(input)));
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link File}.
     *
     * <p>This constructor reads bytes from the content of a file.</p>
     *
     * <p>Example usage:</p>
     *
     * <pre>{@code
     * File file = new File("example.txt");
     * byte[] bytes = new BytesOf(file).asBytes();
     * }</pre>
     *
     * @param file The {@link File} to read bytes from.
     * @since 0.13
     */
    public BytesOf(final File file) {
        this(new InputOf(file));
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Path}.
     *
     * <p>This constructor reads bytes from the content of a file represented
     * by the {@link Path}.</p>
     *
     * <p>Example usage:</p>
     *
     * <pre>{@code
     * Path path = Paths.get("example.txt");
     * byte[] bytes = new BytesOf(path).asBytes();
     * }</pre>
     *
     * @param path The {@link Path} to read bytes from.
     * @since 0.13
     */
    public BytesOf(final Path path) {
        this(new InputOf(path));
    }

    /**
     * Constructs a {@link BytesOf} instance from an {@link Input} with a buffer limit.
     *
     * <p>This constructor reads bytes from an {@link Input} source but
     * limits the reading to a specified maximum buffer size.</p>
     *
     * <p>Example usage:</p>
     *
     * <pre>{@code
     * Input input = new InputOf("This is a long string that exceeds the limit.");
     * byte[] bytes = new BytesOf(input, 10).asBytes();
     * }</pre>
     *
     * @param input The input source to read bytes from.
     * @param max The maximum buffer size for reading bytes.
     */
    public BytesOf(final Input input, final int max) {
        this(new InputAsBytes(input, max));
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Reader}.
     *
     * <p>This constructor reads bytes from a {@link Reader},
     * such as a {@link java.io.StringReader} or a {@link java.io.FileReader}.
     * The resulting bytes can be retrieved using {@link #asBytes()}.</p>
     *
     * <p>Example usage:</p>
     *
     * <pre>{@code
     * Reader reader = new StringReader("Hello, world!");
     * byte[] bytes = new BytesOf(reader).asBytes();
     * }</pre>
     *
     * @param rdr The {@link Reader} to read bytes from.
     */
    public BytesOf(final Reader rdr) {
        this(new ReaderAsBytes(rdr));
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Reader} with a specified {@link Charset}.
     *
     * <p>This constructor reads bytes from a {@link Reader} and uses the given
     * {@link Charset} for encoding. This is useful for handling different
     * character encodings like UTF-8 or ISO-8859-1.</p>
     *
     * <p>Example usage:</p>
     *
     * <pre>{@code
     * Reader reader = new StringReader("Привет, мир!");
     * byte[] bytes = new BytesOf(reader, StandardCharsets.UTF_8).asBytes();
     * }</pre>
     *
     * @param rdr The {@link Reader} to read bytes from.
     * @param charset The {@link Charset} used to encode the characters.
     */
    public BytesOf(final Reader rdr, final Charset charset) {
        this(new ReaderAsBytes(rdr, charset));
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Reader} with a {@link CharSequence}
     * representing the charset.
     *
     * <p>This constructor reads bytes from a {@link Reader} and uses the charset
     * specified as a {@link CharSequence} for encoding.</p>
     *
     * <p>Example usage:</p>
     *
     * <pre>{@code
     * Reader reader = new StringReader("Hola, mundo!");
     * byte[] bytes = new BytesOf(reader, "UTF-8").asBytes();
     * }</pre>
     *
     * @param rdr The {@link Reader} to read bytes from.
     * @param charset The charset as a {@link CharSequence}.
     */
    public BytesOf(final Reader rdr, final CharSequence charset) {
        this(new ReaderAsBytes(rdr, charset));
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Reader} with a {@link Charset}
     * and a specified maximum buffer size.
     *
     * <p>This constructor reads bytes from a {@link Reader} and uses the given
     * {@link Charset} for encoding, but limits the reading to a specified buffer size.</p>
     *
     * <p>Example usage:</p>
     *
     * <pre>{@code
     * Reader reader = new StringReader("This is a long string.");
     * byte[] bytes = new BytesOf(reader, StandardCharsets.UTF_8, 10).asBytes();
     * }</pre>
     *
     * @param rdr The {@link Reader} to read bytes from.
     * @param charset The {@link Charset} used to encode the characters.
     * @param max The maximum buffer size for reading bytes.
     */
    public BytesOf(final Reader rdr, final Charset charset, final int max) {
        this(new ReaderAsBytes(rdr, charset, max));
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Reader} with a specified maximum
     * buffer size.
     *
     * <p>This constructor reads bytes from a {@link Reader}, such as a
     * {@link java.io.StringReader},
     * but limits the reading to the specified buffer size.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * Reader reader = new StringReader("Sample text data");
     * byte[] bytes = new BytesOf(reader, 1024).asBytes();
     * }</pre>
     *
     * @param rdr The {@link Reader} to read bytes from.
     * @param max The maximum buffer size for reading bytes.
     * @since 0.13.3
     */
    public BytesOf(final Reader rdr, final int max) {
        this(new ReaderAsBytes(rdr, max));
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Reader} with a {@link CharSequence}
     * representing the charset and a specified maximum buffer size.
     *
     * <p>This constructor reads bytes from a {@link Reader} using the provided character encoding
     * and limits the reading to the specified buffer size.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * Reader reader = new StringReader("Пример данных");
     * byte[] bytes = new BytesOf(reader, "UTF-8", 512).asBytes();
     * }</pre>
     *
     * @param rdr The {@link Reader} to read bytes from.
     * @param charset The charset as a {@link CharSequence}.
     * @param max The maximum buffer size for reading bytes.
     */
    public BytesOf(final Reader rdr, final CharSequence charset, final int max) {
        this(new ReaderAsBytes(rdr, charset, max));
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link CharSequence}.
     *
     * <p>This constructor converts a {@link CharSequence} (e.g., {@link String}) into bytes using
     * the default UTF-8 encoding.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * CharSequence sequence = "Hello, world!";
     * byte[] bytes = new BytesOf(sequence).asBytes();
     * }</pre>
     *
     * @param input The {@link CharSequence} source to convert into bytes.
     */
    public BytesOf(final CharSequence input) {
        this(input, StandardCharsets.UTF_8);
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link CharSequence} with a
     * specified {@link Charset}.
     *
     * <p>This constructor converts a {@link CharSequence} (e.g., {@link String}) into bytes using
     * the provided character encoding.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * CharSequence sequence = "Привет, мир!";
     * byte[] bytes = new BytesOf(sequence, StandardCharsets.UTF_8).asBytes();
     * }</pre>
     *
     * @param input The {@link CharSequence} source to convert into bytes.
     * @param charset The {@link Charset} used to encode the characters into bytes.
     */
    public BytesOf(final CharSequence input, final Charset charset) {
        this(() -> input.toString().getBytes(charset));
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link CharSequence} with
     * a specified {@link CharSequence} charset.
     *
     * <p>This constructor converts a {@link CharSequence} (e.g., {@link String}) into bytes using
     * the charset represented as a {@link CharSequence}.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * CharSequence sequence = "Hola, mundo!";
     * byte[] bytes = new BytesOf(sequence, "ISO-8859-1").asBytes();
     * }</pre>
     *
     * @param input The {@link CharSequence} source to convert into bytes.
     * @param charset The {@link CharSequence} representation of the charset used for encoding.
     */
    public BytesOf(final CharSequence input, final CharSequence charset) {
        this(() -> input.toString().getBytes(charset.toString()));
    }

    /**
     * Constructs a {@link BytesOf} instance from a sequence of characters.
     *
     * <p>This constructor converts a sequence of characters into bytes using
     * the default UTF-8 encoding.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * byte[] bytes = new BytesOf('a', 'b', 'c').asBytes();
     * }</pre>
     *
     * @param chars The sequence of characters to be converted into bytes.
     */
    public BytesOf(final char... chars) {
        this(chars, StandardCharsets.UTF_8);
    }

    /**
     * Constructs a {@link BytesOf} instance from a sequence of characters
     * and a specified {@link Charset}.
     *
     * <p>This constructor converts a sequence of characters into bytes using
     * the provided character encoding.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * byte[] bytes = new BytesOf(new char[]{'H', 'e', 'l', 'l', 'o'},
     * StandardCharsets.UTF_16).asBytes();
     * }</pre>
     *
     * @param chars The sequence of characters to be converted into bytes.
     * @param charset The {@link Charset} used to encode the characters into bytes.
     */
    public BytesOf(final char[] chars, final Charset charset) {
        this(new String(chars), charset);
    }

    /**
     * Constructs a {@link BytesOf} instance from a sequence of characters and
     * a specified charset represented as a {@link CharSequence}.
     *
     * <p>This constructor converts a sequence of characters into bytes using the charset
     * provided as a {@link CharSequence}.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * byte[] bytes = new BytesOf(new char[]{'П', 'р', 'и', 'в', 'е', 'т'}, "UTF-8").asBytes();
     * }</pre>
     *
     * @param chars The sequence of characters to be converted into bytes.
     * @param charset The charset represented as a {@link CharSequence}.
     */
    public BytesOf(final char[] chars, final CharSequence charset) {
        this(new String(chars), charset);
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Text} object.
     *
     * <p>This constructor converts the text into bytes using the default UTF-8 encoding.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * Text text = new TextOf("Hello, world!");
     * byte[] bytes = new BytesOf(text).asBytes();
     * }</pre>
     *
     * @param text The {@link Text} source to be converted into bytes.
     */
    public BytesOf(final Text text) {
        this(text, StandardCharsets.UTF_8);
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Text} object and a specified {@link Charset}.
     *
     * <p>This constructor converts the text into bytes using the provided character encoding.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * Text text = new TextOf("Привет, мир!");
     * byte[] bytes = new BytesOf(text, StandardCharsets.UTF_8).asBytes();
     * }</pre>
     *
     * @param text The {@link Text} source to be converted into bytes.
     * @param charset The {@link Charset} used to encode the text into bytes.
     */
    public BytesOf(final Text text, final Charset charset) {
        this(() -> text.asString().getBytes(charset));
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Text} object and
     * a charset represented as a {@link CharSequence}.
     *
     * <p>This constructor converts the text into bytes using the charset provided as
     * a {@link CharSequence}.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * Text text = new TextOf("Hola, mundo!");
     * byte[] bytes = new BytesOf(text, "ISO-8859-1").asBytes();
     * }</pre>
     *
     * @param text The {@link Text} source to be converted into bytes.
     * @param charset The charset represented as a {@link CharSequence}.
     */
    public BytesOf(final Text text, final CharSequence charset) {
        this(() -> text.asString().getBytes(charset.toString()));
    }
    /**
     * Constructs a {@link BytesOf} instance from a {@link Throwable} (exception or error).
     *
     * <p>This constructor serializes the stack trace of the exception using
     * the default UTF-8 encoding.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * try {
     *     throw new RuntimeException("Example exception");
     * } catch (Exception ex) {
     *     byte[] bytes = new BytesOf(ex).asBytes();
     *     System.out.println(new String(bytes, StandardCharsets.UTF_8));
     * }
     * }</pre>
     *
     * @param error The exception or error to serialize into bytes.
     */
    public BytesOf(final Throwable error) {
        this(error, StandardCharsets.UTF_8);
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Throwable} (exception or error) and
     * a specified {@link Charset}.
     *
     * <p>This constructor serializes the stack trace of the exception using
     * the provided character encoding.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * try {
     *     throw new IllegalStateException("Another exception");
     * } catch (Exception ex) {
     *     byte[] bytes = new BytesOf(ex, StandardCharsets.ISO_8859_1).asBytes();
     *     System.out.println(new String(bytes, StandardCharsets.ISO_8859_1));
     * }
     * }</pre>
     *
     * @param error The exception or error to serialize into bytes.
     * @param charset The {@link Charset} used to encode the stack trace into bytes.
     */
    public BytesOf(final Throwable error, final Charset charset) {
        this(error, charset.name());
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Throwable} (exception or error) and
     * a charset represented as a {@link CharSequence}.
     *
     * <p>This constructor serializes the stack trace of the exception using the charset
     * provided as a {@link CharSequence}.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * try {
     *     throw new NullPointerException("Null pointer exception");
     * } catch (Exception ex) {
     *     byte[] bytes = new BytesOf(ex, "UTF-16").asBytes();
     *     System.out.println(new String(bytes, "UTF-16"));
     * }
     * }</pre>
     *
     * @param error The exception or error to serialize into bytes.
     * @param charset The charset represented as a {@link CharSequence}.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public BytesOf(final Throwable error, final CharSequence charset) {
        this(
            () -> {
                try (
                    ByteArrayOutputStream baos =
                        new ByteArrayOutputStream()
                ) {
                    error.printStackTrace(
                        new PrintStream(baos, true, charset.toString())
                    );
                    return baos.toByteArray();
                }
            }
        );
    }

    /**
     * Constructs a {@link BytesOf} instance from a series of {@link StackTraceElement}.
     *
     * <p>This constructor serializes the stack trace elements into bytes using
     * the default UTF-8 encoding.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * StackTraceElement[] stack = Thread.currentThread().getStackTrace();
     * byte[] bytes = new BytesOf(stack).asBytes();
     * System.out.println(new String(bytes, StandardCharsets.UTF_8));
     * }</pre>
     *
     * @param strace The array of stack trace elements to serialize.
     * @since 0.29
     */
    public BytesOf(final StackTraceElement... strace) {
        this(strace, StandardCharsets.UTF_8);
    }

    /**
     * Constructs a {@link BytesOf} instance from a stack trace represented
     * as an array of {@link StackTraceElement}.
     *
     * <p>This constructor serializes the stack trace elements into bytes using
     * the provided {@link Charset}.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * StackTraceElement[] stack = Thread.currentThread().getStackTrace();
     * byte[] bytes = new BytesOf(stack, StandardCharsets.UTF_8).asBytes();
     * System.out.println(new String(bytes, StandardCharsets.UTF_8));
     * }</pre>
     *
     * @param strace The stack trace elements to serialize.
     * @param charset The {@link Charset} used to encode the stack trace into bytes.
     * @since 0.29
     */
    public BytesOf(final StackTraceElement[] strace, final Charset charset) {
        this(strace, charset.name());
    }

    /**
     * Constructs a {@link BytesOf} instance from a stack trace represented as
     * an array of {@link StackTraceElement}.
     *
     * <p>This constructor serializes the stack trace elements into bytes using
     * the provided charset represented as a {@link CharSequence}.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * StackTraceElement[] stack = Thread.currentThread().getStackTrace();
     * byte[] bytes = new BytesOf(stack, "UTF-16").asBytes();
     * System.out.println(new String(bytes, "UTF-16"));
     * }</pre>
     *
     * @param strace The stack trace elements to serialize.
     * @param charset The charset represented as a {@link CharSequence}.
     * @since 0.29
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public BytesOf(final StackTraceElement[] strace,
        final CharSequence charset) {
        this(
            () -> {
                try (
                    ByteArrayOutputStream baos =
                        new ByteArrayOutputStream();
                    PrintStream stream = new PrintStream(
                        baos, true, charset.toString()
                    )
                ) {
                    for (final StackTraceElement element : strace) {
                        stream.append(element.toString());
                        stream.append("\n");
                    }
                    return baos.toByteArray();
                }
            }
        );
    }

    /**
     * Constructs a {@link BytesOf} instance from a sequence of bytes.
     *
     * <p>This constructor encapsulates the provided byte array.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * byte[] data = new BytesOf((byte) 1, (byte) 2, (byte) 3).asBytes();
     * System.out.println(Arrays.toString(data));
     * }</pre>
     *
     * @param bytes The bytes to encapsulate.
     */
    public BytesOf(final byte... bytes) {
        this(() -> bytes);
    }

    /**
     * Constructs a {@link BytesOf} instance from an {@link Iterator} of bytes.
     *
     * <p>This constructor collects the bytes from the iterator and encapsulates
     * them as an array.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * List<Byte> byteList = Arrays.asList((byte) 10, (byte) 20, (byte) 30);
     * byte[] data = new BytesOf(byteList.iterator()).asBytes();
     * System.out.println(Arrays.toString(data));
     * }</pre>
     *
     * @param iterator The iterator of bytes to encapsulate.
     */
    public BytesOf(final Iterator<Byte> iterator) {
        this(new IterableOf<>(iterator));
    }

    /**
     * Constructs a {@link BytesOf} instance from an {@link Iterable} of bytes.
     *
     * <p>This constructor collects the bytes from the iterable and encapsulates
     * them as an array.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * Iterable<Byte> bytes = Arrays.asList((byte) 100, (byte) 101);
     * byte[] data = new BytesOf(bytes).asBytes();
     * System.out.println(Arrays.toString(data));
     * }</pre>
     *
     * @param bytes The iterable collection of bytes to encapsulate.
     */
    public BytesOf(final Iterable<Byte> bytes) {
        this(() -> {
            final List<Byte> concrete = new ListOf<>(bytes);
            final ByteBuffer buf = ByteBuffer.allocate(
                concrete.size()
            );
            concrete.forEach(buf::put);
            return buf.array();
        });
    }

    /**
     * Constructs a {@link BytesOf} instance from a {@link Collection} of bytes.
     *
     * <p>This constructor collects the bytes from the collection and encapsulates
     * them as an array.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * Collection<Byte> bytes = List.of((byte) 65, (byte) 66);
     * byte[] data = new BytesOf(bytes).asBytes();
     * System.out.println(new String(data, StandardCharsets.UTF_8));
     * }</pre>
     *
     * @param bytes The collection of bytes to encapsulate.
     */
    public BytesOf(final Collection<Byte> bytes) {
        this(() -> {
            final ByteBuffer buf = ByteBuffer.allocate(
                bytes.size()
            );
            bytes.forEach(buf::put);
            return buf.array();
        });
    }

    /**
     * Constructs a {@link BytesOf} instance from an encapsulated {@link Bytes} object.
     *
     * <p>This constructor is private and is intended for internal usage only, allowing
     * composition of {@link Bytes} implementations.</p>
     *
     * <p>Example usage (indirectly via other constructors):</p>
     * <pre>{@code
     * byte[] data = new BytesOf(new InputOf("example")).asBytes();
     * System.out.println(Arrays.toString(data));
     * }</pre>
     *
     * @param bytes The {@link Bytes} instance to encapsulate.
     */
    private BytesOf(final Bytes bytes) {
        this.origin = bytes;
    }

    @Override
    public byte[] asBytes() throws Exception {
        return this.origin.asBytes();
    }
}
