package com.neko233.forward.codeEngine.impl;

import com.neko233.common.base.KvTemplate;
import com.neko233.forward.codeEngine.CodeEngine;
import com.neko233.forward.codeEngine.domain.CodeEngineMetadata;
import com.neko233.forward.util.StringUtilsByForward;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LuoHaoJun on 2023-01-19
 **/
public class CodeEngineByJavaDaoCrudSimpleTemplate implements CodeEngine {

    public static final String DAO = "dao";

    @Override
    public String generateCode(CodeEngineMetadata metadata) {

        Class clazz = metadata.getTargetClass();
        final String className = clazz.getSimpleName();

        final Map<String, String> propertiesMap = new HashMap<String, String>() {{
            put("className", className);
            put("className.firstLowerCase", StringUtilsByForward.firstWordLowerCase(className));
        }};

        final Map<String, String> codeMap = new HashMap<>();

        // code
        final String dao = KvTemplate.builder(DAO_CRUD_TEMPLATE)
                .put(propertiesMap)
                .build();
        codeMap.put(DAO, dao);

        StringBuilder daoCodeBuilder = new StringBuilder();
        codeMap.forEach((moduleName, code) -> {
            daoCodeBuilder.append(System.lineSeparator() + "//------------ " + moduleName + " -----------------" + System.lineSeparator())
                    .append(code);
        });

        return daoCodeBuilder.toString();
    }

    /**
     * I don't know what you use
     */
    public static final String DAO_CRUD_TEMPLATE = "public class ${className}Dao {\n" +
            "\n" +
            "    public static final String INSERT = \"\";\n" +
            "    public static final String DELETE = \"\";\n" +
            "    public static final String UPDATE = \"\";\n" +
            "    public static final String SELECT_ALL = \"\";\n" +
            "\n" +
            "    \n" +
            "    public boolean insert(List<${className}> dataList) {\n" +
            "        \n" +
            "        return true;\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    public boolean delete(List<${className}> dataList) {\n" +
            "        \n" +
            "        return true;\n" +
            "    }\n" +
            "    \n" +
            "    public boolean update(List<${className}> dataList) {\n" +
            "        \n" +
            "        return true;\n" +
            "    }\n" +
            "\n" +
            "    public List<${className}> selectAll() {\n" +
            "        \n" +
            "        return new ArrayList<>();\n" +
            "    }\n" +
            "\n" +
            "}\n";
}
