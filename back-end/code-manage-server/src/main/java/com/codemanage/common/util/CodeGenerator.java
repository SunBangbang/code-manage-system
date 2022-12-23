package com.codemanage.common.util;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName CodeGenerator
 * @Description 代码生成
 * @author hyh
 * @since 2022-05-30
 */
public class CodeGenerator {
//    private static final String url = "jdbc:mysql://127.0.0.1:3306/test?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useAffectedRows=true&useSSL=false";
//    SqlServer
//    private static final String url = "jdbc:sqlserver://192.168.0.144:1433;DatabaseName=bs_global_situation";
//    private static final String username = "sa";
//    private static final String password = "Jrqx123456";

    // PostgresSQL
    private static final String url = "jdbc:postgresql://47.93.145.101:5432/code_manage";
    private static final String username = "postgres";
    private static final String password = "123456";

    public static void main(String[] args) {

        String author = "hyh";
        String moduleName = "code";
        String tablePrefix = "cvcd";
        List<String> tableList = new ArrayList<>();
//        tableList.add("cvso_dbms_m");
//        tableList.add("cvcd_cd_ppty_m");
//        tableList.add("cvcd_cd_ppty_r");
//        tableList.add("cvcd_cd_ppyv_m");
//        tableList.add("cvcd_cdvl_m");
//        tableList.add("cvcd_cdvl_r");
        tableList.add("cvcd_cd_ppty_fild_bnd_r");

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    // 指定作者
                    builder.author(author)
                            // 生成文件的输出路径
                            .outputDir(System.getProperty("user.dir") + "\\src\\main\\java")
                            //支持swagger
                            .enableSwagger()
                            .dateType(DateType.ONLY_DATE)
                            .fileOverride()
                            // 时间格式
                            .commentDate("yyyy-MM-dd");
                })
                // 包名配置
                .packageConfig(builder -> {
                    builder.parent("com.codemanage")
                            .moduleName(moduleName)
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .mapper("mapper")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir")+"\\src\\main\\resources\\mapper\\"+moduleName));
                })
                .strategyConfig(builder -> {
                    // 要生成的表
                    builder.addInclude(tableList)
                            // 表前缀
                            .addTablePrefix(tablePrefix)
                            // service策略配置
                            .serviceBuilder()
                            .formatServiceFileName("I%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            // 实体策略配置
                            .entityBuilder()
                            // 开启lombok
                            .enableLombok()
                            // 说明逻辑删除字段
                            .logicDeleteColumnName("del_flag")
                            // 属性加上说明注解
                            .enableTableFieldAnnotation()
                            // controller 策略配置
                            .controllerBuilder()
                            .formatFileName("%sController")
                            // 开启RestController
                            .enableRestStyle()
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }


}
