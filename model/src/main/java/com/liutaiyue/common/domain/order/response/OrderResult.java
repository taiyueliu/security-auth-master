package com.liutaiyue.common.domain.order.response;

import com.liutaiyue.common.domain.order.XcOrders;
import com.liutaiyue.common.model.response.ResponseResult;
import com.liutaiyue.common.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/26.
 */
@Data
@ToString
public class OrderResult extends ResponseResult {
    private XcOrders xcOrders;
    public OrderResult(ResultCode resultCode, XcOrders xcOrders) {
        super(resultCode);
        this.xcOrders = xcOrders;
    }


}
