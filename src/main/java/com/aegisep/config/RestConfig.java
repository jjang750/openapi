package com.aegisep.config;


import com.aegisep.auth.ApiAuthenticationToken;
import com.aegisep.dto.BilldataVo;
import com.aegisep.dto.TableAuthority;
import com.aegisep.repository.BilldataMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
    public ResponseEntity<HashMap<String, Object>> openapi(
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

        SecurityContext context = SecurityContextHolder.getContext();
        ApiAuthenticationToken token =
                (ApiAuthenticationToken) context.getAuthentication();
        try {
            log.debug("Authentication token : " + token.getToken());
            log.debug("Authentication table authorize : " + token.getTableAuthorities());
            log.debug("Authentication UUID : " + token.getUUID());

            Collection<TableAuthority> tableAuthorities = token.getTableAuthorities();
            boolean is_authority = tableAuthorities.
                    stream().
                    allMatch(tableAuthority -> tableAuthority.getTablename().equals(command));

            log.debug("Authentication table authorized : " + is_authority);
            /*  테이블 접근 권한이 있는 경우 실행 */
            if (is_authority) {
                 /* 테이블 접근 권한 있지만 실행 메소드 없다면 에러 */
                HashMap<String, Object> returnMap = messageToJson(token.getUUID(), "success");
                returnMap.put("data", executeMethod("billdata",command, paging, map));

                return ResponseEntity.ok().body(returnMap);
            }
        }catch (JsonProcessingException | InvocationTargetException | IllegalAccessException ignored) {
            log.error(token.getUUID() + "] " + ignored.getMessage(), ignored);
            return ResponseEntity.internalServerError().body(messageToJson(token.getUUID(), ignored.getMessage()));
        }catch (NoSuchMethodException noSuchMethodException) {
            log.error(token.getUUID() + "] " + noSuchMethodException.getMessage(), noSuchMethodException);
            return ResponseEntity.internalServerError().body(messageToJson(token.getUUID(), "The request is malformed."));
        }
        return ResponseEntity.badRequest().body(messageToJson(token.getUUID(),"The request is malformed."));
    }
    /* 리턴 메시지를 JSON 타입으로 변경 */
    private HashMap<String, Object> messageToJson(String UUID, Object body) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("request_uuid", UUID);
        map.put("request_time", Calendar.getInstance().getTime().toString());
        map.put("message", body);
        return map;
    }
    /* Open Api process */
    public Collection<BilldataVo> billdata(String command, Integer paging, HashMap<String, Object> map)
            throws JsonProcessingException {
        log.info("billdata : enter");
        return billdataMapper.getBillDataListByAptcdAndBillym("12345", 202210);
    }

    /* 메소드를 찾아 실행한다. */
    private Collection<?> executeMethod(String name, Object... args)
            throws JsonProcessingException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        log.info("executeMethod : " + name);
        try {
            Class<?>[] params = new Class<?>[args.length];      // 파라미터의 개수만큼
            for (int i = 0; i < args.length; i++) {        // 타입 추출
                params[i] = args[i].getClass();
            }
            // 인스턴스의 클래스 타입에서 메소드를 찾는다.
            Method method = this.getClass().getMethod(name, params);
            return (Collection<?>) method.invoke(this, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
