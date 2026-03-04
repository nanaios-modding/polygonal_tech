package com.nanaios.polygonal_tech.capability.base;

import com.nanaios.polygonal_tech.capability.interfaces.ICapabilityMarker;
import com.nanaios.polygonal_tech.capability.interfaces.ICombinedCapability;

import java.util.ArrayList;
import java.util.List;

/// 複数の同一タイプのCapabilityをまとめるためのクラス
public class CombinedCapability<T extends ICapabilityMarker> implements ICombinedCapability<T> {
    protected final List<T> capabilities = new ArrayList<>();
    public void add(T capability) {
        capabilities.add(capability);
    }
    public void remove(T capability) {
        capabilities.remove(capability);
    }
}
