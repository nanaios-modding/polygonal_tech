package com.nanaios.polygonal_tech.lib.util

/** overrideされるべきメソッドがoverrideされていない場合に使用する */
class NonOverrideException(msg: String = ""): Exception(msg)