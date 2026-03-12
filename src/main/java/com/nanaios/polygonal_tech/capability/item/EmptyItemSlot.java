package com.nanaios.polygonal_tech.capability.item;

import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/// IItemSlotの空実装。常に空のアイテムスタックを返し、アイテムの挿入や抽出を受け付けません。
/// ifPresentのデフォルト値として使用できます。
/// 例えば、アイテムスロットが存在しない場合にこのクラスのインスタンスを返すことで、呼び出し側はnullチェックをせずに安全にアイテムスロットを操作できます。
/// このクラスはシングルトンパターンで実装されており、INSTANCEフィールドを通じて唯一のインスタンスにアクセスできます。
public class EmptyItemSlot implements IItemSlot {
    public static IItemSlot INSTANCE = new EmptyItemSlot();

    @Override
    public @NotNull ItemStack getStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public void setStack(@NotNull ItemStack stack) {

    }

    @Override
    public @NotNull ItemStack insertItem(@NotNull ItemStack stack, boolean simulate) {
        return stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit() {
        return 0;
    }

    @Override
    public boolean isItemValid(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public int getMenuX() {
        return 0;
    }

    @Override
    public int getMenuY() {
        return 0;
    }
}
