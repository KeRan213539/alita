package top.klw8.alita.starter.authorization.validator;

import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.validator.IResponseMsgGenerator;
import top.klw8.alita.validator.ValidatorException;

/**
 * @author klw
 * @ClassName: AlitaResponseGenerator
 * @Description: response 生成器
 * @date 2018年9月17日 下午5:20:43
 */
public class AlitaResponseGenerator implements IResponseMsgGenerator {

    /*
     * <p>Title: generatorResponse</p>
     * @author klw
     * <p>Description: </p>
     * @param code
     * @param message
     * @return
     * @see top.klw8.alita.validator.IResponseMsgGenerator#generatorResponse(java.lang.String, java.lang.String)
     */
    @Override
    public Object generatorResponse(String code, String message, ValidatorException ex) {
        return JsonResult.failed(message);
    }

}
