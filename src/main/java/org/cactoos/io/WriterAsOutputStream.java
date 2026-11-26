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
 * <p>There is no thread-safety guarantee.</p>
 *
 * @since 0.13
 */
final class WriterAsOutputStream extends OutputStream {

    /**
     * Incoming data.
     */
    private ByteBuffer input;

    /**
     * Output ready to be flushed.
     */
    private CharBuffer output;

    /**
     * The writer.
     */
    private final Writer writer;

    /**
     * The charset decoder, deferred.
     */
    private final Unchecked<CharsetDecoder> decoder;

    /**
     * Buffer size, used when the buffers are first allocated.
     */
    private final int size;

    /**
     * Ctor.
     *
     * @param wtr Writer
     */
    WriterAsOutputStream(final Writer wtr) {
        this(wtr, StandardCharsets.UTF_8);
    }

    /**
     * Ctor.
     *
     * @param wtr Writer
     * @param charset Charset
     */
    WriterAsOutputStream(final Writer wtr, final CharSequence charset) {
        this(wtr, charset, 16 << 10);
    }

    /**
     * Ctor.
     *
     * @param wtr Writer
     * @param charset Charset
     */
    WriterAsOutputStream(final Writer wtr, final Charset charset) {
        this(wtr, charset, 16 << 10);
    }

    /**
     * Ctor.
     *
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
     *
     * @param wtr Reader
     * @param size Buffer size
     * @since 0.13.3
     */
    WriterAsOutputStream(final Writer wtr, final int size) {
        this(wtr, StandardCharsets.UTF_8, size);
    }

    /**
     * Ctor.
     *
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
     *
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
     *
     * @param wtr Reader
     * @param ddr Charset decoder, deferred
     * @param size Buffer size
     */
    private WriterAsOutputStream(final Writer wtr,
        final Scalar<CharsetDecoder> ddr, final int size) {
        super();
        this.writer = wtr;
        this.decoder = new Unchecked<>(new Sticky<>(ddr));
        this.size = size;
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

    private int next(final byte[] buffer, final int offset,
        final int length) throws IOException {
        final ByteBuffer ibuf = this.grown();
        final int max = Math.min(length, ibuf.remaining());
        ibuf.put(buffer, offset, max);
        ibuf.flip();
        this.decode(ibuf);
        ibuf.compact();
        return max;
    }

    private void decode(final ByteBuffer ibuf) throws IOException {
        if (this.output == null) {
            this.output = CharBuffer.allocate(this.size);
        }
        while (true) {
            final CoderResult result = this.decoder.value().decode(
                ibuf, this.output, false
            );
            if (result.isError()) {
                result.throwException();
            }
            final int written = this.output.position();
            this.writer.write(this.output.array(), 0, written);
            this.writer.flush();
            this.output.rewind();
            if (result.isUnderflow()) {
                break;
            }
            if (written == 0) {
                this.output = CharBuffer.allocate(
                    Math.max(1, this.output.capacity() * 2)
                );
            }
        }
    }

    private ByteBuffer grown() {
        if (this.input == null) {
            this.input = ByteBuffer.allocate(this.size);
        }
        if (this.input.remaining() == 0) {
            final ByteBuffer grown = ByteBuffer.allocate(
                Math.max(1, this.input.capacity() * 2)
            );
            this.input.flip();
            grown.put(this.input);
            this.input = grown;
        }
        return this.input;
    }
}
