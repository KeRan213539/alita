package top.klw8.alita.admin.web.user.vo;

import top.klw8.alita.entitys.user.ExtUserInfoDemo;
import top.klw8.alita.starter.web.base.vo.PagePrarmVo;

/**
 * @ClassName: NormalUserInfoPageVo
 * @Description: 分页查询客户列表(Skip方式)VO
 * @author klw
 * @date 2019年1月31日 下午2:24:23
 */
public class NormalUserInfoPageVo extends PagePrarmVo<ExtUserInfoDemo> {

    private static final long serialVersionUID = 1369194671740853514L;

    @Override
    public ExtUserInfoDemo buildEntity() {
	return new ExtUserInfoDemo();
    }

}
