package generate;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据配置自动生成Mapper,service,controller代码
 */
public class AutoGenerate {
    /**
     * 实体类模板
     */
    private static final String ENTITY_TEMP = "/codeTemp/entity.java.ftl";
    /**
     * 实体类-Do模板
     */
    private static final String ENTITY_DO_TEMP = "/codeTemp/entityDo.java.ftl";
    /**
     * 实体类-Dto模板
     */
    private static final String ENTITY_DTO_TEMP= "/codeTemp/entityDto.java.ftl";
    /**
     * 实体类-Info模板
     */
    private static final String ENTITY_INFO_TEMP = "/codeTemp/entityInfo.java.ftl";

    /**
     * 实体类-Base模板
     */
    private static final String ENTITY_BASE_TEMP="/codeTemp/entityBase.java.ftl";
    /**
     * 实体类-BaseDto模板
     */
    private static final String ENTITY_DTO_BASE_TEMP = "/codeTemp/entityBaseDto.java.ftl";
    /**
     * 实体类-BaseInfo模板
     */
    private static final String ENTITY_INFO_BASE_TEMP = "/codeTemp/entityBaseInfo.java.ftl";
    /**
     * mapper文件模板
     */
    private static final String MAPPER_TEMP = "/codeTemp/mapper.java.ftl";
    /**
     * mapper wrapper模板
     */
    private static final String MAPPER_WRAPPER_TEMP = "/codeTemp/mapperWrapper.java.ftl";
    /**
     * mapper wrapper实现模板
     */
    private static final String MAPPER_WRAPPER_IMPL_TEMP = "/codeTemp/mapperWrapperImpl.java.ftl";
    /**
     * mapper xml文件模板
     */
    private static final String MAPPER_XML_TEMP = "/codeTemp/mapper.xml.ftl";

    /**
     * service类模板
     */
    private static final String SERVICE_TEMP = "/codeTemp/service.java.ftl";
    /**
     * service实现类模板
     */
    private static final String SERVICE_IMPL_TEMP = "/codeTemp/serviceImpl.java.ftl";
    /**
     * wrapper类模板
     */
    private static final String WRAPPER_TEMP = "/codeTemp/wrapper.java.ftl";
    /**
     * wrapper实现类模板
     */
    private static final String WRAPPER_IMPL_TEMP = "/codeTemp/wrapperImpl.java.ftl";
    /**
     * controller类模板
     */
    private static final String CONTROLLER_TEMP = "/codeTemp/controller.java.ftl";
    /**
     * 要生成的表
     */
    private static final String[] TABLES = new String[]{};
    private static final String ROOT_PATH;
    private static String MODULE_NAME= StringUtils.EMPTY;
    //数据库名
    private static final String DATABASE="parking";
    //公司名字
    private static final String COMPANY_NAME="meitian";

    private static final String ENTITY_PATH= "/"+DATABASE+"-entity/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/entity/";
    private static final String ENTITY_BASE_PATH="/"+DATABASE+"-entity/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/entity/";

    private static final String ENTITY_DO_PATH= "/"+DATABASE+"-dto/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/dto/";
    private static final String ENTITY_DTO_PATH= "/"+DATABASE+"-dto/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/dto/";
    private static final String ENTITY_INFO_PATH = "/"+DATABASE+"-dto/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/dto/";
    private static final String ENTITY_DTO_BASE_PATH = "/"+DATABASE+"-dto/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/dto/";
    private static final String ENTITY_INFO_BASE_PATH = "/"+DATABASE+"-dto/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/dto/";
    private static final String MAPPER_PATH = "/"+DATABASE+"-mapper/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/mapper/";
    private static final String MAPPER_WRAPPER_PATH ="/"+DATABASE+"-mapper/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/mapper/";
    private static final String MAPPER_WRAPPER_IMPL_PATH = "/"+DATABASE+"-mapper/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/mapper/";
    private static final String MAPPER_XML_PATH = "/"+DATABASE+"-mapper/src/main/resources/mappers/";
    private static final String SERVICE_PATH = "/"+DATABASE+"-biz/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/biz/";
    private static final String SERVICE_IMPL_PATH = "/"+DATABASE+"-biz/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/biz/";
    private static final String WRAPPER_PATH = "/"+DATABASE+"-biz/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/biz/";
    private static final String WRAPPER_IMPL_PATH = "/"+DATABASE+"-biz/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/biz/";
    private static final String CONTROLLER_PATH = "/"+DATABASE+"-web/src/main/java/com/"+COMPANY_NAME+"/"+DATABASE+"/web/";

