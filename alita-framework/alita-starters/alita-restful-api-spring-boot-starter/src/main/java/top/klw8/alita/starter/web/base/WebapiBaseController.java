package top.klw8.alita.starter.web.base;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import top.klw8.alita.starter.web.dateEditor.CustomLocalDateEditor;
import top.klw8.alita.starter.web.dateEditor.CustomLocalDateTimeEditor;
import top.klw8.alita.starter.web.dateEditor.MyCustomDateEditor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author klw
 * @ClassName: BaseController
 * @Description: web api 用的 无操作BaseController
 * @date 2018年10月26日 下午3:32:04
 */
public class WebapiBaseController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 自动去除空格
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new MyCustomDateEditor());
        binder.registerCustomEditor(LocalDate.class, new CustomLocalDateEditor());
        binder.registerCustomEditor(LocalDateTime.class, new CustomLocalDateTimeEditor());
    }

}
