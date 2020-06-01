package com.nyaxs.shypicture.exception;

import java.lang.annotation.*;

/**
 * 自动填充
 *
 * @author nyaxs
 * @version V1.0
 * @Package com.nyaxs.shypicture.exception
 * @date 2020/6/1 15:38
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface JacksonFill {
    FillTypeEnum value() default FillTypeEnum.BRACE;
}