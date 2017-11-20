package com.even.model;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

public class _generatorDao {
    private static DataSource getDataSource(){
        PropKit.use("config.properties");
        String url = PropKit.get("jdbcUrl");
        String user = PropKit.get("user");
        String password = PropKit.get("password").trim();
        DruidPlugin druidPlugin = new DruidPlugin(url,user,password);
        druidPlugin.setInitialSize(1);
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

    public static void main(String[] args){
        // base model 所使用的包名
        String baseModelPackageName = "com.even.model.base";
        // base model 文件保存路径
        System.out.println(PathKit.getWebRootPath());
        String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/even/model/base";
        System.out.println("model url:" + baseModelOutputDir);
        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "com.even.model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";
        // 创建生成器
        Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        // 添加不需要生成的表名
        // generator.addExcludedTable("");
        // 设置是否在 Model 中生成 dao 对象
        generator.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        generator.setGenerateDataDictionary(false);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
//        generator.setRemovedTableNamePrefixes("table_");
        // 生成
        generator.generate();
    }
}
