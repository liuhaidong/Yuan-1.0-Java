package ai.bitwill.xyz.config;

/**
 * # 注意：engine必需是['base_10B','translate','dialog']之一，'base_10B'是基础模型，'translate'是翻译模型，'dialog'是对话模型
 */

public enum EngineType {
    //
    BASE10B("base_10B"),
    TRANSLATE("translate"),
    DIALOG("dialog");

    private EngineType(String engineType){
        this.engineType = engineType;

    }

    private String engineType;

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }
}
