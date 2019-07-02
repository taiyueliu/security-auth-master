package com.liutaiyue.common.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 2018/1/24 10:04.
 * @Modified By:
 */
@Data
@ToString
public class CmsTemplate {

    //站点ID
    private String siteId;
    //模版ID
    @Id
    private String templateId;
    //模版名称
    private String templateName;
    //模版参数
    private String templateParameter;

    //模版文件Id
    private String templateFileId;
}
