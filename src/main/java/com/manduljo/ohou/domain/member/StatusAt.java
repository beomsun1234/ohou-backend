package com.manduljo.ohou.domain.member;

import lombok.Getter;

@Getter
public enum StatusAt {

    T("T", true),
    F("F", false)
    ;
    private String value;
    private boolean isActive;

    StatusAt(String value, boolean isActive) {
        this.value = value;
        this.isActive = isActive;
    }
}
