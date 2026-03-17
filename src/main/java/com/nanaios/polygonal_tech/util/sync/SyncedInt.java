package com.nanaios.polygonal_tech.util.sync;

import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Field;

public class SyncedInt extends SyncedValue<Integer>{
    public SyncedInt(Field field, Object instance) {
        super(field, instance);
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        tag.putInt(NBT_KEY, value);
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        setValue(tag.getInt(NBT_KEY));
    }
}
