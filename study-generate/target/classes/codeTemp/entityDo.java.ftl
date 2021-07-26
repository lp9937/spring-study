<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.domain;
        <#break>
    </#if>
</#list>
import com.${cfg.companyName}.${cfg.dataBase}.dto.base.domain.${cfg.dataBase?cap_first}BaseDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Blob;

/**
* @author :${author}
* @date :Created in ${date}
* @description :${table.entityName}领域层模型，该层对象仅在service层中使用
* @version :1.0
*/

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class ${table.entityName}Do extends ${cfg.dataBase?cap_first}BaseDo{
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
    /**
    * ${field.comment?replace("\r\n","")}
    */
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
}