    static {
        File file=new File(AutoGenerate.class.getClassLoader().getResource(StringUtils.EMPTY).getPath());
        ROOT_PATH=file.getParentFile().getParentFile().getParentFile().getAbsolutePath();
    }

    //表前缀
    private static final String[] TABLE_PREFIX=  new String[]{"parking_"};


    //数据库访问url
    private static final String URL=
            "jdbc:mysql://10.10.0.130:3306/parking_system?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false";
    //数据库连接驱动
    private static final String DRIVER_NAME="com.mysql.cj.jdbc.Driver";
    //用户名
    private static final String USER_NAME="root";
    //密码
    private static final String PASSWORD="root";


    public static void main(String[] args){
        codeGenerator();
    }

    private static void codeGenerator(){
        AutoGenerator generator=new AutoGenerator();
        generator.setDataSource(createDataSource());
        generator.setGlobalConfig(createGlobalConfig());
        generator.setPackageInfo(createPackageConfig());
        generator.setStrategy(createStrategyConfig());
        generator.setCfg(createCustomConfig());

        TemplateConfig templateConfig=new TemplateConfig();
        templateConfig.setEntity(null);
        templateConfig.setController(null);
        templateConfig.setMapper(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setService(null);
        templateConfig.setXml(null);

        generator.setTemplate(templateConfig);
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }

    /**
     * 设置数据源
     * @return
     */
    private static DataSourceConfig createDataSource(){
        DataSourceConfig dsConfig=new DataSourceConfig();
        dsConfig.setDbQuery(new MySqlQuery());
        dsConfig.setDbType(DbType.MYSQL);
        dsConfig.setTypeConvert(new MySqlTypeConvert());
        //数据库访问url
        dsConfig.setUrl(URL);
        //数据库访问驱动
        dsConfig.setDriverName(DRIVER_NAME);
        //数据库访问用户名
        dsConfig.setUsername(USER_NAME);
        //数据库访问密码
        dsConfig.setPassword(PASSWORD);
        return dsConfig;
    }

    /**
     * 代码生成基础配置
     * @return
     */
    private static GlobalConfig createGlobalConfig(){
        GlobalConfig globalConfig=new GlobalConfig();
        globalConfig.setFileOverride(false);
        globalConfig.setOpen(false);
        globalConfig.setEnableCache(false);
        globalConfig.setAuthor("lp");
        globalConfig.setSwagger2(true);
        globalConfig.setBaseResultMap(true);
        globalConfig.setDateType(DateType.TIME_PACK);
        globalConfig.setIdType(IdType.ID_WORKER_STR);
        return globalConfig;
    }

    /**
     * 包相关配置
     * @return
     */
    private static PackageConfig createPackageConfig(){
        PackageConfig packageConfig=new PackageConfig();
        return packageConfig;
    }

    /**
     * 数据库表相关配置
     * @return
     */
    private static StrategyConfig createStrategyConfig(){
        StrategyConfig config=new StrategyConfig();
        config.setCapitalMode(false);
        config.setSkipView(true);
        config.setTablePrefix(TABLE_PREFIX);
        config.setNaming(NamingStrategy.underline_to_camel);
        config.setColumnNaming(NamingStrategy.underline_to_camel);
        config.setEntityLombokModel(true);
        config.setRestControllerStyle(true);
        config.setEntitySerialVersionUID(true);
        //config.setInclude(TABLES);
        config.setEntityTableFieldAnnotationEnable(true);
        config.setControllerMappingHyphenStyle(false);
        config.setEntityColumnConstant(false);
        config.setSuperEntityColumns(new String[]{"id","create_date",
                "update_date","create_user_id","update_user_id","is_delete"});
        return config;
    }

    /**
     * 自定义配置
     * @return
     */
    private static InjectionConfig createCustomConfig(){
        InjectionConfig config= new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String,Object> objMap=new HashMap<String, Object>();
                objMap.put("dataBase",DATABASE);
                objMap.put("companyName",COMPANY_NAME);
                this.setMap(objMap);
            }
        };

