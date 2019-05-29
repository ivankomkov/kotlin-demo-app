package com.example.android.roomphotossample.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestMe

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PerformanceLogable

@Aspect
class LogAspect {

    @Before("@annotation(TestMe)")
    fun beforeMethodAnnotation(joinPoint: JoinPoint) {
        println(" execution for {} $joinPoint")
    }

    @Before("execution(* onCreate(..))")
    fun beforeJoinPointAnnotation(joinPoint: JoinPoint) {
        println(" execution for {} $joinPoint")
    }

    @Before("@annotation(PerformanceLogable)")
    fun beforeClassAnnotation(joinPoint: JoinPoint) {
        println(" execution for {} $joinPoint")
    }

}