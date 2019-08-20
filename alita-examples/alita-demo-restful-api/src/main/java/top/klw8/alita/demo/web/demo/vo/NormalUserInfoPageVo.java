package top.klw8.alita.demo.web.demo.vo;

import top.klw8.alita.entitys.demo.mongo.ExtUserInfoDemo;
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
