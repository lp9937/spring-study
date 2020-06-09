<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.dto;
import com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.dto.base.${table.entityName}BaseDto;
        <#break>
    </#if>
</#list>

import com.${cfg.companyName}.${cfg.dataBase}.dto.base.dto.${cfg.dataBase?cap_first}BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
* @description :${table.entityName}传出对象
* @version :1.0
*/

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ApiModel(value="${table.entityName} dto",description="${table.entityName} - ${table.comment!}")
public class ${table.entityName}Dto extends ${table.entityName}BaseDto{
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
    /**
    * ${field.comment?replace("\r\n","")}
    */
    </#if>

    <#if swagger2>
    @ApiModelProperty(value="${field.comment?replace("\r\n","")}")
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
}