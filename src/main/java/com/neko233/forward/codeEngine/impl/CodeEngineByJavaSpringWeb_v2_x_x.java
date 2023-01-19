package com.neko233.forward.codeEngine.impl;

import com.neko233.common.base.KvTemplate;
import com.neko233.common.reflect.ReflectUtils;
import com.neko233.forward.codeEngine.CodeEngine;
import com.neko233.forward.codeEngine.domain.CodeEngineMetadata;
import com.neko233.forward.util.StringUtilsByForward;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LuoHaoJun on 2023-01-19
 **/
public class CodeEngineByJavaSpringWeb_v2_x_x implements CodeEngine {

    public static final String CONTROLLER_NAME = "controller";
    public static final String SERVICE = "service";
    public static final String SERVICE_IMPL = "serviceImpl";

    @Override
    public final String generateCode(CodeEngineMetadata metadata) {

        Class clazz = metadata.getTargetClass();
        final String className = clazz.getSimpleName();
        List<Field> allFieldsRecursive = ReflectUtils.getAllFieldsRecursive(clazz);

        final Map<String, String> propertiesMap = new HashMap<String, String>() {{
            put("className", className);
            put("className.firstLowerCase", StringUtilsByForward.firstWordLowerCase(className));
            put(CONTROLLER_NAME, className + "Controller");
            put(SERVICE, className + "Service");
            put(SERVICE_IMPL, className + "ServiceImpl");
        }};

        final Map<String, String> codeMap = new HashMap<>();

        // code
        final String controllerApi = KvTemplate.builder(controllerApiTemplate)
                .put(propertiesMap)
                .build();
        codeMap.put(CONTROLLER_NAME, controllerApi);

        final String serviceApi = KvTemplate.builder(serviceApiTemplate)
                .put(propertiesMap)
                .build();
        codeMap.put(SERVICE, serviceApi);

        final String serviceApiImpl = KvTemplate.builder(serviceApiImplTemplate)
                .put(propertiesMap)
                .build();
        codeMap.put(SERVICE_IMPL, serviceApiImpl);

        StringBuilder mvcCodeBuilder = new StringBuilder();
        codeMap.forEach((moduleName, code) -> {
            mvcCodeBuilder.append(System.lineSeparator() + "//------------ " + moduleName + " -----------------" + System.lineSeparator())
                    .append(code);
        });

        return mvcCodeBuilder.toString();
    }


    final String controllerApiTemplate = "@RestController\n" +
            "@RequestMapping(\"/${className.firstLowerCase}\")\n" +
            "public class ${className}Controller {\n" +
            "\n" +
            "    @Resource\n" +
            "    private ${className}Service ${className.firstLowerCase}Service;\n" +
            "\n" +
            "    @RequestMapping(\"/add\")\n" +
            "    public Map<String, String> add(@RequestBody List<${className}> dtoList) {\n" +
            "        boolean isSuccess = ${className.firstLowerCase}Service.add(dtoList);\n" +
            "        Map<String, String> map = new HashMap<>();\n" +
            "        if (!isSuccess) {\n" +
            "            map.put(\"msg\", \"添加失败\");\n" +
            "        }\n" +
            "        return map;\n" +
            "    }\n" +
            "\n" +
            "    @RequestMapping(\"/delete\")\n" +
            "    public Map<String, String> delete(@RequestBody List<${className}> dtoList) {\n" +
            "        boolean isSuccess = ${className.firstLowerCase}Service.delete(dtoList);\n" +
            "        Map<String, String> map = new HashMap<>();\n" +
            "        if (!isSuccess) {\n" +
            "            map.put(\"msg\", \"删除失败\");\n" +
            "        }\n" +
            "        return map;\n" +
            "    }\n" +
            "\n" +
            "    @RequestMapping(\"/update\")\n" +
            "    public Map<String, String> update(@RequestBody List<${className}> dtoList) {\n" +
            "        boolean isSuccess = ${className.firstLowerCase}Service.update(dtoList);\n" +
            "        Map<String, String> map = new HashMap<>();\n" +
            "        if (!isSuccess) {\n" +
            "            map.put(\"msg\", \"删除失败\");\n" +
            "        }\n" +
            "        return map;\n" +
            "    }\n" +
            "\n" +
            "    @RequestMapping(\"/selectAll\")\n" +
            "    public List<${className}> selectAll() {\n" +
            "        return ${className.firstLowerCase}Service.selectAll();\n" +
            "    }\n" +
            "\n" +
            "}\n";


    final String serviceApiTemplate = "public interface ${className}Service {\n" +
            "    boolean add(List<${className}> dtoList);\n" +
            "\n" +
            "    boolean delete(List<${className}> dtoList);\n" +
            "\n" +
            "    boolean update(List<${className}> dtoList);\n" +
            "\n" +
            "    List<${className}> selectAll();\n" +
            "}\n";

    final String serviceApiImplTemplate = "@Service\n" +
            "public class ${className}ServiceImpl implements ${className}Service {\n" +
            "\n" +
            "    private static final Logger log = LoggerFactory.getLogger(getClass());\n" +
            "    \n" +
            "    @Autowired\n" +
            "    private ${className}Dao ${className.firstLowerCase}Dao;\n" +
            "    \n" +
            "    @Override\n" +
            "    public boolean add(List<${className}> dtoList) {\n" +
            "        try {\n" +
            "            ${className.firstLowerCase}Dao.insert(dtoList);\n" +
            "        } catch (Exception e) {\n" +
            "            log.error(\"[${className}Service] insert error\", e);\n" +
            "            return false;\n" +
            "        }\n" +
            "        return true;\n" +
            "    }\n" +
            "\n" +
            "    \n" +
            "    \n" +
            "    @Override\n" +
            "    public boolean delete(List<${className}> dtoList) {\n" +
            "        try {\n" +
            "            ${className.firstLowerCase}Dao.delete(dtoList);\n" +
            "        } catch (Exception e) {\n" +
            "            log.error(\"[${className}Service] insert error\", e);\n" +
            "            return false;\n" +
            "        }\n" +
            "        return true;\n" +
            "    }\n" +
            "\n" +
            "    \n" +
            "    \n" +
            "    @Override\n" +
            "    public boolean update(List<${className}> dtoList) {\n" +
            "        try {\n" +
            "            ${className.firstLowerCase}Dao.update(dtoList);\n" +
            "        } catch (Exception e) {\n" +
            "            log.error(\"[${className}Service] insert error\", e);\n" +
            "            return false;\n" +
            "        }\n" +
            "        return true;\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public List<${className}> selectAll() {\n" +
            "        return ${className.firstLowerCase}Dao.selectAll();\n" +
            "    }\n" +
            "\n" +
            "}\n";


}
