package top.klw8.alita.admin.web.user.vo;

import top.klw8.alita.entitys.user.StaffInfo;
import top.klw8.alita.starter.web.base.vo.PagePrarmVo;

/**
 * @ClassName: SelfSupportPageVo
 * @Description: 分页查询自营人员列表(Skip方式)VO
 * @author klw
 * @date 2019年1月31日 下午2:24:23
 */
public class SelfSupportPageVo extends PagePrarmVo<StaffInfo> {

    private static final long serialVersionUID = 1369194671740853514L;

    @Override
    public StaffInfo buildEntity() {
	return new StaffInfo();
    }

}
