package top.klw8.alita.validator;

/**
 * @ClassName: IResponseMsgGenerator
 * @Description: 返回消息生成器的接口,由于验证器的切面逻辑可以共用,
 * 但是具体使用的项目的返回消息不统一,所以对应具体项目的返回消息需要项目自己实现
 * @author klw
 * @date 2018年9月17日 上午11:16:48
 */
public interface IResponseMsgGenerator {

    public Object generatorResponse(String code, String message);
    
}
