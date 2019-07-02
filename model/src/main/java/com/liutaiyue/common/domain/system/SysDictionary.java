package com.liutaiyue.common.domain.system;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by admin on 2018/2/6.
 */
@Data
@ToString
public class SysDictionary {

    @Id
    private String id;

//    @Field("d_name")
    private String dName;

//    @Field("d_type")
    private String dType;

//    @Field("d_value")
    private List<SysDictionaryValue> dValue;

}
