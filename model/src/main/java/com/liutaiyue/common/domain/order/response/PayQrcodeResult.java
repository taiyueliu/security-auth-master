package com.liutaiyue.common.domain.order.response;

import com.liutaiyue.common.model.response.ResponseResult;
import com.liutaiyue.common.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/27.
 */
@Data
@ToString
public class PayQrcodeResult extends ResponseResult {
    public PayQrcodeResult(ResultCode resultCode){
        super(resultCode);
    }
    private String codeUrl;
    private Float money;
    private String orderNumber;

}
