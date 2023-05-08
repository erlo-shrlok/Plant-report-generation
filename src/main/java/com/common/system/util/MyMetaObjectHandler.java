package com.common.system.util;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName("createTime",metaObject);
        if(createTime == null){
            setFieldValByName("createTime", LocalDateTime.now().toString(),metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 如果需要更新时自动填充字段，可以在此实现
    }
}
