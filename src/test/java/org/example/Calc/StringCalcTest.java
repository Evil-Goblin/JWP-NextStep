package org.example.Calc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
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

        assertThat(calc.Calc(test1)).isEqualTo(0);
        assertThat(calc.Calc(test2)).isEqualTo(3);
        assertThat(calc.Calc(test3)).isEqualTo(6);
        assertThat(calc.Calc(test4)).isEqualTo(6);
        assertThat(calc.Calc(test5)).isEqualTo(6);
    }

    @Test
    void extractToken() {
        String test1 = "//;\n1;2;3";

        assertThat(calc.ExtractToken(test1)).isEqualTo("1;2;3");
        assertThat(calc.getToken()).isEqualTo(";");
    }

    @Test
    void NullCheck() {
        String NULL = null;
        String empty = "";
        String other = "asdf";

        assertThat(calc.ValidateCmd(NULL)).isTrue();
        assertThat(calc.ValidateCmd(empty)).isTrue();
        assertThat(calc.ValidateCmd(other)).isFalse();
    }

    @Test
    void add_negative() throws Exception {
        String negative = "-1,2,3";

        assertThatThrownBy(() -> calc.Calc(negative))
                .isInstanceOf(RuntimeException.class);
    }
}
