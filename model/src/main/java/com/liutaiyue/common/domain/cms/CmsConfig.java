package com.liutaiyue.common.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Created by admin on 2018/2/6.
 */
@Data
@ToString
public class CmsConfig {

    @Id
    private String id;
    private String name;
    private List<CmsConfigModel> model;

}
