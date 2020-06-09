package com.${cfg.companyName}.${cfg.dataBase}.entity.base;

<#list table.importPackages as package>
import ${package};
</#list>

<#if entityLombokModel>
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
</#if>

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Blob;

/**
* @author :${author}
* @date :Created in ${date}
* @description :持久层系统基础entity,其它entity继承该基础entity,entity用于数据持久化
* @version :1.0
*/

@Data
@NoArgsConstructor
@Accessors(chain=true)
public class ${cfg.dataBase?cap_first}Base implements Serializable{
    <#if entitySerialVersionUID>
    private static final long serialVersionUID=1L;
    </#if>

    <#list table.commonFields as field>
        <#if field.comment!?length gt 0>
    /**
    * ${field.comment?replace("\r\n","")}
    */
        </#if>
        <#--主键-->
        <#if field.keyFlag>
            <#if field.keyIdentityFlag>
    @TableId(value="${field.name}")
            <#elseif idType??>
    @TableId(value="${field.name}",type=IdType.${idType})
            <#elseif field.convert>
    @TableId(${field.name})
            </#if>
        <#--普通字段-->
        <#elseif field.fill??>
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
}