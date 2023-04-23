package org.example.Calc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalc {

    private String Token;
    private static final String DefaultToken = ",:";

    public int Calc(String cmd) {
        if (ValidateCmd(cmd)) {
            return 0;
        }

        String target = ExtractToken(cmd);
        String[] split = target.split("["+Token+"]");
        return CalculateString(split);
    }

    public String ExtractToken(String cmd) {
        // 정규식을 이용하는 방법이 유지보수에 더 좋다고 생각된다.
        // 패턴이 변경되는 경우 인덱싱을 통한 방법은 로직 자체가 변경되어야하는 반면
        // 정규식을 사용하는 방법은 정규식 패턴만 변경하면 되기 때문
        Matcher m = Pattern.compile("//(.)\n(.*)").matcher(cmd);
        if (m.find()) {
            Token = m.group(1);
            return m.group(2);
        }
//        if (cmd.startsWith("//")) {
//            int endIdx = cmd.indexOf("\n");
//            Token = cmd.substring(2, endIdx);
//            return cmd.substring(endIdx+1);
//        }
        Token = DefaultToken;
        return cmd;
    }

    public String getToken() {
        return Token;
    }

    public boolean ValidateCmd(String cmd) {
        return cmd == null || cmd.isEmpty();
    }

    public int CalculateString(String[] SplitedString) {
        int result = 0;
        for (String item : SplitedString) {
            result += ParseInteger(item);
        }
        return result;
    }

    public int ParseInteger(String item) {
        int res = 0;
        try {
            res = Integer.parseInt(item);
        }
        catch (Exception e) {
            return 0;
        }

        if (res < 0)
            throw new RuntimeException();

        return res;
    }
}
