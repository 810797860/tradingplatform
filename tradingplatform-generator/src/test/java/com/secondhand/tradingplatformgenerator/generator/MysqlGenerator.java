package com.secondhand.tradingplatformgenerator.generator;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.*;

/**
 * MybatisPlus代码生成器
 */

public class MysqlGenerator {

    private static String packageName="";
    private static String packageClass="tradingplatformgenerator";
    private static String projectName="secondhand";
    private static String authorName="zhangjk";
    private static String[] table=new String[]{"s_base_menu"};
    private static String[] prefix=new String[]{"s_base_"};
    private static File file = new File(packageName);
    private static String path = file.getAbsolutePath();

    public static void main(String[] args) {
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig()
/*                        .setOutputDir(path+"/src/main/java")//输出目录*/
                        .setOutputDir("D:\\code")
                        .setFileOverride(true)// 是否覆盖文件
                        .setActiveRecord(true)// 开启 activeRecord 模式
                        .setEnableCache(false)// XML 二级缓存
                        .setBaseResultMap(true)// XML ResultMap
                        .setBaseColumnList(true)// XML columList
                        .setOpen(false)//生成后打开文件夹
                        .setAuthor(authorName)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                 .setMapperName("%sMapper")
                 .setXmlName("%sMapper")
                 .setServiceName("%sService")
                 .setServiceImplName("%sServiceImpl")
                 .setControllerName("%sController")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        .setDbType(DbType.MYSQL)// 数据库类型
                        .setTypeConvert(new MySqlTypeConvert() {
                            // 自定义数据库表字段类型转换【可选】
                            @Override
                            public DbColumnType processTypeConvert(String fieldType) {
                                System.out.println("转换类型：" + fieldType);
                                // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                                //    return DbColumnType.BOOLEAN;
                                // }
                                return super.processTypeConvert(fieldType);
                            }
                        })
                        .setDriverName("com.mysql.jdbc.Driver")
                        .setUsername("root")
                        .setPassword("123456")
/*                        .setUrl("jdbc:mysql://mysqldb:3306/myshtp?characterEncoding=utf8")*/
                        .setUrl("jdbc:mysql://localhost:3306/myshtp?characterEncoding=UTF-8&useUnicode=true&useSSL=false")
        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        // .setCapitalMode(true)// 全局大写命名
                        //.setDbColumnUnderline(true)//全局下划线命名
                        .setTablePrefix(prefix)// 此处可以修改为您的表前缀
                        .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                        .setInclude(table) // 需要生成的表
                        .setRestControllerStyle(true)
                        //.setExclude(new String[]{"test"}) // 排除生成的表
                        // 自定义实体父类
                        // .setSuperEntityClass("com.baomidou.demo.TestEntity")
                        // 自定义实体，公共字段
                        //.setSuperEntityColumns(new String[]{"test_id"})
                        .setTableFillList(tableFillList)
                // 自定义 dao 父类
                .setSuperMapperClass("com.secondhand.tradingplatformcommon.base.BaseDao.BaseDao")
                // 自定义 service 父类
                .setSuperServiceClass("com.secondhand.tradingplatformcommon.base.BaseService.BaseService")
                // 自定义 service 实现类父类
                .setSuperServiceImplClass("com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl")
                // 自定义 controller 父类
                .setSuperControllerClass("com.secondhand.tradingplatformcommon.base.BaseController.BaseController")
                // 自定义 entity 父类
                .setSuperEntityClass("com.secondhand.tradingplatformcommon.base.BaseEntity.BaseEntity")
                // 【实体】是否生成字段常量（默认 false）
                // public static final String ID = "test_id";
                // .setEntityColumnConstant(true)
                // 【实体】是否为构建者模型（默认 false）
                // public UserClient setName(String name) {this.name = name; return this;}
                // .setEntityBuilderModel(true)
                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                // .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                // .setEntityBooleanColumnRemoveIsPrefix(true)
                // .setRestControllerStyle(true)
                // .setControllerMappingHyphenStyle(true)
        ).setPackageInfo(
                // 包配置
                new PackageConfig()
                        //.setModuleName("UserClient")
                        .setParent("com."+projectName+"."+packageClass)// 自定义包路径
/*                        .setParent("com." + projectName)*/
                        .setController("controller")// 这里是控制器包名，默认 web
                        .setEntity("entity")
                        .setMapper("dao")
                        .setService("service")
                        .setServiceImpl("service.impl")
                        .setXml("dao")
//        ).setCfg(
//                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
//                new InjectionConfig() {
//                    @Override
//                    public void initMap() {
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                        this.setMap(map);
//                    }
//                }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
//                    // 自定义输出文件目录
//                    @Override
//                    public String outputFile(TableInfo tableInfo) {
//                        return path+"/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
//                    }
//                }))
        ).setTemplate(
                // 关闭默认 xml 生成，调整生成 至 根目录
                new TemplateConfig().setXml(null)
                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：

                .setController("")
                .setEntity("")
                .setMapper("")
                .setXml("")
                .setService("")
                .setServiceImpl("")

                .setController("/template/controller.java.vm")
                .setEntity("/template/entity.java.vm")
                .setMapper("/template/mapper.java.vm")
                .setXml("/template/mapper.xml.vm")
                .setService("/template/service.java.vm")
                .setServiceImpl("/template/serviceImpl.java.vm")
        );

        // 执行生成
        mpg.execute();

//        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
//        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}
