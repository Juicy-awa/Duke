package com.catclient.duke.module.impl.render;

import com.catclient.duke.event.api.annotations.EventTarget;
import com.catclient.duke.module.Category;
import com.catclient.duke.module.Module;
import com.catclient.duke.value.impl.BooleanValue;
import com.catclient.duke.value.impl.NumberValue;

public class HUD extends Module {
    public BooleanValue blur = new BooleanValue("Blur", "模糊", true, this);
    public BooleanValue fastBlur = new BooleanValue("FastBlur", "快速模糊", true, this);
    public NumberValue blurIntensity = new NumberValue("BlurIntensity", "模糊强度", 5, 0, 10, 1, this);

    public HUD() {
        super("HUD", "HUD", "Head Up Display", "抬头显示", Category.RENDER);
    }
}
