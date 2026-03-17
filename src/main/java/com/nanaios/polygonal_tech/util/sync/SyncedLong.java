package com.nanaios.polygonal_tech.util.sync;

import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Field;

public class SyncedLong extends SyncedValue<Long> {
    public SyncedLong(Field field, Object instance) {
        super(field, instance);
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        tag.putLong(NBT_KEY, value);
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        setValue(tag.getLong(NBT_KEY));
    }
}
