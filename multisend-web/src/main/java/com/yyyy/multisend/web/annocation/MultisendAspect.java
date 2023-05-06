package com.yyyy.multisend.web.annocation;

import java.lang.annotation.*;

/**
 * @author isADuckA
 * @Date 2023/5/4 22:04
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultisendAspect{

}
