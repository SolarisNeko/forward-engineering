package com.neko233.forward.codeEngine.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author SolarisNeko on 2023-01-19
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeEngineMetadata {

    private Class targetClass;

    /**
     * diy 配置
     */
    private Map<String, String> diyConfigMap;

}
