package com.aegisep.config;


import com.aegisep.dto.BilldataVo;
import com.aegisep.repository.BilldataMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

@RestController
public class RestConfig {

    @Autowired
    BilldataMapper billdataMapper;

    private static final Logger log = LoggerFactory.getLogger(RestConfig.class);

    @Operation(summary = "테스트", description = "이지스엔터프라이즈 api example")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })
    @GetMapping("/hello")
    public ResponseEntity<String> hello(@Parameter(description = "이름", required = true, example = "Park") @RequestParam String name) {
        return ResponseEntity.ok("hello " + name);
    }
    /* Open Api process */
    @Operation(summary = "테스트", description = "이지스엔터프라이즈 api example")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })
    @ResponseBody
    @GetMapping(value ="/openapi/{command}/{paging}", produces = "application/json; charset=UTF-8")
    @PostMapping(value ="/openapi/{command}/{paging}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> openapi(
            @Parameter(description = "Command", required = true, example = "billdata")
            @PathVariable String command,
            @Parameter(description = "Paging Count * 200", required = true, example = "1")
            @PathVariable int paging,
            @Parameter(description = "Request Body Json", required = true, example = "{\"commands\":\"{\"command\":\"value\"}\"}")
            @RequestBody HashMap<String, Object> map
            ){
        log.info("openapi : enter");
        log.debug("command : " + command);
        log.debug("paging : " + paging);
        log.debug("RequestBody : " + map);
        try {
            if (command.equals("billdata")) {
                return billdata(command, paging, map);
            }
        }catch (JsonProcessingException ignored) {
            return ResponseEntity.internalServerError().body(ignored.getMessage());
        }
        return ResponseEntity.badRequest().body("The request is malformed.");
    }

    /* Open Api process */
    public ResponseEntity<String> billdata(String command,int paging,HashMap<String, Object> map)
            throws JsonProcessingException {
        log.info("billdata : enter");
        Collection<BilldataVo> billdataVos = billdataMapper.getBillDataListByAptcdAndBillym("12345", 202210);
        return ResponseEntity.ok(new ObjectMapper().writeValueAsString(billdataVos));
    }
}
