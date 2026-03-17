package com.nanaios.polygonal_tech.util.sync;

import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Field;

public abstract class SyncedValue<T> {
    public static String NBT_KEY = "value";

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
    public boolean isChanged() {
        T oldValue = value;
        try {
            value = (T) field.get(instance);
            return !value.equals(oldValue);
        } catch (IllegalAccessException ignored) {}
        return false;
    }

    public abstract void writeToNBT(CompoundTag tag);
    public abstract void readFromNBT(CompoundTag tag);
}
