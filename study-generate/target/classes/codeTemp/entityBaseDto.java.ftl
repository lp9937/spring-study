<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.dto.base;
        <#break>
    </#if>
</#list>

import com.${cfg.companyName}.${cfg.dataBase}.dto.base.dto.${cfg.dataBase?cap_first}BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* @author :${author}
* @date :Created in ${date}
* @description :${table.entityName}基础传出对象
* @version :1.0
*/
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@ApiModel(value="${table.entityName} dto",description="${table.entityName} - ${table.comment!}")
public class ${table.entityName}BaseDto extends ${cfg.dataBase?cap_first}BaseDto{

}