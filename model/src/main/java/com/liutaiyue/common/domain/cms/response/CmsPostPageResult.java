package com.liutaiyue.common.domain.cms.response;

import com.liutaiyue.common.model.response.ResponseResult;
import com.liutaiyue.common.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * @version 1.0
 **/
@Data
@NoArgsConstructor
public class CmsPostPageResult extends ResponseResult {

    String pageUrl;
    public CmsPostPageResult(ResultCode resultCode, String pageUrl) {
        super(resultCode);
        this.pageUrl = pageUrl;
    }
}
