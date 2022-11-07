package com.aegisep.repository;

import com.aegisep.dto.BilldataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.HashMap;

@Mapper
public interface BilldataMapper {
    Collection<HashMap> getBillDataListByAptcdAndBillym(@Param("aptcd") String aptcd, @Param("billym") int billym);
}
