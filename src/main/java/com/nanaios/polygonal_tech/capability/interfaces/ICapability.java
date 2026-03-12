package com.nanaios.polygonal_tech.capability.interfaces;

import com.nanaios.polygonal_tech.util.interfaces.IEventTarget;

/// 最も抽象化されたCapabilityインターフェース。
public interface ICapability extends IEventTarget {
    /// このCapabilityが入力可能かどうか
    boolean canInput();
    /// このCapabilityが出力可能かどうか
    boolean canOutput();
}
