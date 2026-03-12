package com.nanaios.polygonal_tech.util.interfaces;

import com.nanaios.polygonal_tech.util.IOMode;

/// 入出力のモードを表すinterface\
/// 主に、機械の各面の入出力設定に使用される。\
/// 実装例は{@link IOMode}を参照。
public interface IIOMode {
    String name();
}