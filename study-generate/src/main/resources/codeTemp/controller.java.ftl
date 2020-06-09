<#list table.name?split("_") as item>
    <#if (item_index==1)>
package com.${cfg.companyName}.${cfg.dataBase}.web.${item}.controller;

import com.${cfg.companyName}.${cfg.dataBase}.biz.${item}.wrapper.api.I${entity}Wrapper;
import com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.info.${entity}Info;
import com.${cfg.companyName}.${cfg.dataBase}.dto.${item}.dto.${entity}Dto;
    <#break>
    </#if>
</#list>

import com.${cfg.companyName}.${cfg.dataBase}.dto.base.response.${cfg.dataBase}BaseResponse;
import com.${cfg.companyName}.${cfg.dataBase}.web.base.controller.${cfg.dataBase}BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author :${author}
* @date :Created in ${date}
<#if table.comment!?length gt 0>
* @description :${table.comment!} controller
<#else>
* @description :${entity} controller实现
</#if>
* @version :1.0
*/
<#if table.comment!?length gt 0>
@Api(tags="${table.comment!}控制器")
<#else>
@Api(tags="${entity}控制器")
</#if>
@RestController
<#assign str="">
<#list table.name?split("_") as item>
    <#if (item_index!=0)>
        <#assign str=str+"/"+item/>
    </#if>
</#list>
@RequestMapping("${str}")
@Slf4j
public class ${entity}Controller extends ${cfg.dataBase?cap_first}BaseController<${entity}Info,${entity}Dto>{

    @Autowired
    private I${entity}Wrapper ${entity?uncap_first}Wrapper;
}
