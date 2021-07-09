package com.exadel.sandbox.team5.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Pair {
    private String first;
    private String second;

    public Pair(long first, double second) {
        this.first = String.valueOf(first);
        this.second = String.valueOf(second);
    }

    public Pair(String first, long second) {
        this.first = first;
        this.second = String.valueOf(second);
    }

    public Pair(long first, String second) {
        this.first = String.valueOf(first);
        this.second = second;
    }
}
