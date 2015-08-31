Proxy实现AOP功能
====
使用Proxy简单的实现AOP功能，项目使用Maven管理

项目简介
----
    项目使用jdk1.7 + maven 3.3.3 + eclipse
    
    `annotation`包为自定义注解,根据注解调用不同的方法
    
    `aop`包中
        IAopUser.java AopUser.java 是使用注解的接口和类
        ProxyHandler.java 是AOP代理类
        
    `executor`包中为ProxyHandler中invoke方法调用到类
