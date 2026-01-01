/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.bytes.BytesOf;
import org.cactoos.scalar.IoChecked;
import org.cactoos.scalar.Unchecked;

/**
 * An {@link Input} that encapsulates other sources of data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.11.8
 */
public final class InputOf implements Input {

    /**
     * Input.
     */
    private final Input origin;

    /**
     * Ctor.
     *
     * @param file The file
     */
    @SuppressWarnings("PMD.AvoidFileStream")
    public InputOf(final File file) {
        this(
            () -> new FileInputStream(
                new Unchecked<>(() -> file).value()
            )
        );
    }

    /**
     * Ctor.
     *
     * @param path The path
     */
    @SuppressWarnings("PMD.AvoidFileStream")
    public InputOf(final Path path) {
        this(() -> new FileInputStream(path.toFile()));
    }

    /**
     * Ctor.
     *
     * @param uri The URI
     */
    public InputOf(final URI uri) {
        this(uri::toURL);
    }

    /**
     * Ctor.
     *
     * @param url The URL
     */
    public InputOf(final URL url) {
        this(() -> url);
    }

    /**
     * Ctor.
     *
     * @param scalar The url
     */
    public InputOf(final Scalar<URL> scalar) {
        this(() -> new IoChecked<>(scalar).value().openStream());
    }

    /**
     * Ctor.
     *
     * @param rdr Reader
     */
    public InputOf(final Reader rdr) {
        this(new BytesOf(rdr));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     */
    public InputOf(final Reader rdr, final Charset charset) {
        this(new BytesOf(rdr, charset));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     */
    public InputOf(final Reader rdr, final CharSequence charset) {
        this(new BytesOf(rdr, charset));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param max Buffer size
     * @since 0.13.3
     */
    public InputOf(final Reader rdr, final int max) {
        this(new BytesOf(rdr, max));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     * @param max Buffer size
     */
    public InputOf(final Reader rdr, final Charset charset, final int max) {
        this(new BytesOf(rdr, charset, max));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     * @param max Buffer size
     */
    public InputOf(final Reader rdr, final CharSequence charset,
        final int max) {
        this(new BytesOf(rdr, charset, max));
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     */
    public InputOf(final char... chars) {
        this(new BytesOf(chars));
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     * @param charset The charset
     */
    public InputOf(final char[] chars, final Charset charset) {
        this(new BytesOf(chars, charset));
    }

    /**
     * Ctor.
     *
     * @param chars The chars
     * @param charset The charset
     */
    public InputOf(final char[] chars, final CharSequence charset) {
        this(new BytesOf(chars, charset));
    }

    /**
     * Ctor.
     * @param source The string
     */
    public InputOf(final CharSequence source) {
        this(new BytesOf(source));
    }

    /**
     * Ctor.
     * @param source The string
     * @param charset The charset
     */
    public InputOf(final CharSequence source, final Charset charset) {
        this(new BytesOf(source, charset));
    }

    /**
     * Ctor.
     * @param source The string
     * @param charset The charset
     */
    public InputOf(final CharSequence source, final CharSequence charset) {
        this(new BytesOf(source, charset));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public InputOf(final Text text) {
        this(new BytesOf(text));
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset The charset
     */
    public InputOf(final Text text, final Charset charset) {
        this(new BytesOf(text, charset));
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset The charset
     */
    public InputOf(final Text text, final CharSequence charset) {
        this(new BytesOf(text, charset));
    }

    /**
     * Ctor.
     * @param error The exception to serialize
     */
    public InputOf(final Throwable error) {
        this(new BytesOf(error));
    }

    /**
     * Ctor.
     * @param error The exception to serialize
     * @param charset Charset
     */
    public InputOf(final Throwable error, final Charset charset) {
        this(new BytesOf(error, charset));
    }

    /**
     * Ctor.
     * @param error The exception to serialize
     * @param charset Charset
     */
    public InputOf(final Throwable error, final CharSequence charset) {
        this(new BytesOf(error, charset));
    }

    /**
     * Ctor.
     * @param bytes The bytes
     */
    public InputOf(final byte[] bytes) {
        this(new BytesOf(bytes));
    }

    /**
     * Ctor.
     * @param src The bytes
     */
    public InputOf(final Bytes src) {
        this(
            () -> new IoChecked<InputStream>(
                () -> new ByteArrayInputStream(src.asBytes())
            ).value()
        );
    }

    /**
     * Ctor.
     * @param stream The stream
     */
    public InputOf(final InputStream stream) {
        this(() -> stream);
    }

    /**
     * Ctor.
     *
     * @param input The input
     */
    private InputOf(final Input input) {
        this.origin = input;
    }

    @Override
    public InputStream stream() throws Exception {
        return this.origin.stream();
    }

}
