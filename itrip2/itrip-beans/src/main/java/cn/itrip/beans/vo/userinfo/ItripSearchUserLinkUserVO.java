package cn.itrip.beans.vo.userinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by donghai on 2017-06-08.
 */
@ApiModel(value = "ItripSearchUserLinkUserVO",description = "查询常用联系人")
public class ItripSearchUserLinkUserVO {
    @ApiModelProperty("[必填] 常用刚联系人姓名")
    private String linkUserName;
     private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLinkUserName() {
        return linkUserName;
    }

    public void setLinkUserName(String linkUserName) {
        this.linkUserName = linkUserName;
    }
}
