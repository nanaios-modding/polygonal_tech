package com.nanaios.polygonal_tech.util.sync;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/// フィールドにこのアノテーションを付けると、そのフィールドの値がクライアントとサーバーで同期されるようになります。
/// 同期タイミングは、アノテーションのvalue()で指定されたTypeによって異なります。
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Synchronize {
    Type value();
    enum Type {
        /// 常に同期される
        ALWAYS,
        /// GUIを開いているときのみ同期される
        MENU
    }
}
