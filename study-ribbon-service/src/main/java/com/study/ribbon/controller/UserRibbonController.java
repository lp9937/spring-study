package com.study.ribbon.controller;

import cn.hutool.system.UserInfo;
import common.api.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author lp
 * @description TODO
 * @date 2021/10/9 14:37
 *
 * BestAvailableRule:选择并发请求最小的Server
 * AvailabilityFilteringRule:过滤掉那些因为连接失败被标记为
 *                           Circuit tripped的后端Server,
 *                           并过滤掉那些高并发的后端Server
 * WeightedResponseTimeRule:根据响应时间分配一个Weight,响应
 *                          时间越长,Weight越小,被选中的可能性越低
 * RetryRule:在一个配置时间段内,当选择Server不成功时,则在配置的负载均
 *           衡策略上进行重试
 * RoundRobinRule:轮询选择Server
 * RandomRule:随机选择一个Server
 * ZoneAvoidanceRule:先判断Zone的运行性能,剔除不可用的Zone,再过滤掉可
 *                   用Zone下因为连接失败被标记为Circuit tripped和高
 *                   并发的后端Server
 *
 * 
 * RandomRule：从提供服务的实例中以随机的方式；
 * RoundRobinRule：以线性轮询的方式，就是维护一个计数器，
 *                 从提供服务的实例中按顺序选取，第一次选第一个，
 *                 第二次选第二个，以此类推，到最后一个以后再从头来过；
 * RetryRule：在RoundRobinRule的基础上添加重试机制，
 *            即在指定的重试时间内，反复使用线性轮询策略来选择可用实例；
 * WeightedResponseTimeRule：对RoundRobinRule的扩展，响应速度越快的
 *                           实例选择权重越大，越容易被选择；
 * BestAvailableRule：选择并发较小的实例；
 * AvailabilityFilteringRule：先过滤掉故障实例，再选择并发较小的实例；
 * ZoneAwareLoadBalancer：采用双重过滤，同时过滤不是同一区域的实例和故障实例，
 *                        选择并发较小的实例。
 */
@RestController
@RequestMapping("/user/")
public class UserRibbonController {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${service-url.user-service}")
    private String userServiceUrl;
    @PostMapping("create")
    public ResponseResult<String> create(@RequestBody UserInfo info){
        return restTemplate.postForObject(userServiceUrl+"/user/create",info,ResponseResult.class);
    }
}
