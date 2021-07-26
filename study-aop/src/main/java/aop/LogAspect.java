package aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class LogAspect {
    /**
     * 定义切点
     */
    @Pointcut("execution()")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){

    }

    @After("log()")
    public void doAfter(JoinPoint joinPoint){

    }

    @AfterReturning()
    public void doAfterReturning(JoinPoint joinPoint){

    }

    @AfterThrowing()
    public void doAfterThrowing(JoinPoint joinPoint){

    }

    @Around("log()")
    public void doAround(ProceedingJoinPoint joinPoint){

    }
}
