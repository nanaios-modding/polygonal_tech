package com.nanaios.polygonal_tech.util.sync;

import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Field;

public class SyncedBoolean extends SyncedValue<Boolean>{
    public SyncedBoolean(Field field, Object instance) {
        super(field, instance);
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        tag.putBoolean(NBT_KEY, value);
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        setValue(tag.getBoolean(NBT_KEY));
    }
}
