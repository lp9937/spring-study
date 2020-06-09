<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.biz.${item}.wrapper.impl;

import com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.domain.${entity}Do;
import com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.dto.${entity}Dto;
import com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.info.${entity}Info;
import com.${cfg.companyName}.${cfg.dataBase}.biz.${item}.wrapper.api.I${entity}Wrapper;
        <#break>
    </#if>
</#list>

import com.${cfg.companyName}.${cfg.dataBase}.biz.base.wrapper.impl.${cfg.dataBase?cap_first}BaseWrapperImpl;
import org.springframework.stereotype.Service;

/**
* @author :${author}
* @date :Created in ${date}
* @description :${table.comment!}wrapper实现
* @version :1.0
*/

public class ${entity}WrapperImpl extends ${cfg.dataBase?cap_first}BaseWrapperImpl<${entity}Info,${entity}Dto,${entity}Do>
    implements I${entity}Wrapper{

}