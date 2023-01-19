package com.neko233.forward.codeEngine;

import com.neko233.forward.codeEngine.constant.CodeEngineName;
import com.neko233.forward.codeEngine.impl.CodeEngineByJavaDaoCrudSimpleTemplate;
import com.neko233.forward.codeEngine.impl.CodeEngineByJavaSpringWebV2xx;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SolarisNeko on 2023-01-19
 **/
@Slf4j
public class CodeEngineFactory {

    /**
     * default & extra
     */
    private static final Map<String, CodeEngine> CODE_ENGINE_MAP = new HashMap<String, CodeEngine>() {{
        put(CodeEngineName.JAVA_SPRING_WEB_2_x_x, new CodeEngineByJavaSpringWebV2xx());
        put(CodeEngineName.JAVA_DAO_CRUD_SIMPLE_TEMPLATE, new CodeEngineByJavaDaoCrudSimpleTemplate());
    }};

    /**
     * 注册自定义的策略
     *
     * @param codeStrategy 策略名
     * @param strategy     策略API
     */
    public static void register(Class codeStrategy, CodeEngine strategy) {
        if (codeStrategy == null) {
            throw new IllegalArgumentException("your codeStrategy = null");
        }
        register(codeStrategy.getName(), strategy);
    }

    public static void register(String codeStrategy, CodeEngine strategy) {
        CODE_ENGINE_MAP.merge(codeStrategy, strategy, (v1, v2) -> {
            log.warn("Already exist mvcStrategy = " + codeStrategy);
            return v2;
        });
    }

    /**
     * 生成对应的工厂
     *
     * @param mvcStrategy 选择的db类型
     * @return 生成策略
     */
    public static CodeEngine choose(String mvcStrategy) {
        CodeEngine mvEngine = CODE_ENGINE_MAP.get(mvcStrategy);
        if (mvEngine == null) {
            String eMsg = String.format("not find this Strategy. check your mvcStrategy = %s is register? or your lifecycle is error.", String.valueOf(mvcStrategy));
            throw new RuntimeException(eMsg);
        }
        return mvEngine;
    }
}
