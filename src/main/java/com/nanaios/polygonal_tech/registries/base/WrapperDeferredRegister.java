package com.nanaios.polygonal_tech.registries.base;

import com.nanaios.polygonal_tech.util.NamedToken;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WrapperDeferredRegister<T> {
    protected final DeferredRegister<T> deferredRegister;
    protected final Map<NamedToken, RegistryObject<T>> registryObjectMap = new HashMap<>();

    public WrapperDeferredRegister(DeferredRegister<T> deferredRegister) {
        this.deferredRegister = deferredRegister;
    }

    @SuppressWarnings("unchecked")
    public <I extends T> RegistryObject<I> register(NamedToken token, java.util.function.Supplier<? extends I> sup) {
        RegistryObject<I> nn = deferredRegister.register(token.name(), sup);

        // I extend Tより、RegistryObject<I>はRegistryObject<T>のサブタイプであるため、キャスト可能
        RegistryObject<T> raw = (RegistryObject<T>) nn;
        registryObjectMap.put(token, raw);

        return nn;
    }

    /// tokenに対応するRegistryObjectを返す
    public RegistryObject<T> get(NamedToken token) {
        return registryObjectMap.get(token);
    }

    /// 登録されている全てのRegistryObjectを返す
    public Collection<RegistryObject<T>> getEntries() {
        return deferredRegister.getEntries();
    }
}
