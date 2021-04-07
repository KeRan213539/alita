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
package top.klw8.alita.entitys.demo.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;
import top.klw8.alita.base.mongodb.annotations.NotPersistence;
import top.klw8.alita.service.base.mongo.common.ITextIndexedCustomSupport;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.base.mongo.common.MongoBaseEntity;
import top.klw8.alita.utils.AnalyzerUtil;

import java.time.LocalDateTime;

/**
 * @ClassName: ExtUserInfoDemo
 * @Description: 扩展用户信息Demo
 * @author klw
 * @date 2019-03-12 10:32:42
 */
@Document(collection = "ext_user_info_demo")
@Getter
@Setter
public class ExtUserInfoDemo extends MongoBaseEntity implements ITextIndexedCustomSupport {

    private static final long serialVersionUID = 6774327869246437799L;
    
    /**
     * @author klw
     * @Fields accountInfo : 账户信息
     */
    @NotPersistence
    private AlitaUserAccount accountInfo;
    
    /**
     * @author klw
     * @Fields nickName : 昵称
     */
    private String nickName;
    
    /**
     * @author klw
     * @Fields realName : 姓名
     */
    private String realName;
    
    /**
     * @author klw
     * @Fields phoneNumber : 联系电话
     */
    private String phoneNumber;
    
    /**
     * @author klw
     * @Fields birthday : 生日
     */
    private LocalDateTime birthday;
    
    
    /**
     * @author klw
     * @Fields textIndexedField : 全文搜索字段
     */
    @TextIndexed
    private String textIndexedField;


    /*
     * <p>Title: buildTextIndexedField</p>
     * @author klw
     * <p>Description: </p>
     * @see top.klw8.alita.service.base.entitys.ITextIndexedCustomSupport#buildTextIndexedField()
     */
    @Override
    public void buildTextIndexedField() {
	Assert.hasText(realName, "姓名不能为空");
	Assert.hasText(phoneNumber, "电话号码不能为空");
	this.textIndexedField = AnalyzerUtil.strAnalyzerReturnsWithSpaceNoSmart(realName) + " "  + AnalyzerUtil.strAnalyzerInnerSpace(phoneNumber);
    }
    

}
