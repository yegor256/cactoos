package org.cactoos.text;

import org.cactoos.Text;

import java.io.IOException;

/**
 * Replace the Text.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ReplacedText implements Text {

    /**
     * The text.
     */
    private final Text origin;

    /**
     * The old char.
     */
    private String oldChar;

    /**
     * The new char.
     */
    private String newChar;

    /**
     * Ctor.
     *
     * @param text    The text
     * @param oldChar The old char
     * @param newChar The new char
     */
    public ReplacedText(final Text text, String oldChar, String newChar) {
        this.origin = text;
        this.oldChar = oldChar;
        this.newChar = newChar;
    }

    @Override
    public String asString() throws IOException {
        return origin.asString().replace(oldChar, newChar);
    }
}
