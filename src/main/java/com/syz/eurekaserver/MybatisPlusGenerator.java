package com.syz.eurekaserver;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @ProjectName: mybatis-plus
 * @Package: com.czkj.mybatisplus.generate
 * @ClassName: Generator
 * @Author: Administrator
 * @Description:
 * @Date 2019/11/19/01916:38
 */
public class MybatisPlusGenerator {

    private static MybatisPlusGenerator single = null;

    private MybatisPlusGenerator() {
        super();
    }

    private static MybatisPlusGenerator getSingle() {
        if (single == null) {
            single = new MybatisPlusGenerator();
        }
        return single;
    }

    public void autoGeneration() {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://localhost:3306/world?autoReconnect=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2b8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("sheng1988")
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
//                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("t_")
                .setInclude("city","country")
                .setNaming(NamingStrategy.underline_to_camel);
        config.setActiveRecord(false)
                .setEnableCache(false)
                .setAuthor("steven.sheng")
                //指定输出文件夹位置
                .setOutputDir("E:\\githubWorkspace\\short-link\\src\\main\\java")
                .setFileOverride(true)
                .setServiceName("%sService");
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent("com.syz.eurekaserver")
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }

    public static void main(String[] args) {
        MybatisPlusGenerator generator = MybatisPlusGenerator.getSingle();
        generator.autoGeneration();
    }
}
