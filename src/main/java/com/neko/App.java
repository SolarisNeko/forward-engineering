package com.neko;

import com.neko.forward.engineer.ForwardEngine;

/**
 * 正向工程
 *  App 为 演示
 */
public class App {

    public static void main(String[] args) {

//        ForwardEngineer.readMe();

//       ForwardEngine.runClass("com.neko.pojo.entity.SystemUser");

       ForwardEngine.runPackage("com.neko.pojo.entity");
    }

}
