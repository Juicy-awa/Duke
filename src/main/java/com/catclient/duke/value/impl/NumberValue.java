package com.catclient.duke.value.impl;

import com.catclient.duke.module.Module;
import com.catclient.duke.utils.math.MathUtils;
import com.catclient.duke.value.Value;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

/**
 * @Author：jiuxian_baka
 * @Date：2025/11/30 21:50
 * @Filename：NumberValue
 */
@Getter
@Setter
public class NumberValue extends Value {
    private float value;
    private float min;
    private float max;
    private float minValue;
    private float maxValue;
    private boolean range;
    private float step;

    public NumberValue(String name, String cnName, float min, float max, float minValue, float maxValue, float step, Module parent, Supplier<Boolean> visible) {
        super(name, cnName, parent, visible);
        this.min = min;
        this.max = max;
        this.minValue = minValue;
        this.maxValue = maxValue;
        range = true;
        this.step = step;
    }

    public NumberValue(String name, String cnName, float min, float max, float minValue, float maxValue, float step, Module parent) {
        super(name, cnName, parent, () -> true);
        this.min = min;
        this.max = max;
        this.minValue = minValue;
        this.maxValue = maxValue;
        range = true;
        this.step = step;
    }

    public NumberValue(String name, String cnName, float value, float min, float max, float step, Module parent, Supplier<Boolean> visible) {
        super(name, cnName, parent, visible);
        this.value = value;
        this.min = min;
        this.max = max;
        range = false;
        this.step = step;
    }

    public NumberValue(String name, String cnName, float min, float max, float value, float step, Module parent) {
        super(name, cnName, parent, () -> true);
        this.value = value;
        this.min = min;
        this.max = max;
        range = false;
        this.step = step;
    }

    public float get() {
        if (range) return MathUtils.getRandomInRange(minValue, maxValue);
        else return value;
    }

    public float getRandom() {
        return MathUtils.getRandomInRange(minValue, maxValue);
    }
}
