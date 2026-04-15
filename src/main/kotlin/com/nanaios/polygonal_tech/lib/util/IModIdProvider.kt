package com.nanaios.polygonal_tech.lib.util

/**
 * modIdを提供できることを示すinterface。
 * */
interface IModIdProvider {
    /**
     * このIModIdProviderが属するMODのIDを返します。
     * */
    val modId: String
}