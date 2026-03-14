package com.nanaios.polygonal_tech.util.sync;

import net.minecraft.network.FriendlyByteBuf;

/// 同期可能なオブジェクトを表すインターフェース。
public interface ISynchronized {
    void writeToBuffer(FriendlyByteBuf buffer);

    void readFromBuffer(FriendlyByteBuf buffer);
}
