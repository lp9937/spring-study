<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.entity.${item};
        <#break>
    </#if>
</#list>
<#list table.importPackages as package>
import ${package};
</#list>
<#if entityLombokModel>
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.${cfg.companyName}.${cfg.dataBase}.entity.base.${cfg.dataBase?cap_first}Base;
</#if>
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Blob;

/**
* @author :${author}
* @date :Created in ${date}
* @description :${table.comment!}
* @version :1.0
*/
@Data
@NoArgsConstructor
@Accessors(chain=true)
<#if table.convert>
@TableName("${table.name}")
</#if>
public class ${entity} extends ${cfg.dataBase?cap_first}Base{
<#--------------BEGIN字段循环遍历------------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.comment!?length gt 0>
    /**
    * ${field.comment?replace("\r\n","")}
    */
    </#if>
    <#if field.keyFlag>
        <#--主键-->
        <#if field.keyIdentityFlag>
    @TableId(value="${field.name}")
        <#elseif idType??>
    @TableId(value="${field.name}",type=IdType.${idType})
        <#elseif field.convert>
    @TableId("${field.name}")
        </#if>

        <#--普通字段-->
    <#elseif field.fill??>
    <#-------存在字段填充设置------->
        <#if field.convert>
    @TableField(value="${field.name}",fill=FieldFill.${field.fill})
        <#else>
    @TableField(fill=FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
    @TableField("${field.name}")
    </#if>
    <#--乐观锁注解-->
    <#if (versionFieldName!"")==field.name>
    @TableLogic
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#----------------END 字段循环遍历-------------->
<#if !entityLombokModel>
    <#list table.field as field>
        <#if field.properType=="boolean">
            <#assign getPrefix="is"/>
        <#else>
            <#assign getPrefix="get"/>
        </#if>
    public ${field.propertyType} ${getPrefix}${field.capitalName}(){
        return ${field.propertyName};
    }

    <#if entityBuilderModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}){
    <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}){
    </#if>
        this.${field.propertyName}=${field.propertyName};
        <#if entityBuilderModel>
        return this;
        </#if>
    }
    </#list>
</#if>

<#if entityColumnConstant>
    <#list table.fields as field>
    public static final String ${field.name?upper_case}="${field.name}";
    </#list>
</#if>
<#if activeRecord>
    @Override
    protected Serializable pkVal(){
    <#if keyPropertyName??>
        return this.${keyPropertyName};
    <#else>
        return null;
    </#if>
    }
</#if>
<#if !entityLombokModel>
    @Override
    public String toString(){
        return "${entity}{"+
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}="+${field.propertyName}+
        <#else>
            ",${field.propertyName}="+${field.propertyName}+
        </#if>
    </#list>
        "}";
    }
</#if>
}
