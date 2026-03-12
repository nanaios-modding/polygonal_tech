package com.nanaios.polygonal_tech.registration.base;

import com.nanaios.polygonal_tech.util.NamedToken;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Map;

public class WrapperDeferredRegister<T> {
    protected final DeferredRegister<T> deferredRegister;
    protected final Map<NamedToken, RegistryObject<T>> registryObjectMap;
    protected final Map<RegistryObject<T>, NamedToken> reverseRegistryObjectMap;

    public WrapperDeferredRegister(
            DeferredRegister<T> deferredRegister,
            Map<NamedToken, RegistryObject<T>> registryObjectMap,
            Map<RegistryObject<T>, NamedToken> reverseRegistryObjectMap
    ) {
        this.deferredRegister = deferredRegister;
        this.registryObjectMap = registryObjectMap;
        this.reverseRegistryObjectMap = reverseRegistryObjectMap;
    }

    public <I extends T> RegistryObject<I> register(String name, java.util.function.Supplier<? extends I> sup) {
        NamedToken token = new NamedToken(name);
        return register(token, sup);
    }

    @SuppressWarnings("unchecked")
    public <I extends T> RegistryObject<I> register(NamedToken token, java.util.function.Supplier<? extends I> sup) {
        RegistryObject<I> registryObject = deferredRegister.register(token.name(), sup);

        // I extend Tより、RegistryObject<I>はRegistryObject<T>のサブタイプであるため、キャスト可能
        RegistryObject<T> raw = (RegistryObject<T>) registryObject;
        registryObjectMap.put(token, raw);
        reverseRegistryObjectMap.put(raw, token);

        return registryObject;
    }

    /// tokenに対応するRegistryObjectを返す
    public RegistryObject<T> getRegistry(NamedToken token) {
        return registryObjectMap.get(token);
    }

    /// registryObjectに対応するtokenを返す
    public NamedToken getToken(RegistryObject<T> registryObject) {
        return reverseRegistryObjectMap.get(registryObject);
    }

    /// 登録されている全てのRegistryObjectを返す
    public Collection<RegistryObject<T>> getEntries() {
        return deferredRegister.getEntries();
    }
}
