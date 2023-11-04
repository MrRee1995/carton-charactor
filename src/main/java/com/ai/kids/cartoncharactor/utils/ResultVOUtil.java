package com.ai.kids.cartoncharactor.utils;

import com.ai.kids.cartoncharactor.enums.ResultCode;
import com.ai.kids.cartoncharactor.vo.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(ResultCode resultCode){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultCode.getCode());
        resultVO.setMsg(resultCode.getMessage());
        return resultVO;
    }

    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
