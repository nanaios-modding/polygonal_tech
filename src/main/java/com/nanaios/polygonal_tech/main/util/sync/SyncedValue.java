package com.nanaios.polygonal_tech.main.util.sync;

import net.minecraft.network.FriendlyByteBuf;

import java.lang.reflect.Field;

public abstract class SyncedValue<T> {

    protected T value;
    protected final Field field;
    protected final Object instance;
    public SyncedValue(Field field,Object instance) {
        this.field = field;
        this.instance = instance;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        try {
            field.set(instance, value);
        } catch (IllegalAccessException ignored) {}
    }

    @SuppressWarnings("unchecked")
    protected void updateValue() {
        try {
            value = (T) field.get(instance);
        } catch (IllegalAccessException ignored) {}
    }

    public boolean isChanged() {
        T oldValue = value;
        updateValue();
        return !value.equals(oldValue);
    }
    
    public void onSynced() {
    }

    public abstract void writeToFriendlyByteBuf(FriendlyByteBuf tag);
    public abstract void readFromFriendlyByteBuf(FriendlyByteBuf tag);
}
