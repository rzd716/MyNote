####1. Spring概述
#####1. 什么是Spring
Spring 是个java企业级应用的开源开发框架
#####2. Spring简化Java开发
 1. 基于POJO的轻量级和**最小侵入性编程**
 2. 通过依赖注入和面向接口实现**松耦合**
 3. 基于切面和惯例进行**声明式编程**
 4. 通过切面和模板**减少样板式代码**
#####3. 使用Spring框架的好处
 1. 轻量：Spring 是轻量的，基本的版本大约2MB
 2. 控制反转
 3. 面向切面编程
 4. 容器
 5. MVC框架
 6. 事务管理
 7. 异常处理
#####4. Spring由哪些模块组成
 1. 测试：Test
 2. 核心容器：Beans、Context、Core、Expression、Context support
 3. 面向切面编程：AOP、Aspects
 4. Instrumentation
 5. 数据访问与集成：JDBC、Transaction、ORM、JMS
 6. Web与远程调用：Web、Web servlet、WebSocket
#####5. BeanFactory和ApplicationContext区别
 1. BeanFactory是Spring框架的基础设施，面向Spring本身  
   ApplicationContext面向Spring框架的使用者，开发中基本都在使用ApplicationContext, web项目使用WebApplicationContext ，很少用到BeanFactory
 2. BeanFactory采取延迟加载，第一次getBean时才会初始化Bean, ApplicationContext是会在加载配置文件时初始化Bean
 3. ApplicationContext是对BeanFactory扩展，它可以进行国际化处理、事件传递和bean自动装配以及各种不同应用层的Context实现
####2. 依赖注入
#####1. 什么是依赖注入
#####2. 有哪些注入方式
 1. 构造注入
 2. 设值注入
 3. 接口注入 （Spring不支持）
####3. Spring Bean
#####1. Spring配置bean实例化
 1. 使用类构造器实例化(默认无参数)  
 ```
 <bean id="bean1" class="cn.itcast.spring.b_instance.Bean1"></bean>
 ```
 2. 使用静态工厂方法实例化(简单工厂模式)  
  ```
  <bean id="bean2" class="cn.itcast.spring.b_instance.Bean2Factory" factory-method="getBean2"></bean>
  ```
 3. 使用实例工厂方法实例化(工厂方法模式)  
  ```
  <bean id="bean3Factory" class="cn.itcast.spring.b_instance.Bean3Factory"></bean>
  <bean id="bean3" factory-bean="bean3Factory" factory-method="getBean3"></bean>
  
  ```
#####2. BeanFactory中Bean的生命周期
 1. Bean的完整生命周期是从Spring容器着手实例化Bean开始，直到最终销毁Bean
 2. 期间调用大量方法，分为4类：  
    1. Bean自身的方法：如调用Bean自身构造函数、设值函数（Setter）以及通过<bean>的int-method和destroy-method所指定的方法
    2. Bean级生命周期接口方法：BeanNameAware、 BeanFactoryAware、InitializingBean、DisposableBean。这些接口方法由Bean类直接实现
    3. 容器级生命周期接口方法: BeanPostProcessor、InstantiationAwareBeanPostProcessor。独立于Bean。
    4. 工厂后处理器接口方法  
#####3. ApplicationContext中Bean的生命周期
 1. 相比BeanFactory增加了ApplicationContextAware接口方法
 2. 大致过程：  
    1. Spring对bean进行实例化；  
    2. Spring将值和bean的引用注入到bean对应的属性中；  
    3. 如果bean实现了BeanNameAware接口，Spring将bean的ID传递给
 setBean-Name()方法；  
    4. 如果bean实现了BeanFactoryAware接口，Spring将调
 用setBeanFactory()方法，将BeanFactory容器实例传入；  
    5. 如果bean实现了ApplicationContextAware接口，Spring将调
 用setApplicationContext()方法，将bean所在的应用上下文的
 引用传入进来；  
    6. 如果bean实现了BeanPostProcessor接口，Spring将调用它们
 的post-ProcessBeforeInitialization()方法；  
    7. 如果bean实现了InitializingBean接口，Spring将调用它们的
 after-PropertiesSet()方法。类似地，如果bean使用init-
 method声明了初始化方法，该方法也会被调用；  
    8. 如果bean实现了BeanPostProcessor接口，Spring将调用它们
 的post-ProcessAfterInitialization()方法；  
    9. 此时，bean已经准备就绪，可以被应用程序使用了，它们将一直
 驻留在应用上下文中，直到该应用上下文被销毁；  
    10. 如果bean实现了DisposableBean接口，Spring将调用它的
 destroy()接口方法。同样，如果bean使用destroy-method声明
 了销毁方法，该方法也会被调用
