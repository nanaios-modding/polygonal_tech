package com.nanaios.polygonal_tech.container.interfaces;

/// Combined containerを表すインターフェース。複数のコンテナをまとめて扱うためのもの。
public interface ICombinedContainer<T> extends IContainer<T>{
    int getContainerSize();
}
