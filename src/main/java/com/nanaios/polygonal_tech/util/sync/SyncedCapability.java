package com.nanaios.polygonal_tech.util.sync;

import com.nanaios.polygonal_tech.capability.interfaces.ICapability;
import net.minecraft.network.FriendlyByteBuf;

import java.lang.reflect.Field;

public class SyncedCapability extends SyncedValue<ICapability> {
    public SyncedCapability(Field field, Object instance) {
        super(field, instance);
    }

    @Override
    public boolean isChanged() {
        updateValue();
        return value != null && value.isMarkUpdate();
    }

    @Override
    public void writeToFriendlyByteBuf(FriendlyByteBuf tag) {
        tag.writeNbt(value.serializeNBT());
    }

    @Override
    public void readFromFriendlyByteBuf(FriendlyByteBuf tag) {
        updateValue();
        value.deserializeNBT(tag.readNbt());
    }

    @Override
    public void onSynced() {
        value.setMarkUpdate(false);
    }
}