#####4. Spring框架下bean的作用域
 1. singleton : bean在每个Spring ioc 容器中只有一个实例。
 2. prototype：一个bean的定义可以有多个实例。
 3. request：每次http请求都会创建一个bean，该作用域仅在基于web的Spring ApplicationContext情形下有效。
 4. session：在一个HTTP Session中，一个bean定义对应一个实例。该作用域仅在基于web的Spring ApplicationContext情形下有效。
 5. global-session：在一个全局的HTTP Session中，一个bean定义对应一个实例。该作用域仅在基于web的Spring ApplicationContext情形下有效。
缺省的Spring bean 的作用域是Singleton.
#####5. Spring框架中的单例bean是线程安全的吗?
不，Spring框架中的单例bean不是线程安全的。
#####6. Spring如何处理线程并发问题
将bean的作用域设为prototype，Spring将使用ThreadLocal解决线程安全问题
#####7. 在 Spring中如何注入一个java集合
Spring提供以下几种集合的配置元素：  
 1. <list>类型用于注入一列值，允许有相同的值
 2. <set> 类型用于注入一组值，不允许有相同的值
 3. <map> 类型用于注入一组键值对，键和值都可以为任意类型
 4. <props>类型用于注入一组键值对，键和值都只能为String类型
#####8. 你可以在Spring中注入一个null 和一个空字符串吗？
可以  
####4. Spring AOP
#####1. 基本术语 
    1. 切面（Aspect）：其实就是共有功能的实现。如日志切面、权限切面、事务切面等。在实际应用中通常是一个存放共有功能实现的普通Java类，之所以能被AOP容器识别成切面，是在配置中指定的。
    2. 通知（Advice）：是切面的具体实现。  
        1. 前置通知（Before）
        2. 后置通知（After）：当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）。 
        3. 返回通知（AfterReturning）：在切入点选择的连接点处的方法**正常执行**完毕时执行的通知，必须是连接点处的方法没抛出任何异常正常返回时才调用。
        4. 异常通知（AfterThrowing）
        5. 环绕通知（Around）
    3. 连接点（Joinpoint）：就是程序在运行过程中能够插入切面的地点。**spring只支持方法级的连接点**。
    4. 切入点（Pointcut）：用于定义通知应该切入到哪些连接点上。
    5. 目标对象（Target）：就是那些即将切入切面的对象，也就是那些被通知的对象。
    6. 代理对象（Proxy）：将通知应用到目标对象之后被动态创建的对象。
    7. 织入（Weaving）：将切面应用到目标对象从而创建一个新的代理对象的过程。  
        编译期：要求有一个支持这种AOP实现的特殊编译器  
        类装载期：要求有一个支持AOP实现的特殊类装载器  
        运行期：直接通过Java语言的反射机制与动态代理机制来动态实现
####5. Spring 事务
#####1. 事务管理抽象
 1. Spring事务管理抽象层主要包括3个接口  
 **TransactionDefinition**用于描述事务的隔离级别、传播规则、超时时间和是否为只读等控制事务具体行为的事务属性。  
 **PlatformTransactionManager**根据TransactionDefinition提供的事务属性的配置信息创建事务，并用**TransactionStatus**来描述这个激活事务的状态。
 2. Spring事务管理器实现类
 5种分别对应 Jpa、Jdo 、JDBC和MyBatis、Hibernate、Jta（具有多个数据源的全局事务）
 3. Spring事务同步管理器
 使用ThreadLocal技术，让Dao、Service类可做到singleton (Connection、Session等资源同一时刻不能被多线程共享)
 4. spring提供的事务管理可以分为两类：编程式的和声明式的。
 5. Spring事务配置
    1. 切点信息，用于定位实施事务切面的业务类方法
    2. 控制事物行为的事务属性：事务隔离级别、传播行为、超时时间、回滚规则
 6. 事务传播行为：
    1. PROPAGATION_REQUIRED–支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。
    2. PROPAGATION_SUPPORTS–支持当前事务，如果当前没有事务，就以非事务方式执行。
    3. PROPAGATION_MANDATORY–支持当前事务，如果当前没有事务，就抛出异常。
    4. PROPAGATION_REQUIRES_NEW–新建事务，如果当前存在事务，把当前事务挂起。
    5. PROPAGATION_NOT_SUPPORTED–以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
    6. PROPAGATION_NEVER–以非事务方式执行，如果当前存在事务，则抛出异常。
    7. PROPAGATION_NESTED–如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与 PROPAGATION_REQUIRED类似的操作

##### 6. Spring Dao

   
##### 6. Spring Dao

   