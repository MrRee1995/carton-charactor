package com.ai.kids.cartoncharactor.utils;

import java.util.UUID;

public class KeyUtils {

    /**
     * 生成唯一的主键UUID
     * @return
     */
    public static synchronized String genUniqueKey(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
