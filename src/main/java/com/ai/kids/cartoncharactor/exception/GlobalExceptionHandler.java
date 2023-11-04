package com.ai.kids.cartoncharactor.exception;

import com.ai.kids.cartoncharactor.utils.ResultVOUtil;
import com.ai.kids.cartoncharactor.vo.ResultVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.ai.kids.cartoncharactor.enums.ResultCode.SERVER_EXCEPTION;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BusinessException.class})
    public ResultVO ExceptionDemo(BusinessException e) {
        LOGGER.error("业务异常！", e);
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({Throwable.class})
    public ResultVO ExceptionDemo(Throwable th) {
        LOGGER.error("未知异常", th);
        return ResultVOUtil.error(SERVER_EXCEPTION);
    }
}
