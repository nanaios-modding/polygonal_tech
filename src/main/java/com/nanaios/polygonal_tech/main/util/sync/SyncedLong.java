package com.nanaios.polygonal_tech.main.util.sync;

import net.minecraft.network.FriendlyByteBuf;

import java.lang.reflect.Field;

public class SyncedLong extends SyncedValue<Long> {
    public SyncedLong(Field field, Object instance) {
        super(field, instance);
    }

    @Override
    public void writeToFriendlyByteBuf(FriendlyByteBuf tag) {
        tag.writeLong(value);
    }

    @Override
    public void readFromFriendlyByteBuf(FriendlyByteBuf tag) {
        setValue(tag.readLong());
    }
}
