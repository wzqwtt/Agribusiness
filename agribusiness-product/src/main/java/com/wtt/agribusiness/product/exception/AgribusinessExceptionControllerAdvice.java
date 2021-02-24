package com.wtt.agribusiness.product.exception;

import com.wtt.common.exception.BizCodeEnume;
import com.wtt.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
* 集中处理所有异常
* @author Wang TianTian
* @email 442301197@qq.com
* @date 2021/2/24 16点17分
* */

@Slf4j
@RestControllerAdvice(basePackages = "com.wtt.agribusiness.product.controller")
public class AgribusinessExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e){
        log.error("数据校验出现问题{}，异常类型:{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError)->{
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        //抽取状态码和错误信息到common的exception包下，封装为枚举类，直接取用
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(),BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data",errorMap);
    }

    //如果匹配不到任何异常
    @ExceptionHandler(value = Throwable.class)
    public R handleException(){



        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(),BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }

}
