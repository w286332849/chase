SSH框架集成搭建
	一:Struts2框架:
		版本:2.3.8(GA), 
  		官方最新版本(文档版本):2.3.8, 
		下载框架日期:2013-01-19. 
		框架类型:完整包.
		包含: 测试实例,lib包,Docs文档,src源代码.
		
	 二:Hibernate框架:
    	版本:4.1.9(Final), 
  		官方最新版本(文档版本):4.1.9, 
		下载框架日期:2013-01-20. 
		框架类型:完整包.
		包含: lib包,Docs文档,src源代码.
		
	三:Spring框架:
    	版本:3.2(GA), 
  		官方最新版本(文档版本):3.2, 
		下载框架日期:2013-01-20. 
		框架类型:完整包.
		包含: lib包,Docs文档,src源代码.
		
	四.Log4j框架:
		版本:2.0(Bate3)(目前是Bate版本,以后替换成正式版)
		官方最新版本(文档版本):2.0Bate3,
		下载框架日期:2013-01-24.  
		框架类型:完整包.
		包含: lib包,Docs文档,src源代码
		
			                                 
Struts-Jar包准备:
	struts2-core-2.3.8.jar		核心包
	xwork-core-2.3.8.jar		核心包:xwork核心
	ognl-3.0.6.jar			核心包:用于支持ognl表达式
	freemarker-2.3.19.jar     	核心包:用于支持freemarker
	javassist-3.11.0.GA.jar   	核心包:用于字节码处理(包冲突,使用Hibernate的javassist-3.17.1-GA.jar)
	
	struts2-convention-plugin-2.3.8.jar   	扩展包: 支持注解
	asm-3.3.jar                          	扩展包: 使用注解时加入
	asm-commons-3.3.jar                  	扩展包: 使用注解时加入
	struts2-spring-plugin-2.3.8.jar       	扩展包: 支持Spring管理Struts,与Spring整合时使用
	
	commons-fileupload-1.2.2.jar   		公用组件:用于支持文件上传
	commons-io-2.0.1.jar                  	公用组件:常用IO封装
	commons-lang3-3.1.jar                 	公用组件:常用工具封装
	
Hibernate-Jar包准备:
	hibernate-core-4.1.9.Final.jar   	         核心包
	antlr-2.7.7.jar    	                         核心包:语法分析器
	dom4j-1.6.1.jar					 核心包:DOM4J解析配置文件
	javassist-3.17.1-GA.jar   			 核心包:用于字节码处理
	jboss-transaction-api_1.1_spec-1.0.0.Final.jar   核心包:支持事务处理
	jboss-logging-3.1.0.GA.jar                       核心包:支持日志
	hibernate-commons-annotations-4.0.1.Final.jar    核心包：支持注解
	hibernate-jpa-2.0-api-1.0.1.Final.jar            核心包:支持JPA注解	

          
Spring-Jar包准备:
	spring-core-3.2.0.RELEASE.jar			核心包
	spring-context-3.2.0.RELEASE.jar		核心包:上下文
	spring-context-support-3.2.0.RELEASE.jar	核心包:支持上下文
	spring-beans-3.2.0.RELEASE.jar			核心包:支持创建Bean
	spring-expression-3.2.0.RELEASE.jar   		核心包:支持Spring表达式
	commons-logging-1.1.1.jar             		核心包:支持日志
	
	spring-web-3.2.0.RELEASE.jar         		扩展包:支持web操作
	spring-aop-3.2.0.RELEASE.jar          		扩展包:支持AOP
	spring-aspects-3.2.0.RELEASE.jar      		扩展包:支持Aspects-J面向切面语法
	spring-tx-3.2.0.RELEASE.jar           		扩展包:支持声明式事物
	aspectjweaver.jar                     		扩展包:支持Aspects
	aopalliance-1.0.jar                   		扩展包:支持AOP
	spring-test-3.2.0.RELEASE.jar			扩展包:支持Junit4单元测试
    		
    		
数据库包准备：
	mysql-connector-java-5.0.8-bin.jar    	数据库连接：mysql
	commons-dbcp-1.4.jar                  	数据源: dbcp
	commons-pool-1.6.jar                  	数据库连接池
        	
        
Log4j2-Jar包准备:
	log4j-core-2.0-beta3.jar 		核心包
	log4j-api-2.0-beta3.jar			核心包:API
       		
       
Json-lib包:		
	json-lib-2.4-jdk15.jar                  Json包
	ezmorph-1.0.6.jar                       Json转换器
	commons-lang-2.6.jar                    公用组件
	commons-collections-3.1.jar             公用组件
	commons-beanutils-1.8.3.jar             公用组件
	struts2-json-plugin-2.3.8.jar           struts-Json插件,Struts使用Json时使用

       			
前端框架:
	jQuery EasyUI 1.3.2
	jQuery 1.8.0      		


控件:
	1.zTree控件3.5.12