package com.ansel.pojo;

import lombok.Data;

import java.util.List;

@Data
public class RespData {
    private String id;
    private String object;
    private int created;
    private String model;
    private List<Choice> choices;
}
