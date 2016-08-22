package com.test.entity;

/*WTF? With this class Spring Boot cant's start with error:
*java.lang.IllegalStateException: LifecycleProcessor not initialized - call 'refresh' before invoking lifecycle methods
* via the context: org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@6c80d78a:
* startup date [Mon Aug 22 08:39:19 EEST 2016]; root of context hierarchy
* AND
* java.lang.ArrayIndexOutOfBoundsException: 0
	at org.springframework.core.MethodParameter.getGenericParameterType(MethodParameter.java:388)
	~[spring-core-4.3.2.RELEASE.jar:4.3.2.RELEASE]*/

//@Component
public enum Roles {
	GUEST, CUSTOMER, ADMIN
}
