package com.nanaios.polygonal_tech.capability.interfaces;

public interface IHasIOStatus {
    default boolean isActive() {
        return true;
    }

    default boolean canInput() {
        return isActive();
    }

    default boolean canOutput() {
        return isActive();
    }
}