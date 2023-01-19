package com.neko233.forward.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author LuoHaoJun on 2023-01-19
 **/
public class ClasspathUtils {


    public static void main(String[] args) throws IOException {
        URL base = ClasspathUtils.class.getResource("");

        String classPath = "com.neko233.forward.util";
        String replacement = "\\" + File.separator;
        String path = new File(base.getFile(), classPath.replaceAll("\\.", replacement)).getCanonicalPath();

        System.out.println(path);
    }
}
