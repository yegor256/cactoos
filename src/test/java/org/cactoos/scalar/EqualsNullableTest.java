package org.cactoos.scalar;

import org.junit.Test;

/**
 * Test case for {@link EqualsNullable}.
 *
 */
public class EqualsNullableTest {

    @Test
    public void nullEqualsNull() throws Exception {
        assert new EqualsNullable(null, null).value();
    }

    @Test
    public void equals() throws Exception {
        assert new EqualsNullable(1, 1).value();
    }

    @Test
    public void notEquals() throws Exception {
        Boolean value = new EqualsNullable(1, 2).value();
        assert !value;
    }
}
