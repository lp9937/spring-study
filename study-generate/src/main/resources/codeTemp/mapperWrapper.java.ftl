<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.mapper.${item}.wrapper.api;
import com.${cfg.companyName}.${cfg.dataBase}.entity.${item}.${entity};
        <#break>
    </#if>
</#list>

import com.${cfg.companyName}.${cfg.dataBase}.mapper.base.wrapper.api.I${cfg.dataBase?cap_first}MapperWrapper;

/**
* @author :${author}
* @date :Created in ${date}
* @description :${table.comment!} mapper封装，提供扩展功能
* @version :1.0
*/

public interface I${entity}MapperWrapper extends I${cfg.dataBase?cap_first}MapperWrapper<${entity}>{
}