package com.common.system.util;

import com.github.yitter.idgen.YitIdHelper;

public class NewId {
    /**
     * 根据雪花算法生成纯数字的id
     * @return 新id
     */
    public static String nextId(){
        return YitIdHelper.nextId()+"";
    }
}
