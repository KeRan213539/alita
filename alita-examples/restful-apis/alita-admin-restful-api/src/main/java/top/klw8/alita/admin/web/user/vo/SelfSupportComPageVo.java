package top.klw8.alita.admin.web.user.vo;

import top.klw8.alita.entitys.user.StaffInfo;
import top.klw8.alita.starter.web.base.vo.ComparativePagePrarmVo;

/**
 * @ClassName: SelfSupportComPageVo
 * @Description: 分页查询自营人员列表(比较方式)VO
 * @author klw
 * @date 2019年1月31日 下午2:24:47
 */
public class SelfSupportComPageVo extends ComparativePagePrarmVo<StaffInfo> {

    private static final long serialVersionUID = -2320579166748280919L;

    @Override
    public StaffInfo buildEntity() {
	return new StaffInfo();
    }

}
