package com.nanaios.polygonal_tech.util.interfaces;

import java.util.function.Consumer;

/// イベントシステムのインターフェース。\
/// イベントのリスナーを追加・削除し、イベントをトリガーするためのメソッドを提供する。
public interface IEventTarget {
    /// 指定されたイベントタイプのリスナーを追加する。
    <E extends IEvent> void addListener(String type, Consumer<E> listener);

    /// 指定されたイベントタイプのリスナーを削除する。
    <E extends IEvent> void removeListener(String type, Consumer<E> listener);

    /// 指定されたイベントをトリガーし、対応するリスナーを呼び出す。
    <E extends IEvent> void triggerEvent(String type, E event);
}
