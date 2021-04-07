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
package top.klw8.alita.service.demo.service.impl.test;


/**
 * @author klw
 * @ClassName: ExtUserInfoDemoServiceImpl
 * @Description: 用户扩展信息demo 的 Service
 * @date 2019-01-30 11:39:45
 */
//@Slf4j
//@DubboService(async = true)
//public class ExtUserInfoDemoServiceImpl extends BaseServiceImpl<ExtUserInfoDemo> implements IExtUserInfoDemoService {
//
////    private IExtUserInfoDemoServicenfoDemoDao dao;
//
////    @Autowired
////    private IAlitaUserService accountService;
//
//    public ExtUserInfoDemoServiceImpl(@Autowired IExtUserInfoDemoDao dao) {
////        super(dao);
////        this.dao =dao dao;
//    }
//
//    @Override
//    public ExtUserInfoDemo findByAccountId(String accountId) {
//        AlitaUserAccount account = new AlitaUserAccount();
//        ExtUserInfoDemo query = new ExtUserInfoDemo();
//        account.setId(accountId);
//        query.setAccountInfo(account);
//        Mono<List<ExtUserInfoDemo>> monoList = dao.findByEntityWithRefQuery(query, null);
//        return asyncSendData(monoList.flatMap(list -> {
//            if (list.size() > 0) {
//                return Mono.just(list.get(0));
//            }
//            return Mono.empty();
//        }));
//    }
//
//    @Override
//    public ExtUserInfoDemo addUser(AlitaUserAccount account, ExtUserInfoDemo user) {
//        user.setId(new ObjectId());
//        BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
//        account.setUserPwd(pwdEncoder.encode(account.getUserPwd()));
////        accountService.save(account);
//        user.setAccountInfo(account);
//        return asyncSendData(dao.save(user));
//    }
//
//}
