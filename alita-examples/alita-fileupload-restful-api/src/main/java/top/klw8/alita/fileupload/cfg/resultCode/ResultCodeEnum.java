package top.klw8.alita.fileupload.cfg.resultCode;

import lombok.Getter;
import top.klw8.alita.service.result.IResultCode;

/**
 * @author klw
 * @ClassName: ResultCodeEnum
 * @Description: 返回给前端的Code定义--类别
 * @date 2019/6/6 16:50
 */
@Getter
public enum ResultCodeEnum implements IResultCode {

    FILE_UPLOAD("101", "文件上传"),

    ;

    /**
     * @author klw
     * @Description: 类别的code(3位)
     */
    private String code;


    /**
     * @author klw
     * @Description: 类别名称
     */
    private String codeName;

    ResultCodeEnum(String code, String codeName){
        this.code = code;
        this.codeName = codeName;
    }

}
