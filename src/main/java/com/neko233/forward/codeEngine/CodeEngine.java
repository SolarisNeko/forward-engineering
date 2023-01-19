package com.neko233.forward.codeEngine;

import com.neko233.forward.codeEngine.domain.CodeEngineMetadata;

/**
 * @author SolarisNeko on 2023-01-19
 **/
public interface CodeEngine {


    /**
     * 生成 MVC 代码
     *
     * @param metadata 元数据
     * @return 生成的代码
     */
    String generateCode(CodeEngineMetadata metadata);


}
