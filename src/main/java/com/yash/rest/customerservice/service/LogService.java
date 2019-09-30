package com.yash.rest.customerservice.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LogService {

	@Pointcut(value="execution(* com.yash.rest.customerservice.controller.CustomerController.*(..))")
	public void method1() {
		
	}
	
	@Before("method1()")
	public void beforeReturn(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSignature().getName()+"\n\n******Start******");
	}
	@AfterReturning("method1()")
	public void logReturning(JoinPoint joinPoint){
		System.out.println(joinPoint.getSignature().getName()+"\n\n******End******");
	}


}
