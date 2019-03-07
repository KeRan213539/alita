package top.klw8.alita.admin.web.user.vo;

import top.klw8.alita.entitys.user.StaffInfo;
import top.klw8.alita.starter.web.base.vo.ListPrarmVo;

/**
 * @ClassName: SelfSupportListVo
 * @Description: 查询自营人员列表(不分页)VO
 * @author klw
 * @date 2019年1月31日 下午2:23:32
 */
public class SelfSupportListVo extends ListPrarmVo<StaffInfo> {

    private static final long serialVersionUID = -8753740521433616073L;

    @Override
    public StaffInfo buildEntity() {
	return new StaffInfo();
    }

}
