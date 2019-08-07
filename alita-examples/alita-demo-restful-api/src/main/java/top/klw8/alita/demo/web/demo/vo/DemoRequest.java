package top.klw8.alita.demo.web.demo.vo;

import io.swagger.annotations.ApiParam;

/**
 * @ClassName: DemoRequest
 * @Description: demo 请求
 * @author klw
 * @date 2018年9月29日 下午5:10:20
 */
public class DemoRequest {

    @ApiParam(value = "如果为空就抛异常", required = false)
//    @Required(validatFailMessage="用户名不能为空!")
//    @NotEmpty(validatFailMessage="用户名不能为空2!")
    private String abc;

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }
    
}
