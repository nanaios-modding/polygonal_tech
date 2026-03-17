package com.nanaios.polygonal_tech.util.sync;

import net.minecraft.network.FriendlyByteBuf;

import java.lang.reflect.Field;

public class SyncedInt extends SyncedValue<Integer>{
    public SyncedInt(Field field, Object instance) {
        super(field, instance);
    }

    @Override
    public void writeToFriendlyByteBuf(FriendlyByteBuf tag) {
        tag.writeInt(value);
    }

    @Override
    public void readFromFriendlyByteBuf(FriendlyByteBuf tag) {
        setValue(tag.readInt());
    }
}
