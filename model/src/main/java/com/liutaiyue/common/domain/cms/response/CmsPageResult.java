package com.liutaiyue.common.domain.cms.response;

import com.liutaiyue.common.domain.cms.CmsPage;
import com.liutaiyue.common.model.response.ResponseResult;
import com.liutaiyue.common.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CmsPageResult extends ResponseResult {
    CmsPage cmsPage;
    public CmsPageResult(ResultCode resultCode,CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
