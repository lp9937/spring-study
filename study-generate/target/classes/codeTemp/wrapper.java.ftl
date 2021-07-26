<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.biz.${item}.wrapper.api;

import com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.dto.${entity}Dto;
import com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.info.${entity}Info;
        <#break>
    </#if>
</#list>

import com.${cfg.companyName}.${cfg.dataBase}.biz.base.wrapper.api.I${cfg.dataBase?cap_first}BaseWrapper;
/**
* @author :${author}
* @date :Created in ${date}
* @description :${table.comment!}Wrapper接口，继承base接口是为了能够使用公共方法，当然也可以不继承base接口，不继承就不能在实现中使用到公共方法
* @version :1.0
*/

public interface I${entity}Wrapper extends I${cfg.dataBase?cap_first}BaseWrapper<${entity}Info,${entity}Dto>{

}