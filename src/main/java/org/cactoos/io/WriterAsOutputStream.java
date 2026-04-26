/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import org.cactoos.Scalar;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Writer as {@link OutputStream}.
 *
 * <p>This class is for internal use only. Use {@link OutputStreamTo}
 * instead.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.13
 */
final class WriterAsOutputStream extends OutputStream {

    /**
     * Incoming data, deferred.
     */
    private final Unchecked<ByteBuffer> input;

    /**
     * Output ready to be flushed, deferred.
     */
    private final Unchecked<CharBuffer> output;

    /**
     * The writer.
     */
    private final Writer writer;

    /**
     * The charset decoder, deferred.
     */
    private final Unchecked<CharsetDecoder> decoder;

    /**
     * Ctor.
     * @param wtr Writer
     */
    WriterAsOutputStream(final Writer wtr) {
        this(wtr, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     * @param wtr Writer
     * @param charset Charset
     */
    WriterAsOutputStream(final Writer wtr, final CharSequence charset) {
        this(wtr, charset, 16 << 10);
    }

    /**
     * Ctor.
     * @param wtr Writer
     * @param charset Charset
     */
    WriterAsOutputStream(final Writer wtr, final Charset charset) {
        this(wtr, charset, 16 << 10);
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param charset Charset
     * @param size Buffer size
     */
    WriterAsOutputStream(final Writer wtr, final CharSequence charset,
        final int size) {
        this(
            wtr,
            (Scalar<CharsetDecoder>) () -> Charset.forName(charset.toString())
                .newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT),
            size
        );
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param size Buffer size
     * @since 0.13.3
     */
    WriterAsOutputStream(final Writer wtr, final int size) {
        this(wtr, StandardCharsets.UTF_8, size);
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param charset Charset
     * @param size Buffer size
     */
    WriterAsOutputStream(final Writer wtr, final Charset charset,
        final int size) {
        this(
            wtr,
            (Scalar<CharsetDecoder>) () -> charset.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT),
            size
        );
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param ddr Charset decoder
     * @param size Buffer size
     */
    WriterAsOutputStream(final Writer wtr, final CharsetDecoder ddr,
        final int size) {
        this(wtr, (Scalar<CharsetDecoder>) () -> ddr, size);
    }

    /**
     * Ctor.
     * @param wtr Reader
     * @param ddr Charset decoder, deferred
     * @param size Buffer size
     */
    private WriterAsOutputStream(final Writer wtr,
        final Scalar<CharsetDecoder> ddr, final int size) {
        super();
        this.writer = wtr;
        this.decoder = new Unchecked<>(new Sticky<>(ddr));
        this.input = new Unchecked<>(new Sticky<>(() -> ByteBuffer.allocate(size)));
        this.output = new Unchecked<>(new Sticky<>(() -> CharBuffer.allocate(size)));
    }

    @Override
    public void write(final int data) throws IOException {
        this.write(new byte[] {(byte) data}, 0, 1);
    }

    @Override
    public void write(final byte[] buffer) throws IOException {
        this.write(buffer, 0, buffer.length);
    }

    @Override
    public void write(final byte[] buffer, final int offset,
        final int length) throws IOException {
        int left = length;
        int start = offset;
        while (left > 0) {
            final int taken = this.next(buffer, start, left);
            start += taken;
            left -= taken;
        }
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }

    /**
     * Write a portion from the buffer.
     * @param buffer The buffer
     * @param offset Offset in the buffer
     * @param length Maximum possible amount to take
     * @return How much was taken
     * @throws IOException If fails
     */
    private int next(final byte[] buffer, final int offset,
        final int length) throws IOException {
        final ByteBuffer ibuf = this.input.value();
        final CharBuffer obuf = this.output.value();
        final int max = Math.min(length, ibuf.remaining());
        ibuf.put(buffer, offset, max);
        ibuf.flip();
        while (true) {
            final CoderResult result = this.decoder.value().decode(
                ibuf, obuf, false
            );
            if (result.isError()) {
                result.throwException();
            }
            this.writer.write(
                obuf.array(), 0, obuf.position()
            );
            this.writer.flush();
            obuf.rewind();
            if (result.isUnderflow()) {
                break;
            }
        }
        ibuf.compact();
        return max;
    }
}
