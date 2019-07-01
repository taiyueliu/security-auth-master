package com.liutaiyue.common.domain.order.request;

import com.liutaiyue.common.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/26.
 */
@Data
@ToString
public class CreateOrderRequest extends RequestData {

    String courseId;

}
