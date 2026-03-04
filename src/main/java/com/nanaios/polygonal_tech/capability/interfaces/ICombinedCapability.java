package com.nanaios.polygonal_tech.capability.interfaces;

public interface ICombinedCapability<T extends ICapabilityMarker> {
    /// Capabilityを追加
    /// @param capability 追加するCapability
    void add(T capability);

    /// Capabilityを削除
    /// @param capability 削除するCapability
    void remove(T capability);
}
