package com.googlecode.kevinarpe.papaya.jdk.properties;

/**
 * @author Kevin Connor ARPE (kevinarpe@gmail.com)
 */
public class JavaProperty {

    private String _key;
    private String _value;

    public JavaProperty(String key, String value) {
        this._key = key;
        this._value = value;
    }

    public String getKey() {
        return _key;
    }

    public void setKey(String _key) {
        this._key = _key;
    }

    public String getValue() {
        return _value;
    }

    public void setValue(String _value) {
        this._value = _value;
    }
}
