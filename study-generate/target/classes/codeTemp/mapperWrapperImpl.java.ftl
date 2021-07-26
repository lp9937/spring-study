<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.mapper.${item}.wrapper.impl;

import com.${cfg.companyName}.${cfg.dataBase}.mapper.${item}.mapper.api.I${entity}Mapper;
import com.${cfg.companyName}.${cfg.dataBase}.mapper.${item}.wrapper.api.I${entity}MapperWrapper;
import com.${cfg.companyName}.${cfg.dataBase}.entity.${item}.${entity};
        <#break>
    </#if>
</#list>

import com.${cfg.companyName}.${cfg.dataBase}.mapper.base.wrapper.impl.${cfg.dataBase?cap_first}MapperWrapperImpl;
import org.springframework.stereotype.Service;

/**
* @author :${author}
* @date :Created in ${date}
* @description :${table.comment!} mapper封装实现，提供功能增强
* @version :1.0
*/

@Service("${entity?uncap_first}MapperWrapper")
public class ${entity}MapperWrapperImpl extends ${cfg.dataBase?cap_first}MapperWrapperImpl<I${entity}Mapper,${entity}>
    implements I${entity}MapperWrapper{

}