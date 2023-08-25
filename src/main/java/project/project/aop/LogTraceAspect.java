package project.project.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import project.project.aop.trace.LogTrace;
import project.project.aop.trace.TraceStatus;

@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Pointcut("execution(* project.project.controller.*Controller.*(..))")
    private void controller(){};

    @Pointcut("execution(* project.project.service.*Service.*(..))")
    private void service(){};

    @Pointcut("execution(* project.project.repository..*Repository.*(..))")
    private void repository(){};



    @Around("service() || repository() || controller()")
    public Object execute(ProceedingJoinPoint joinPoint)throws Throwable{
        TraceStatus status=null;
        try {
            String message = joinPoint.getSignature().toShortString();

            status = logTrace.begin(message);

            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        }catch (Exception e){
            logTrace.exception(status,e);
            throw e;
        }
    }
}
