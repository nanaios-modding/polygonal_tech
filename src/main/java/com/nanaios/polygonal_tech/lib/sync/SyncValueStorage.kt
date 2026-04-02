package com.nanaios.polygonal_tech.lib.sync

import com.mojang.logging.LogUtils
import com.nanaios.polygonal_tech.lib.util.NonOverrideException
import org.slf4j.Logger

/**
 * ISyncValueStorageの基本的な実装クラス
 * byで移譲されることを想定している
 * */
open class SyncValueStorage():ISyncValueStorage {
    companion object{
        private val LOGGER: Logger = LogUtils.getLogger()
    }

    protected val values: MutableList<ISyncValue<*>> = mutableListOf()

    override fun addValue(value: ISyncValue<*>) {
        values.add(value)
    }

    override fun removeValue(value: ISyncValue<*>) {
        values.remove(value)
    }

    /**
     * 移譲先でoverrideすることを想定している
     * これにより、移譲先のクラスでISyncValueの変更を検知できるようになる
     * */
    override fun onSyncValueChanged(value: ISyncValue<*>) {
        // このクラス自体はISyncValueの変更を検知せず、継承先、もしくは移譲先に任せる想定になっている
        // このメソッドは呼ばれるべきでないため、エラーを出す
        val e = NonOverrideException()
        LOGGER.error("SyncValueStorage.onSyncValueChanged has not been overridden. If this is not the intended behavior, the storage in question is not tracking changes to ISyncValue. Please consider overriding this method appropriately in a subclass or a class that inherits from it.",e)
    }
}