package com.liutaiyue.common.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Author 刘太月
 * @Despriction
 * @Created in 2019/7/24 9:58
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
@Data
@ToString
public class ResponseVO<T> extends ResponseResult{
    @ApiModelProperty(
            value = "请求返回消息对象",
            name = "data",
            example = "T.class"
    )
    private T data;

    private ResponseVO() {
    }

    private ResponseVO(ResultCode resultCode,T data){
        super(resultCode);
        this.data = data;
    }

    public static ResponseVO build() {
        return new ResponseVO();
    }

    public static ResponseVO builder(ResultCode resultCode, Object data) {
        return new ResponseVO(resultCode,data);
    }
}
