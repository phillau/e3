package com.itheima;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FreeMarkerTest {
    @Test
    public void test1() throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDirectoryForTemplateLoading(new File("D:\\sourcecode\\idea\\e3\\e3-item-web\\src\\test\\resources"));
        configuration.setDefaultEncoding("utf-8");
        Template template = configuration.getTemplate("hello.ftl");
        Map<String,Object> map = new HashMap();
//        map.put("name","liufei");
        Student student1 = new Student("liufei",24);
        Student student2 = new Student("guorui",23);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        map.put("students",students);
        map.put("date",new Date());
        FileWriter fileWriter = new FileWriter("E:\\javaProject\\csdn-collector\\freemarker\\hello.txt");
        template.process(map,fileWriter);
        fileWriter.close();
    }
}
