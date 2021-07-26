<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.biz.${item}.service.api;
import com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.domain.${entity}Do;
        <#break>
    </#if>
</#list>
import com.${cfg.companyName}.${cfg.dataBase}.biz.base.service.api.I${cfg.dataBase?cap_first}BaseService;

/**
* @author :{author}
* @date :Created in ${date}
* @description :${table.comment!}service接口，继承base接口是为了能使用公共方法，当然也可以不继承base接口，不继承就不能在实现中使用到公共方法
* @version :1.0
*/
public interface I${entity}Service extends I${cfg.dataBase?cap_first}BaseService<${entity}Do>{

}