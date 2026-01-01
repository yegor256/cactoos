/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.Bytes;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * An {@link InputStream} that encapsulates other sources of data.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.13
 */
public final class InputStreamOf extends InputStream {

    /**
     * The source.
     */
    private final Unchecked<InputStream> source;

    /**
     * Ctor.
     * @param path The path
     */
    public InputStreamOf(final Path path) {
        this(new InputOf(path));
    }

    /**
     * Ctor.
     * @param file The file
     */
    public InputStreamOf(final File file) {
        this(new InputOf(file));
    }

    /**
     * Ctor.
     * @param url The URL
     */
    public InputStreamOf(final URL url) {
        this(new InputOf(url));
    }

    /**
     * Ctor.
     * @param uri The URI
     */
    public InputStreamOf(final URI uri) {
        this(new InputOf(uri));
    }

    /**
     * Ctor.
     * @param bytes The text
     */
    public InputStreamOf(final Bytes bytes) {
        this(new InputOf(bytes));
    }

    /**
     * Ctor.
     * @param bytes The text
     */
    public InputStreamOf(final byte[] bytes) {
        this(new InputOf(bytes));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public InputStreamOf(final Text text) {
        this(text, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset Charset
     */
    public InputStreamOf(final Text text, final Charset charset) {
        this(new InputOf(text, charset));
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset Charset
     */
    public InputStreamOf(final Text text, final CharSequence charset) {
        this(new InputOf(text, charset));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public InputStreamOf(final CharSequence text) {
        this(new InputOf(text));
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset Charset
     */
    public InputStreamOf(final CharSequence text, final Charset charset) {
        this(new InputOf(text, charset));
    }

    /**
     * Ctor.
     * @param text The text
     * @param charset Charset
     */
    public InputStreamOf(final CharSequence text, final CharSequence charset) {
        this(new InputOf(text, charset));
    }

    /**
     * Ctor.
     * @param rdr The reader
     * @since 0.13.2
     */
    public InputStreamOf(final Reader rdr) {
        this(rdr, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     * @since 0.13.2
     */
    public InputStreamOf(final Reader rdr, final Charset charset) {
        this(new InputOf(rdr, charset));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     * @since 0.13.2
     */
    public InputStreamOf(final Reader rdr, final CharSequence charset) {
        this(new InputOf(rdr, charset));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param cset Charset
     * @param max Buffer size
     * @since 0.13.2
     */
    public InputStreamOf(final Reader rdr, final Charset cset, final int max) {
        this(new InputOf(rdr, cset, max));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param max Buffer size
     * @since 0.13.2
     */
    public InputStreamOf(final Reader rdr, final int max) {
        this(new InputOf(rdr, StandardCharsets.UTF_8, max));
    }

    /**
     * Ctor.
     * @param rdr Reader
     * @param charset Charset
     * @param max Buffer size
     * @since 0.13.2
     */
    public InputStreamOf(final Reader rdr, final CharSequence charset,
        final int max) {
        this(new InputOf(rdr, charset, max));
    }

    /**
     * Ctor.
     * @param input The input
     */
    public InputStreamOf(final Input input) {
        this((Scalar<InputStream>) input::stream);
    }

    /**
     * Ctor.
     * @param src Source
     */
    private InputStreamOf(final Scalar<InputStream> src) {
        super();
        this.source = new Unchecked<>(new Sticky<>(src));
    }

    @Override
    public int read() throws IOException {
        return this.source.value().read();
    }

    @Override
    public int read(final byte[] buffer) throws IOException {
        return this.source.value().read(buffer);
    }

    @Override
    public int read(final byte[] buffer, final int offset,
        final int length) throws IOException {
        return this.source.value().read(buffer, offset, length);
    }

    @Override
    public void close() throws IOException {
        this.source.value().close();
    }

    @Override
    public long skip(final long num) throws IOException {
        return this.source.value().skip(num);
    }

    @Override
    public int available() throws IOException {
        return this.source.value().available();
    }

    @Override
    public void mark(final int limit) {
        this.source.value().mark(limit);
    }

    @Override
    public void reset() throws IOException {
        this.source.value().reset();
    }

    @Override
    public boolean markSupported() {
        return this.source.value().markSupported();
    }

}
