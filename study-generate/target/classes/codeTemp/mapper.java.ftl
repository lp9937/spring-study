<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.mapper.${item}.mapper.api;
        <#break>
    </#if>
</#list>

import com.${cfg.companyName}.${cfg.dataBase}.mapper.base.mapper.api.I${cfg.dataBase?cap_first}Mapper;
<#list table.name?split("_") as item>
    <#if (item_index==1)>
import com.${cfg.companyName}.${cfg.dataBase}.entity.${item}.${entity};
    </#if>
</#list>

/**
* @author :${author}
* @date :Created in ${date}
* @description :${table.comment!} Mapper 接口
* @version :1.0
*/
public interface I${entity}Mapper extends I${cfg.dataBase?cap_first}Mapper<${entity}>{

}