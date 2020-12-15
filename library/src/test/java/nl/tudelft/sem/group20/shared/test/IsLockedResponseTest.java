package nl.tudelft.sem.group20.shared.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import nl.tudelft.sem.group20.shared.AuthResponse;
import nl.tudelft.sem.group20.shared.IsLockedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class IsLockedResponseTest {
    private transient IsLockedResponse isLockedResponse;

    @BeforeEach
    void setUp() {
        isLockedResponse = new IsLockedResponse(false);
    }

    @Test
    void constructorTest() {
        IsLockedResponse test = new IsLockedResponse(false);
        assertFalse(test.isLocked());
    }

    @Test
    void testLocked() {
        assertFalse(isLockedResponse.isLocked());
    }

}
