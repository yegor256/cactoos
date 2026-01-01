/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

import java.io.OutputStream;
import org.cactoos.io.OutputTo;
import org.cactoos.io.TeeInput;

/**
 * Output.
 *
 * <p>Here is for example how {@link Output} can be used
 * together with {@link Input} in order to modify the content
 * of a text file:</p>
 *
 * <pre> new LengthOf(
 *   new TeeInput(
 *     new InputOf(new TextOf("Hello, world!")),
 *     new OutputTo(new File("/tmp/names.txt"))
 *   )
 * ).asValue();</pre>
 *
 * <p>Here {@link OutputTo} implements {@link Output} and behaves like
 * one, providing write-only access to the encapsulated
 * {@link java.io.File}. The {@link TeeInput} copies the content of the
 * input to the output. The {@link org.cactoos.scalar.LengthOf}
 * calculates the size of the copied data.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @see OutputTo
 * @since 0.1
 */
@FunctionalInterface
public interface Output {

    /**
     * Get write access to it.
     * @return OutputStream to write to
     * @throws Exception If something goes wrong
     */
    OutputStream stream() throws Exception;

}
