/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.demo.web.demo.vo;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import top.klw8.alita.entitys.demo.mongo.ExtUserInfoDemo;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
import top.klw8.alita.validator.annotations.MobilePhoneNumber;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

import java.time.LocalDate;

/**
 * 新增前台用户(客户)信息 VO
 * 2019-01-25 16:26:01
 */
@Getter
@Setter
public class AddExtUserInfoDemoVo implements IBaseCrudVo<ExtUserInfoDemo> {

    private static final long serialVersionUID = -6569981016887416291L;

    @ApiParam(value = "头像")
    private String faceImg;

    @ApiParam(value = "昵称")
    private String nickName;

    @ApiParam(value = "姓名", required = true)
    @Required(validatFailMessage = "姓名不能为空")
    @NotEmpty(validatFailMessage = "姓名不能为空")
    private String realName;

    @ApiParam(value = "联系电话", required = true)
    @Required(validatFailMessage = "联系电话不能为空")
    @NotEmpty(validatFailMessage = "联系电话不能为空")
    @MobilePhoneNumber
    private String phoneNumber;

    @ApiParam(value = "生日")
    private LocalDate birthday;

    @Override
    public ExtUserInfoDemo buildEntity() {
        ExtUserInfoDemo user = new ExtUserInfoDemo();
        BeanUtils.copyProperties(this, user);
        return user;
    }


}
