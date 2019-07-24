package com.liutaiyue.common.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 2018/1/24 18:33.
 * @Modified By:
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseResult implements Response {

    //操作是否成功
    @ApiModelProperty(
            value = "成功与否",
            name = "success",
            example = "true"
    )
    boolean success = SUCCESS;

    //操作代码
    @ApiModelProperty(
            value = "状态码",
            name = "code",
            example = "200"
    )
    int code = SUCCESS_CODE;

    //提示信息
    @ApiModelProperty(
            value = "接口执行状态消息",
            name = "message",
            example = "成功！"
    )
    String message;

    public ResponseResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public static ResponseResult SUCCESS(){
        return new ResponseResult(CommonCode.SUCCESS);
    }
    public static ResponseResult FAIL(){
        return new ResponseResult(CommonCode.FAIL);
    }

}
