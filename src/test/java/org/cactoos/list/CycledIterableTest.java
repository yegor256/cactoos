package org.cactoos.list;

import org.cactoos.ScalarHasValue;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test Case for {@link CycledIterable}.
 * @author Ilia Rogozhin (ilia.rogozhin@gmail.com)
 * @version $Id$
 * @since 0.7
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class CycledIterableTest {

    @Test
    public void repeatIterableTest() throws Exception {
        MatcherAssert.assertThat(
            "Can't repeat iterable",
            new ItemOfIterable<>(
                new CycledIterable<>(
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
