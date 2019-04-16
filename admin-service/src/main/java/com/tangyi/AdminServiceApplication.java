package com.tangyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableZuulProxy
//@EnableCaching
@EnableCircuitBreaker
public class AdminServiceApplication {


    /**
     * 使用fastJson进行json解析
     *
     * @return
     */
	/*@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		//1.定义HttpMessageConverter
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		//2.定义JsonConfig
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		//3.配置JsonConfig
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		//4.将JsonConfig注册到HttpMessageConverter
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
		//5.返回HttpMessageConverter
		return new HttpMessageConverters(converter);
	}*/
    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }

}
