package com.danidemi.obfuscatedids.spring;

public class ObfuscatedId implements CharSequence {

    private final String obfuscatedId;

    public ObfuscatedId(String obfuscatedId) {
        this.obfuscatedId = obfuscatedId;
    }

    @Override
    public int length() {
        return obfuscatedId.length();
    }

    @Override
    public char charAt(int index) {
        return obfuscatedId.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return obfuscatedId.subSequence(start, end);
    }

    @Override
    public String toString() {
        return obfuscatedId.toString();
    }
}
