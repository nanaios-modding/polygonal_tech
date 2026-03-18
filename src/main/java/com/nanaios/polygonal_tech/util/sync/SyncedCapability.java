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
        ICapability capability = getValue();
        return capability != null && capability.isMarkUpdate();
    }

    @Override
    public void writeToFriendlyByteBuf(FriendlyByteBuf tag) {
        tag.writeNbt(getValue().serializeNBT());
        getValue().setMarkUpdate(false);
    }

    @Override
    public void readFromFriendlyByteBuf(FriendlyByteBuf tag) {
         getValue().deserializeNBT(tag.readNbt());
    }
}
