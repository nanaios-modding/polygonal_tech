package com.nanaios.polygonal_tech.util;

import com.nanaios.polygonal_tech.util.interfaces.IIOMode;

public class IOMode {
    /// デフォルトのINPUTモード。通常は、入力が許可されることを示す。
    public static final IIOMode INPUT = new Impl("input");
    /// デフォルトのOUTPUTモード。通常は、出力が許可されることを示す。
    public static final IIOMode OUTPUT = new Impl("output");
    /// デフォルトのINPUT_OUTPUTモード。通常は、入出力の両方が許可されることを示す。
    public static final IIOMode INPUT_OUTPUT = new Impl("input_output");
    /// デフォルトのNONEモード。通常は、入出力を許可しないことを示す。
    public static final IIOMode NONE = new Impl("none");

    /// IOModeの単純な実装クラス。
    public record Impl(String name) implements IIOMode { }
}
