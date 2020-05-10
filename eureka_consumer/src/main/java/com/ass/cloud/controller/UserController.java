package com.ass.cloud.controller;

import com.ass.cloud.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

    /**
     * ribbon负载均衡器,其中记录了从Eureka Server中获取的所有服务信息。
     * 这些服务的信息是IP和端口等。应用名称,域名,主机名等信息。
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 通过Http协议发起远程服务调用,实现一个远程的服务消费
     * @return
     */
    @GetMapping("/get")
    public User getUser(){
        /**
         * 通过spring应用命名,获取服务实例ServiceInstance对象
         * ServiceInstance封装了服务的基本信息,如IP,端口
         * 在Eureka中对所有注册到Eureka Server中的服务都称为一个service instance服务实例
         * 一个服务实例,就是一个有效的,可用的,provider单体实例或集群实例。
         * 每个service instance都和spring application name对应
         * 可以通过spring application name查询service instance
         */
        ServiceInstance eurekaProvider = this.loadBalancerClient.choose("eureka_provider");
        //拼接访问服务的URL
        StringBuilder stringBuilder = new StringBuilder();
        //http://localhost:8081/get/1
        stringBuilder.append("http://").append(eurekaProvider.getHost()).append(":")
                .append(eurekaProvider.getPort()).append("/get/1");
        System.out.println("本次访问的service是:"+stringBuilder.toString());
        /**
         * 创建一个响应类型模板。
         * 就是REST请求的响应体中的数据类型。
         * ParameterizedTypeReference-代表REST请求的响应体中的数据类型
         */
        ParameterizedTypeReference<User> reference = new ParameterizedTypeReference<User>(){};
        ResponseEntity<User> response =
                restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET,null,reference);
        User user = response.getBody();
        return user;
    }
}
