package com.common.system.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.*;
import java.util.Map;

/**
 * Word文档工具类
 */
public class WordUtil {
    /**
     * 使用FreeMarker自动生成Word文档
     * @param dataMap   生成Word文档所需要的数据
     */
    public static void generateWord(Map<String, Object> dataMap, OutputStream outputStream) throws Exception {
        // 设置FreeMarker的版本和编码格式
        Configuration configuration = new Configuration(new Version("2.3.26-incubating"));
        configuration.setDefaultEncoding("UTF-8");

        // 设置FreeMarker生成Word文档所需要的模板的路径
        configuration.setDirectoryForTemplateLoading(new File("D:\\Temp\\javawork\\common-admin-faild"));
        // 设置FreeMarker生成Word文档所需要的模板
        Template t = configuration.getTemplate("report00.xml", "UTF-8");
        // 创建一个Word文档的输出流
        Writer out = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
        //FreeMarker使用Word模板和数据生成Word文档
        t.process(dataMap, out);
        out.flush();
    }
}
