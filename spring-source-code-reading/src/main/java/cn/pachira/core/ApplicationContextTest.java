package cn.pachira.core;

import cn.pachira.MyApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author dingyehang
 * @date 2022/7/7 15:30
 **/
public class ApplicationContextTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyApplication.class);
		final String[] beanDefinitionNames = context.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println(beanDefinitionName);
		}
	}
}