        setRepeatCreateCfg(config);
        List<FileOutConfig> fileOutConfigs=new ArrayList<FileOutConfig>();

        fileOutConfigs.add(entityBaseConfig());
        fileOutConfigs.add(entityConfig());
        fileOutConfigs.add(entityDoConfig());
        fileOutConfigs.add(entityDtoConfig());
        fileOutConfigs.add(entityInfoConfig());
        fileOutConfigs.add(entityBaseDtoConfig());
        fileOutConfigs.add(entityBaseInfoConfig());
        fileOutConfigs.add(mapperConfig());
        fileOutConfigs.add(mapperWrapperConfig());
        fileOutConfigs.add(mapperWrapperImplConfig());
        fileOutConfigs.add(mapperXmlConfig());
        fileOutConfigs.add(serviceConfig());
        fileOutConfigs.add(serviceImplConfig());
        fileOutConfigs.add(wrapperConfig());
        fileOutConfigs.add(wrapperImplConfig());
        fileOutConfigs.add(controllerConfig());

        config.setFileOutConfigList(fileOutConfigs);
        return config;
    }

    /**
     * 设置重复生成策略
     * @param cfg 自定义配置
     */
    private static void setRepeatCreateCfg(InjectionConfig cfg){

    }

    /**
     * 实体自定义配置
     * @return
     */
    private static FileOutConfig entityConfig(){
        return new FileOutConfig(ENTITY_TEMP){
            @Override
            public String outputFile(TableInfo tableInfo) {
                MODULE_NAME=StringUtils.split(tableInfo.getName(), "_")[1];
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(ENTITY_PATH).append(MODULE_NAME);
                path.append("/").append(tableInfo.getEntityName())
                        .append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * 实体自定义配置
     * @return
     */
    private static FileOutConfig entityDoConfig(){
        return new FileOutConfig(ENTITY_DO_TEMP){
            @Override
            public String outputFile(TableInfo tableInfo) {
                MODULE_NAME=StringUtils.split(tableInfo.getName(), "_")[1];
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(ENTITY_DO_PATH).append(MODULE_NAME);
                path.append("/domain/").append(tableInfo.getEntityName());
                path.append("Do").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * 实体自定义配置
     * @return 实体类配置
     */
    private static FileOutConfig entityDtoConfig(){
        return new FileOutConfig(ENTITY_DTO_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MODULE_NAME=StringUtils.split(tableInfo.getName(), "_")[1];
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(ENTITY_DTO_PATH).append(MODULE_NAME);
                path.append("/dto/").append(tableInfo.getEntityName());
                path.append("Dto").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * 实体自定义配置
     * @return 实体类配置
     */
    private static FileOutConfig entityInfoConfig(){
        return new FileOutConfig(ENTITY_INFO_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MODULE_NAME=StringUtils.split(tableInfo.getName(), "_")[1];
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(ENTITY_INFO_PATH).append(MODULE_NAME);
                path.append("/info/").append(tableInfo.getEntityName());
                path.append("Info").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * 实体自定义配置
     * @return 实体类配置
     */
    private static FileOutConfig entityBaseConfig(){
        return new FileOutConfig(ENTITY_BASE_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MODULE_NAME="base";
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(ENTITY_BASE_PATH).append(MODULE_NAME);
                path.append("/ParkingBase").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * 实体类自定义配置
     * @return 实体类配置
     */
    private static FileOutConfig entityBaseDtoConfig(){
        return new FileOutConfig(ENTITY_DTO_BASE_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MODULE_NAME=StringUtils.split(tableInfo.getName(), "_")[1];
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(ENTITY_DTO_BASE_PATH).append(MODULE_NAME);
                path.append("/dto/base/").append(tableInfo.getEntityName());
                path.append("BaseDto").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * 实体自定义配置
     * @return
     */
    private static FileOutConfig entityBaseInfoConfig(){
        return new FileOutConfig(ENTITY_INFO_BASE_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MODULE_NAME=StringUtils.split(tableInfo.getName(), "_")[1];
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(ENTITY_INFO_BASE_PATH).append(MODULE_NAME);
                path.append("/info/base/").append(tableInfo.getEntityName());
                path.append("BaseInfo").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * 实体自定义配置
     * @return
     */
    private static FileOutConfig mapperConfig(){
        return new FileOutConfig(MAPPER_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MODULE_NAME=StringUtils.split(tableInfo.getName(), "_")[1];
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(MAPPER_PATH).append(MODULE_NAME);
                path.append("/mapper/api/I").append(tableInfo.getEntityName());
                path.append("Mapper").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * 实体自定义配置
     * @return
     */
    private static FileOutConfig mapperWrapperConfig(){
        return new FileOutConfig(MAPPER_WRAPPER_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MODULE_NAME=StringUtils.split(tableInfo.getName(), "_")[1];
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(MAPPER_WRAPPER_PATH).append(MODULE_NAME);
                path.append("/wrapper/api/I").append(tableInfo.getEntityName());
                path.append("MapperWrapper").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * 实体自定义配置
     */
    private static FileOutConfig mapperWrapperImplConfig(){
        return new FileOutConfig(MAPPER_WRAPPER_IMPL_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MODULE_NAME=StringUtils.split(tableInfo.getName(), "_")[1];
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(MAPPER_WRAPPER_IMPL_PATH).append(MODULE_NAME);
                path.append("/wrapper/impl/").append(tableInfo.getEntityName());
                path.append("MapperWrapperImpl").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * 实体自定义配置
     * @return 实体类配置
     */
    private static FileOutConfig mapperXmlConfig(){
        return new FileOutConfig(MAPPER_XML_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(MAPPER_XML_PATH);
                path.append("/I").append(tableInfo.getEntityName());
                path.append("Mapper.xml");
                return path.toString();
            }
        };
    }

    /**
     * service层配置
     * @return service层配置
     */
    private static FileOutConfig serviceConfig(){
        return new FileOutConfig(SERVICE_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(SERVICE_PATH).append(MODULE_NAME);
                path.append("/service/api/I").append(tableInfo.getEntityName());
                path.append("Service").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     *
     * @return
     */
    private static FileOutConfig serviceImplConfig(){
        return new FileOutConfig(SERVICE_IMPL_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(SERVICE_IMPL_PATH).append(MODULE_NAME);
                path.append("/service/impl/").append(tableInfo.getEntityName());
                path.append("ServiceImpl").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * service层配置
     * @return service层配置
     */
    private static FileOutConfig wrapperConfig(){
        return new FileOutConfig(WRAPPER_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(WRAPPER_PATH).append(MODULE_NAME);
                path.append("/wrapper/api/I").append(tableInfo.getEntityName());
                path.append("Wrapper").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * service层配置
     * @return
     */
    private static FileOutConfig wrapperImplConfig(){
        return new FileOutConfig(WRAPPER_IMPL_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(WRAPPER_IMPL_PATH).append(MODULE_NAME);
                path.append("/wrapper/impl/").append(tableInfo.getEntityName());
                path.append("WrapperImpl").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }

    /**
     * controller层配置
     * @return
     */
    private static FileOutConfig controllerConfig(){
        return new FileOutConfig(CONTROLLER_TEMP) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                StringBuilder path=new StringBuilder(ROOT_PATH);
                path.append(CONTROLLER_PATH).append(MODULE_NAME);
                path.append("/controller/").append(tableInfo.getEntityName());
                path.append("Controller").append(StringPool.DOT_JAVA);
                return path.toString();
            }
        };
    }
}
