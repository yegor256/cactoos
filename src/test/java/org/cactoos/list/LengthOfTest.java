package org.cactoos.list;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link LengthOf}.
 *
 * @author Andriy Kryvtsun (kontiky@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class LengthOfTest {

    /**
     * Empty iterable length calculation.
     */
    @Test
    public void calcsLengthOfEmptyIterable() throws Exception {
        MatcherAssert.assertThat(
            new LengthOf(
                new ArrayAsIterable<>()
            ).asValue(),
            Matchers.equalTo(0)
        );
    }

    /**
     * Non empty iterable length calculation.
     */
    @Test
    public void calcsLengthOfNonEmptyIterable() throws Exception {
        MatcherAssert.assertThat(
            new LengthOf(
                    new ArrayAsIterable<>(1, 2, 3)
            ).asValue(),
            Matchers.equalTo(3)
        );
    }
}
