package com.ansel.pojo;

import lombok.Data;

@Data
public class Choice {
    private Message message;
    private String finishReason;
    private int index;
}
