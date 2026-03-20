package com.nanaios.polygonal_tech.util.save;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/// BaseBlockEntityのsave/loadでNBTに保存するフィールドに付けるアノテーション
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SaveToNBT {
    String value() default "";
}
