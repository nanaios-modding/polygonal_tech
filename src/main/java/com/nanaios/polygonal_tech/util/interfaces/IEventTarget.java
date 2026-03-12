package com.nanaios.polygonal_tech.util.interfaces;

import java.util.function.Consumer;

/// イベントシステムのインターフェース。\
/// イベントのリスナーを追加・削除し、イベントをトリガーするためのメソッドを提供する。
public interface IEventTarget {
    /// 指定されたイベントタイプのリスナーを追加する。
    <E extends IEvent> void addListener(E type, Consumer<E> listener);

    /// 指定されたイベントタイプのリスナーを削除する。
    <E extends IEvent> void removeListener(E type, Consumer<E> listener);

    /// 指定されたイベントをトリガーし、対応するリスナーを呼び出す。
    <E extends IEvent> void triggerEvent(E event);
}
