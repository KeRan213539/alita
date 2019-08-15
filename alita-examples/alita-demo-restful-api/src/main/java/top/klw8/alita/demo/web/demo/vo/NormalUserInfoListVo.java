package top.klw8.alita.demo.web.demo.vo;

import top.klw8.alita.entitys.demo.ExtUserInfoDemo;
import top.klw8.alita.starter.web.base.vo.ListPrarmVo;

/**
 * @ClassName: NormalUserInfoListVo
 * @Description: 查询客户列表(不分页)VO
 * @author klw
 * @date 2019年1月31日 下午2:23:32
 */
public class NormalUserInfoListVo extends ListPrarmVo<ExtUserInfoDemo> {

    private static final long serialVersionUID = -8753740521433616073L;

    @Override
    public ExtUserInfoDemo buildEntity() {
	return new ExtUserInfoDemo();
    }

}
