/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;
import org.cactoos.func.TriFuncSplitPreserve;
import org.cactoos.iterable.IterableEnvelope;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.iterator.IteratorOf;

/**
 * Splits text into tokens, preserving ALL tokens including empty ones.
 *
 * <p>This class provides functionality equivalent to Apache Commons Lang's
 * {@code StringUtils.splitPreserveAllTokens()}. Unlike Java's standard
 * {@link String#split(String)}, this class preserves:</p>
 * <ul>
 *   <li>Leading empty tokens (when text starts with delimiter)</li>
 *   <li>Trailing empty tokens (when text ends with delimiter)</li>
 *   <li>Adjacent empty tokens (when delimiters appear consecutively)</li>
 * </ul>
 *
 * <h3>Key Guarantee</h3>
 * <p>For a string with N occurrences of the delimiter, this class always
 * returns exactly N+1 tokens (some may be empty strings).</p>
 *
 * <h3>Comparison with String.split()</h3>
 * <table border="1">
 *   <caption>Behavior comparison</caption>
 *   <tr><th>Input</th><th>String.split(",")</th><th>SplitPreserveAllTokens</th></tr>
 *   <tr><td>"a,b,"</td><td>["a", "b"]</td><td>["a", "b", ""]</td></tr>
 *   <tr><td>",,"</td><td>[]</td><td>["", "", ""]</td></tr>
 *   <tr><td>","</td><td>[]</td><td>["", ""]</td></tr>
 * </table>
 *
 * <h3>Example Usage</h3>
 * <pre>{@code
 * // Basic splitting
 * Iterable<Text> tokens = new SplitPreserveAllTokens("a,b,c", ",");
 * // Result: ["a", "b", "c"]
 *
 * // Preserves trailing empty token
 * Iterable<Text> tokens = new SplitPreserveAllTokens("a,b,", ",");
 * // Result: ["a", "b", ""]
 *
 * // Preserves multiple empty tokens
 * Iterable<Text> tokens = new SplitPreserveAllTokens("a,,b", ",");
 * // Result: ["a", "", "b"]
 *
 * // Only delimiters - produces all empty tokens
 * Iterable<Text> tokens = new SplitPreserveAllTokens(",,", ",");
 * // Result: ["", "", ""]
 *
 * // Default delimiter is space
 * Iterable<Text> tokens = new SplitPreserveAllTokens(" hello  world ");
 * // Result: ["", "hello", "", "world", ""]
 *
 * // With limit on number of tokens
 * Iterable<Text> tokens = new SplitPreserveAllTokens("a,b,c,d", ",", 2);
 * // Result: ["a", "b"]
 *
 * // Parsing CSV with empty fields
 * Iterable<Text> fields = new SplitPreserveAllTokens("John,,Smith,,", ",");
 * // Result: ["John", "", "Smith", "", ""] - all 5 fields preserved!
 * }</pre>
 *
 * <h3>Use Cases</h3>
 * <ul>
 *   <li>CSV/TSV parsing where empty fields are meaningful</li>
 *   <li>Log file parsing where field position matters</li>
 *   <li>Protocol parsing where field count must be exact</li>
 *   <li>Data migration where empty values must be preserved</li>
 * </ul>
 *
 * <p><strong>Note:</strong> The delimiter is matched as a literal string,
 * not as a regular expression.</p>
 *
 * @see Split
 * @see TriFuncSplitPreserve
 * @since 0.0
 */
public final class SplitPreserveAllTokens extends IterableEnvelope<Text> {
    /**
     * Ctor.
     *
     * @param text The text
     */
    public SplitPreserveAllTokens(final CharSequence text) {
        this(new TextOf(text), new TextOf(" "));
    }

    /**
     * Ctor.
     *
     * @param text The text
     */
    public SplitPreserveAllTokens(final Text text) {
        this(text, new TextOf(" "));
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(final CharSequence text, final int lmt) {
        this(new TextOf(text), new TextOf(" "), lmt);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(final Text text, final int lmt) {
        this(text, new TextOf(" "), lmt);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     */
    public SplitPreserveAllTokens(final CharSequence text, final CharSequence rgx) {
        this(new TextOf(text), new TextOf(rgx));
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(final CharSequence text, final CharSequence rgx, final int lmt) {
        this(new TextOf(text), new TextOf(rgx), lmt);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     */
    public SplitPreserveAllTokens(final CharSequence text, final Text rgx) {
        this(new TextOf(text), rgx);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(final CharSequence text, final Text rgx, final int lmt) {
        this(new TextOf(text), rgx, lmt);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     */
    public SplitPreserveAllTokens(final Text text, final CharSequence rgx) {
        this(text, new TextOf(rgx));
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(final Text text, final CharSequence rgx, final int lmt) {
        this(text, new TextOf(rgx), lmt);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     */
    public SplitPreserveAllTokens(final Text text, final Text rgx) {
        this(text, rgx, 0);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param rgx The regex
     * @param lmt The limit
     */
    public SplitPreserveAllTokens(
        final Text text,
        final Text rgx,
        final int lmt
    ) {
        super(
            new Mapped<>(
                TextOf::new,
                new IterableOf<>(
                    () -> new IteratorOf<>(
                        new TriFuncSplitPreserve()
                            .apply(
                                text.toString(),
                                rgx.toString(),
                                lmt
                            ).toArray(new String[0])
                    )
                )
            )
        );
    }
}
