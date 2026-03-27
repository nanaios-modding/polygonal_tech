package com.nanaios.polygonal_tech.main.event.interfaces;

import java.util.function.Consumer;

/// イベントシステムのインターフェース。\
/// イベントのリスナーを追加・削除し、イベントをトリガーするためのメソッドを提供する。
public interface IEventTarget {
    /// 指定されたイベントタイプのリスナーを追加する。
    default <E extends IEvent> boolean addListener(IEventType<E> type, Consumer<E> listener) {
        return false;
    }

    /// 指定されたイベントタイプのリスナーを削除する。
    default <E extends IEvent> boolean removeListener(IEventType<E> type, Consumer<E> listener) {
        return false;
    }

    /// 指定されたイベントをトリガーし、対応するリスナーを呼び出す。
    default <E extends IEvent> void triggerEvent(IEventType<E> type, E event) {
        
    }
}
