<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.biz.${item}.service.impl;

import com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.domain.${entity}Do;
import com.${cfg.companyName}.${cfg.dataBase}.entity.${item}.${entity};
import com.${cfg.companyName}.${cfg.dataBase}.biz.${item}.service.api.I${entity}Service;
        <#break>
    </#if>
</#list>

import com.${cfg.companyName}.${cfg.dataBase}.biz.base.service.impl.${cfg.dataBase?cap_first}BaseServiceImpl;
import org.springframework.stereotype.Service;
/**
* @author :${author}
* @date :Created in ${date}
* @description :${table.comment!}service层实现
* @version :1.0
*/
@Service("${entity?uncap_first}Service")
public class ${entity}ServiceImpl extends ${cfg.dataBase?cap_first}BaseServiceImpl<${entity}Do,${entity}>
    implements I${entity}Service{

}