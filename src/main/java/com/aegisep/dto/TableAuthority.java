package com.aegisep.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

@Setter
@Getter
@ToString
public class TableAuthority implements Serializable {
    private String tablename;
    private String authority;
}
