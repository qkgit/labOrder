package com.bdu.laborder.exception;


import com.bdu.laborder.common.BussinessCode;
import com.bdu.laborder.common.Result;
import com.bdu.laborder.common.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 *
* @Title: GlobalExceptionHandler
* @Description: 全局异常处理
如果使用@RestControllerAdvice 注解
则会将返回的数据类型转换成JSON格式
* @Version:1.0.0
* @author pancm
* @date 2018年10月24日
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 处理自定义的业务异常
	 * @param req
	 * @param e
	 * @return
	 */
    @ExceptionHandler(value = LabOrderException.class)
    @ResponseBody
	public Result bizExceptionHandler(HttpServletRequest req, LabOrderException e){
    	logger.error("发生业务异常！原因是：{}",e.getErrorMsg());
    	return ResultGenerator.error(e.getErrorCode(),e.getErrorMsg());
    }

	/**
	 * 处理空指针的异常
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value =NullPointerException.class)
	@ResponseBody
	public Result exceptionHandler(HttpServletRequest req, NullPointerException e){
		logger.error("发生空指针异常！原因是:",e);
		return ResultGenerator.error(BussinessCode.BODY_NOT_MATCH);
	}


    /**
        * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
	@ResponseBody
	public Result exceptionHandler(HttpServletRequest req, Exception e){
    	logger.error("未知异常！原因是:",e);
       	return ResultGenerator.error(BussinessCode.INTERNAL_SERVER_ERROR);
    }



}
