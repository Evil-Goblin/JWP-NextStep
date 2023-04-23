package org.example.Calc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalcTest {

    private StringCalc calc;

    @BeforeEach
    void setUp() {
        calc = new StringCalc();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void calc() {
        String test1 = "";
        String test2 = "1,2";
        String test3 = "1,2,3";
        String test4 = "1,2:3";
        String test5 = "//;\n1;2;3";

        assertEquals(0, calc.Calc(test1));
        assertEquals(3, calc.Calc(test2));
        assertEquals(6, calc.Calc(test3));
        assertEquals(6, calc.Calc(test4));
        assertEquals(6, calc.Calc(test5));
    }

    @Test
    void extractToken() {
        String test1 = "//;\n1;2;3";

        assertEquals("1;2;3", calc.ExtractToken(test1));
        assertEquals(";", calc.getToken());
    }

    @Test
    void NullCheck() {
        String NULL = null;
        String empty = "";
        String other = "asdf";

        assertTrue(calc.ValidateCmd(NULL));
        assertTrue(calc.ValidateCmd(empty));
        assertFalse(calc.ValidateCmd(other));
    }

    @Test
    void add_negative() throws Exception {
        String negative = "-1,2,3";

        assertThrows(RuntimeException.class, () -> {
            calc.Calc(negative);
        });
    }
}