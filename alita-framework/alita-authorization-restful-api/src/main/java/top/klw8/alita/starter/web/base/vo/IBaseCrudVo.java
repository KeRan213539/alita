package top.klw8.alita.starter.web.base.vo;


import top.klw8.alita.entitys.base.BaseEntity;

/**
 * @ClassName: IBaseCrudVo
 * @Description: 要继承 WebapiCrudBaseController 的 Controller 对应的VO需要实现此接口
 * @author klw
 * @date 2019年1月25日 下午1:34:52
 */
public interface IBaseCrudVo<E extends BaseEntity> {

    public E buildEntity();
    
}
