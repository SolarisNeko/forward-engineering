package com.neko233.forward;

import com.neko233.forward.dto.Email;
import com.neko233.forward.codeEngine.constant.CodeEngineName;
import org.junit.jupiter.api.Test;

/**
 * @author LuoHaoJun on 2023-01-19
 **/
public class ForwardEngineForCodeTest {

    @Test
    public void runClass() {
        String s = ForwardEngineForCode.runClass(CodeEngineName.JAVA_SPRING_WEB_2_x_x, Email.class);
        System.out.println(s);
    }
}