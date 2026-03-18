package com.nanaios.polygonal_tech.capability.interfaces;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

/// 単一のItemStackスロットを表すインターフェース
public interface IItemSlot extends IItemHandlerModifiable, ICapability {

    /// ItemStackを取得するメソッド
    ///
    /// @return スロットにあるItemStack。スロットが空の場合はItemStack.EMPTYを返す。
    @NotNull ItemStack getStack();

    /// ItemStackを設定するメソッド
    ///
    /// @param stack 設定するItemStack
    void setStack(@NotNull ItemStack stack);

    /// ItemStackを挿入するメソッド
    ///
    /// @param stack    挿入するItemStack
    /// @param simulate ItemStackを実際に挿入するかどうか。trueの場合、挿入できるかどうかをシミュレートするだけで、実際には挿入しません。
    /// @return 挿入できなかったItemStackの残り。
    @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate);

    /// ItemStackを抽出するメソッド
    ///
    /// @param amount   抽出するItemStackの数量
    /// @param simulate ItemStackを実際に抽出するかどうか。trueの場合、抽出できるかどうかをシミュレートするだけで、実際には抽出しません。
    /// @return 抽出されたItemStack。スロットが空の場合はItemStack.EMPTYを返す。
    @NotNull ItemStack extractItem(int amount, boolean simulate);

    /// スロットの最大スタックサイズを返すメソッド
    int getSlotLimit();

    /// ItemStackがスロットに挿入可能かどうかを判断するメソッド
    ///
    /// @param stack 挿入しようとしているItemStack
    boolean isItemValid(@NotNull ItemStack stack);

    /// {@link net.minecraft.world.inventory.AbstractContainerMenu}などで、スロットのx座標を指定するためのメソッド
    int getMenuX();

    /// {@link net.minecraft.world.inventory.AbstractContainerMenu}などで、スロットのy座標を指定するためのメソッド
    int getMenuY();

    @Override
    default CompoundTag serializeNBT() {
        return getStack().serializeNBT();
    }

    @Override
    default void deserializeNBT(CompoundTag nbt) {
        ItemStack stack = ItemStack.of(nbt);
        if (!stack.isEmpty()) {
            setStack(stack);
            return;
        }

        setStack(ItemStack.EMPTY);
    }

    @Override
    default int getSlots() {
        return 1;
    }

    @Override
    @NotNull
    default ItemStack getStackInSlot(int slot) {
        return slot == 0 ? getStack() : ItemStack.EMPTY;
    }

    @Override
    default void setStackInSlot(int slot, @NotNull ItemStack stack) {
        if (slot == 0) setStack(stack);
    }

    @Override
    @NotNull
    default ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return slot == 0 ? insertItem(stack, simulate) : stack;
    }

    @Override
    @NotNull
    default ItemStack extractItem(int slot, int amount, boolean simulate) {
        return slot == 0 ? extractItem(amount, simulate) : ItemStack.EMPTY;
    }

    @Override
    default int getSlotLimit(int slot) {
        return slot == 0 ? getSlotLimit() : 0;
    }

    @Override
    default boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return slot == 0 && isItemValid(stack);
    }
}
