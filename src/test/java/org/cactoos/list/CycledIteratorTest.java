package org.cactoos.list;

import org.cactoos.ScalarHasValue;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test Case for {@link CycledIterator}.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class CycledIteratorTest {

    @Test
    public void repeatIteratorTest() throws Exception {
        MatcherAssert.assertThat(
            "Can't repeat iterator",
            new ItemOfIterator<>(
                new CycledIterator<>(
                    new ArrayAsIterable<>(
                        "one", "two", "three"
                    )
                ),
                7
            ),
            new ScalarHasValue<>(
                "two"
            )
        );
    }
}
