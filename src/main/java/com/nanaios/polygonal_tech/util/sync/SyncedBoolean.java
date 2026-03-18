package com.nanaios.polygonal_tech.util.sync;

import net.minecraft.network.FriendlyByteBuf;

import java.lang.reflect.Field;

public class SyncedBoolean extends SyncedValue<Boolean>{
    public SyncedBoolean(Field field, Object instance) {
        super(field, instance);
    }

    @Override
    public void writeToFriendlyByteBuf(FriendlyByteBuf tag) {
        tag.writeBoolean(value);
    }

    @Override
    public void readFromFriendlyByteBuf(FriendlyByteBuf tag) {
        setValue(tag.readBoolean());
    }
}
