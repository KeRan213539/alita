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

import java.time.LocalDate;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;
import top.klw8.alita.entitys.demo.mongo.ExtUserInfoDemo;
import top.klw8.alita.starter.web.base.vo.IBaseCrudVo;
import top.klw8.alita.validator.annotations.NotEmpty;
import top.klw8.alita.validator.annotations.Required;

/**
 * 修改前台用户(客户)信息
 * 2019年1月31日 上午11:19:51
 */
@Getter
@Setter
public class ModifyExtUserInfoDemoVo implements IBaseCrudVo<ExtUserInfoDemo> {

    private static final long serialVersionUID = -4302258047755427837L;

    @ApiParam(value = "要修改的客户的ID", required = true)
    @Required(validatFailMessage = "要修改的客户的ID不能为空")
    @NotEmpty(validatFailMessage = "要修改的客户的ID不能为空")
    private Long id;

    @ApiParam(value = "头像")
    private String faceImg;

    @ApiParam(value = "昵称")
    private String nickName;

    @ApiParam(value = "姓名")
    @Required(validatFailMessage = "姓名不能为空")
    @NotEmpty(validatFailMessage = "姓名不能为空")
    private String realName;

    @ApiParam(value = "联系电话")
    @Required(validatFailMessage = "联系电话不能为空")
    @NotEmpty(validatFailMessage = "联系电话不能为空")
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
