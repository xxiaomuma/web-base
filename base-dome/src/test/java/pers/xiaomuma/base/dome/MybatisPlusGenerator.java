package pers.xiaomuma.base.dome;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

public class MybatisPlusGenerator {

    private static final String OUTPUT_DIR = "";
    private static final String PACKAGE_PARENT = "pers.xiaomuma.base.dome";
    private static final String PACKAGE_MODEL = "domain";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/dome1?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useUnicode=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static final String DRIVE_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        //代码生成器
        AutoGenerator generator = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(OUTPUT_DIR);
        gc.setOpen(true);
        gc.setBaseResultMap(true);
        gc.setFileOverride(true);
        gc.setMapperName("%sDAO");
        gc.setXmlName("%sMapper");
        generator.setGlobalConfig(gc);
        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(URL);
        dsc.setDriverName(DRIVE_CLASS_NAME);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        generator.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(PACKAGE_MODEL);
        pc.setParent(PACKAGE_PARENT);
        pc.setMapper("dao");
        pc.setEntity(PACKAGE_MODEL);
        generator.setPackageInfo(pc);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
        generator.setCfg(cfg);
        TemplateConfig tc = new TemplateConfig();
        generator.setTemplate(tc);
        // 策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel);
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        sc.setEntityLombokModel(true);
        sc.setEntityBuilderModel(true);
        // 写于父类中的公共字段
        sc.setControllerMappingHyphenStyle(true);
        // 需要生成的表
        sc.setInclude(
            ""
        );
        generator.setStrategy(sc);
        generator.setTemplateEngine(new VelocityTemplateEngine());
        generator.execute();
    }
}
