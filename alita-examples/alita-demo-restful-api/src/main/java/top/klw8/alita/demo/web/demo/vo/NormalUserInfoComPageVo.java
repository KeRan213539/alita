package top.klw8.alita.demo.web.demo.vo;

import top.klw8.alita.entitys.demo.mongo.ExtUserInfoDemo;
import top.klw8.alita.starter.web.base.vo.ComparativePagePrarmVo;

/**
 * @ClassName: NormalUserInfoComPageVo
 * @Description: 分页查询客户列表(比较方式)VO
 * @author klw
 * @date 2019年1月31日 下午2:24:47
 */
public class NormalUserInfoComPageVo extends ComparativePagePrarmVo<ExtUserInfoDemo> {

    private static final long serialVersionUID = -2320579166748280919L;

    @Override
    public ExtUserInfoDemo buildEntity() {
	return new ExtUserInfoDemo();
    }

}
