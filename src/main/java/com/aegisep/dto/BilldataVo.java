package com.aegisep.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class BilldataVo implements Serializable {
    @ToString.Exclude
    private long seq;
    private String aptcd;
    private String dongho;
    private int billym;
    private String custnm;
    private int distspace;
    private String paidmethod;
    private long monthfee;
    private long bfoverfee;
    private long bfoverduefee;
    private long bfduedtfee;
}
