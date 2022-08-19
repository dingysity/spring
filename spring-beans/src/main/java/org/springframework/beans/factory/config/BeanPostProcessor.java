/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

/**
 * Bean的后置处理器
 * 允许自定义修改新 bean 实例的工厂挂钩 - 例如，检查标记接口或使用代理包装 bean。
 * <p>
 * 通常，通过标记接口等填充 bean 的后处理器将实现 postProcessBeforeInitialization ，
 * 而使用代理包装 bean 的后处理器通常会实现 postProcessAfterInitialization 。
 * ApplicationContext 可以在其 bean 定义中自动检测 BeanPostProcessor bean，
 * 并将这些后处理器应用于随后创建的任何 bean。
 * 一个普通的 BeanFactory 允许以编程方式注册后处理器，
 * 将它们应用于通过 bean 工厂创建的所有 bean。
 * <p>
 * Factory hook that allows for custom modification of new bean instances &mdash;
 * for example, checking for marker interfaces or wrapping beans with proxies.
 *
 * <p>Typically, post-processors that populate beans via marker interfaces
 * or the like will implement {@link #postProcessBeforeInitialization},
 * while post-processors that wrap beans with proxies will normally
 * implement {@link #postProcessAfterInitialization}.
 *
 * <h3>Registration</h3>
 * <p>An {@code ApplicationContext} can autodetect {@code BeanPostProcessor} beans
 * in its bean definitions and apply those post-processors to any beans subsequently
 * created. A plain {@code BeanFactory} allows for programmatic registration of
 * post-processors, applying them to all beans created through the bean factory.
 *
 *
 * <h3>Ordering</h3>
 * <p>{@code BeanPostProcessor} beans that are autodetected in an
 * {@code ApplicationContext} will be ordered according to
 * {@link org.springframework.core.PriorityOrdered} and
 * {@link org.springframework.core.Ordered} semantics. In contrast,
 * {@code BeanPostProcessor} beans that are registered programmatically with a
 * {@code BeanFactory} will be applied in the order of registration; any ordering
 * semantics expressed through implementing the
 * {@code PriorityOrdered} or {@code Ordered} interface will be ignored for
 * programmatically registered post-processors. Furthermore, the
 * {@link org.springframework.core.annotation.Order @Order} annotation is not
 * taken into account for {@code BeanPostProcessor} beans.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @see InstantiationAwareBeanPostProcessor
 * @see DestructionAwareBeanPostProcessor
 * @see ConfigurableBeanFactory#addBeanPostProcessor
 * @see BeanFactoryPostProcessor
 * @since 10.10.2003
 */

/*
	使用方式：第一种：实现 BeanPostProcessor 接口，然后将此类注册到 Spring 即可；
			第二种是通过ConfigurableBeanFactory 的 addBeanPostProcessor 方法进行注册。
	BeanPostProcess 可以有多个，并且可以通过设置 order 属性来控制这些 BeanPostProcessor 实例的执行顺序。
	仅当 BeanPostProcessor 实现 Ordered 接口时,才能设置此属性，或者 PriorityOrdered 接口。

	如果某个类实现了 BeanPostProcessor 则它会在 AbstractApplicationContext 中的 registerBeanPostProcessors(beanFactory)方法中创建 bean
	而不是和普通的 bean 一样在 finishBeanFactoryInitialization(beanFactory)中才被创建。


	允许自定义修改新 bean 实例的工厂挂钩 - 例如，检查标记接口或使用代理包装 bean。
	通常，通过标记接口或类似方式填充 bean 的后处理器将实现 postProcessBeforeInitialization，
	而用代理包装 bean 的后处理器通常会实现 postProcessAfterInitialization。
	ApplicationContext 可以在其 bean 定义中自动检测 BeanPostProcessor bean，并将这些后处理器应用于随后创建的任何 bean。
	普通的 BeanFactory 允许对后处理器进行编程注册，并将它们应用于通过 bean 工厂创建的所有 bean。
	在 ApplicationContext 中自动检测到的 BeanPostProcessor bean 将根据 PriorityOrded 和 Orded 语义进行排序。
	相比之下，在 BeanFactory 中以编程方式注册的 BeanPostProcessor bean 将按照注册顺序应用;
	对于以编程方式注册的后处理程序，通过实现 PriorityOrded 或 Orded 接口表达的任何排序语义都将被忽略。
	此外，BeanPostProcessor bean 没有考虑@Order 注释。
 */

public interface BeanPostProcessor {

	/**
	 * Apply this {@code BeanPostProcessor} to the given new bean instance <i>before</i> any bean
	 * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
	 * or a custom init-method). The bean will already be populated with property values.
	 * The returned bean instance may be a wrapper around the original.
	 * <p>The default implementation returns the given {@code bean} as-is.
	 *
	 * @param bean     the new bean instance
	 * @param beanName the name of the bean
	 * @return the bean instance to use, either the original or a wrapped one;
	 * if {@code null}, no subsequent BeanPostProcessors will be invoked
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
	 */


	/*
     * 实例化、依赖注入完毕，
     * 在调用显示的初始化之前完成一些定制的初始化任务
     */
	@Nullable
	default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/**
	 * Apply this {@code BeanPostProcessor} to the given new bean instance <i>after</i> any bean
	 * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
	 * or a custom init-method). The bean will already be populated with property values.
	 * The returned bean instance may be a wrapper around the original.
	 * <p>In case of a FactoryBean, this callback will be invoked for both the FactoryBean
	 * instance and the objects created by the FactoryBean (as of Spring 2.0). The
	 * post-processor can decide whether to apply to either the FactoryBean or created
	 * objects or both through corresponding {@code bean instanceof FactoryBean} checks.
	 * <p>This callback will also be invoked after a short-circuiting triggered by a
	 * {@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation} method,
	 * in contrast to all other {@code BeanPostProcessor} callbacks.
	 * <p>The default implementation returns the given {@code bean} as-is.
	 *
	 * @param bean     the new bean instance
	 * @param beanName the name of the bean
	 * @return the bean instance to use, either the original or a wrapped one;
	 * if {@code null}, no subsequent BeanPostProcessors will be invoked
	 * @throws org.springframework.beans.BeansException in case of errors
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
	 * @see org.springframework.beans.factory.FactoryBean
	 */


	// 实例化、依赖注入、初始化完毕时执行
	@Nullable
	default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
