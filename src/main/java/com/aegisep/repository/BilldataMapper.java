package com.aegisep.repository;

import com.aegisep.dto.BilldataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

@Mapper
public interface BilldataMapper {
    Collection<BilldataVo> getBillDataListByAptcdAndBillym(@Param("aptcd") String aptcd, @Param("billym") int billym);
}
