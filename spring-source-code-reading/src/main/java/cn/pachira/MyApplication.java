package cn.pachira;

import cn.pachira.config.MyConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author dingyehang
 * @date 2022/7/7 15:27
 **/
@Configuration
@ComponentScan(value = "cn.pachira")
public class MyApplication {

	@Bean(value = "myConfig")
	public MyConfig getConfig(){
		return new MyConfig();
	}
}
