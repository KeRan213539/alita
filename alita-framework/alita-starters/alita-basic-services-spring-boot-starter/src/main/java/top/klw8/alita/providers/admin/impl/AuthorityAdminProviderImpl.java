package top.klw8.alita.providers.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import top.klw8.alita.entitys.authority.*;
import top.klw8.alita.entitys.authority.enums.AuthorityTypeEnum;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.helper.UserCacheHelper;
import top.klw8.alita.service.api.authority.IAuthorityAdminProvider;
import top.klw8.alita.service.authority.*;
import top.klw8.alita.service.pojos.SystemAuthorityPojo;
import top.klw8.alita.service.pojos.SystemAuthorityCatlogPojo;
import top.klw8.alita.service.pojos.SystemDataSecuredPojo;
import top.klw8.alita.service.result.JsonResult;
import top.klw8.alita.service.result.code.AuthorityResultCodeEnum;
import top.klw8.alita.service.utils.EntityUtil;
import top.klw8.alita.starter.service.common.ServiceContext;
import top.klw8.alita.starter.service.common.ServiceUtil;
import top.klw8.alita.utils.AuthorityUtil;
import top.klw8.alita.utils.BeanUtil;
import top.klw8.alita.utils.UUIDUtil;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author klw(213539 @ qq.com)
 * @ClassName: AuthorityAdminProviderImpl
 * @Description: 权限相关dubbo服务提供者
 * @date 2019/8/14 9:33
 */
@Slf4j
@Service(async = true)
public class AuthorityAdminProviderImpl implements IAuthorityAdminProvider {

    /**
     * @author klw(213539@qq.com)
     * @Description: 全局数据权限用的catlog id
     */
    private static final String ID_PUBLIC_DATA_SECURED_CATLOG = "PUBLIC_DATA_SECURED_CATLOG";

    /**
     * @author klw(213539@qq.com)
     * @Description: 全局数据权限用的权限ID
     */
    private static final String ID_PUBLIC_DATA_SECURED_AUTHORITY = "PUBLIC_DATA_SECURED_AUTHORITY";

    @Autowired
    private ISystemAuthoritysService auService;

    @Autowired
    private ISystemDataSecuredService dsService;

    @Autowired
    private ISystemAuthoritysCatlogService catlogService;

    @Autowired
    private ISystemRoleService roleService;

    @Autowired
    private IAlitaUserService userService;

    @Autowired
    private IAuthorityAppService appService;

    @Autowired
    private UserCacheHelper userCacheHelper;

    @Override
    public CompletableFuture<JsonResult> addAuthority(SystemAuthoritys au) {
        return CompletableFuture.supplyAsync(() -> {
            SystemAuthoritysCatlog catlog = catlogService.getById(au.getCatlogId());
            if (catlog == null) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.CATLOG_NOT_EXIST, "权限目录不存在【" + au.getCatlogId() + "】");
            }
            if(StringUtils.isBlank(au.getId())) {
                au.setId(UUIDUtil.getRandomUUIDString());
            }
            boolean isSaved = auService.save(au);
            if (isSaved) {
                return JsonResult.sendSuccessfulResult();
            }
            return JsonResult.sendFailedResult("保存失败");
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addCatlog(SystemAuthoritysCatlog catlog) {
        return CompletableFuture.supplyAsync(() -> {
            if (catlogService.save(catlog)) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult("保存失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addSysRole(SystemRole role) {
        return CompletableFuture.supplyAsync(() -> {
            if (roleService.save(role)) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult("保存失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addRoleAu(String roleId, String auId) {
        return CompletableFuture.supplyAsync(() -> {
            SystemRole role = roleService.getById(roleId);

            if (EntityUtil.isEntityNoId(role)) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.ROLE_NOT_EXIST);
            }
            SystemAuthoritys au = auService.getById(auId);
            if (EntityUtil.isEntityNoId(au)) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.AUTHORITY_NOT_EXIST);
            }
            int saveResult = roleService.addAuthority2Role(role.getId(), au);
            if (saveResult > 0) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult("添加失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> addUserRole(String userId, String roleId) {
        return CompletableFuture.supplyAsync(() -> {
            AlitaUserAccount user = userService.getById(userId);
            if (EntityUtil.isEntityNoId(user)) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.USER_NOT_EXIST);
            }
            SystemRole role = roleService.getById(roleId);
            if (EntityUtil.isEntityNoId(role)) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.ROLE_NOT_EXIST);
            }
            int saveResult = userService.addRole2User(user.getId(), role.getId());
            if (saveResult > 0) {
                return JsonResult.sendSuccessfulResult();
            } else {
                return JsonResult.sendFailedResult("添加失败");
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> refreshUserAuthoritys(String userId) {
        //查询用户,把权限查出来
        return CompletableFuture.supplyAsync(() -> {
            // 根据用户ID查询到用户信息
            AlitaUserAccount sysUser = userService.getById(userId);
            // 判断是否查询到用户
            if (EntityUtil.isEntityNoId(sysUser)) {
                return JsonResult.sendFailedResult(AuthorityResultCodeEnum.USER_NOT_EXIST);
            } else {
                // 根据用户ID查询用户角色
                List<SystemRole> userRoles = userService.getUserAllRoles(userId, null);

                for (SystemRole role : userRoles) {
                    // 根据用户角色查询角色对应的权限并更新到SystemRole实体中
                    List<SystemAuthoritys> authoritys = roleService.getRoleAllAuthoritys(role.getId());
                    role.setAuthorityList(authoritys);

                    // 根据用户角色查询角色对应的数据权限并更新到SystemRole实体中
                    List<SystemDataSecured> dataSecureds = roleService.getRoleAllDataSecureds(role.getId());
                    role.setDataSecuredList(dataSecureds);
                }
                // 更新用户角色权限到用户实体中
                if (null != userRoles && !userRoles.isEmpty()) {
                    sysUser.setUserRoles(userRoles);
                }
                userCacheHelper.putUserInfo2Cache(sysUser);
                return JsonResult.sendSuccessfulResult();
            }
        }, ServiceContext.executor);
    }

    @Override
    public CompletableFuture<JsonResult> roleList(String roleName, String appTag, Page<SystemRole> page) {
        QueryWrapper<SystemRole> query = new QueryWrapper();
        if(StringUtils.isNotBlank(roleName)){
            query.like("role_name", roleName);
        }
        if(StringUtils.isNotBlank(appTag)){
            query.eq("app_tag", appTag);
        }
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.asc("role_name"));
        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(
                roleService.page(page, query)));
    }

    @Override
    public CompletableFuture<JsonResult> roleAll(String appTag) {
        QueryWrapper<SystemRole> query = new QueryWrapper();
        if(StringUtils.isBlank(appTag)){
            query.orderByAsc("app_tag", "role_name");
        } else {
            query.eq("app_tag", appTag);
            query.orderByAsc("role_name");
        }
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(roleService.list(query)));
    }

    @Override
    public CompletableFuture<JsonResult> markRoleAuthoritys(String roleId, String appTag) {
        List<SystemAuthoritys> roleAuList = new LinkedList<>();
        List<SystemDataSecured> roleDsList = new LinkedList<>();
        if (StringUtils.isNotBlank(roleId)) {
            SystemRole role = roleService.getById(roleId);
            Assert.notNull(role, "该角色不存在!");
            //该角色拥有的权限
            roleAuList.addAll(roleService.getRoleAllAuthoritys(role.getId()));
            //该角色拥有的数据权限
            roleDsList.addAll(roleService.getRoleAllDataSecureds(role.getId()));
        }

        // 构造查询条件
        QueryWrapper<SystemAuthoritys> auSuery = new QueryWrapper();
        QueryWrapper<SystemDataSecured> dsQuery = new QueryWrapper();
        auSuery.orderByAsc("show_index");
        dsQuery.orderByAsc("authoritys_id");
        if(StringUtils.isNotBlank(appTag)){
            auSuery.eq("app_tag", appTag);
            dsQuery.eq("app_tag", appTag);
        }

        // 查询权限
        List<SystemAuthoritys> allAuList = auService.list();
        // 查询数据权限
        List<SystemDataSecured> allDsList = dsService.list();

        // 根据权限分组的非全局数据权限Map<权限ID: List<SystemDataSecuredPojo>
        Map<String, List<SystemDataSecuredPojo>> dsListGroupByAu = new HashMap<>(16);
        // 全局数据权限List
        List<SystemDataSecuredPojo> publicDsList = new ArrayList<>(16);
        // 数据权限Map<数据权限ID: SystemDataSecuredPojo>
        Map<String, SystemDataSecuredPojo> allDsMap = new HashMap<>(16);

        if(CollectionUtils.isNotEmpty(allDsList)){
            for(SystemDataSecured ds : allDsList){
                SystemDataSecuredPojo dsPojo = new SystemDataSecuredPojo();
                BeanUtils.copyProperties(ds, dsPojo);
                // 数据权限加个特殊标记
                dsPojo.setId(ISystemRoleService.DS_ID_PREFIX + dsPojo.getId());
                if(StringUtils.isBlank(dsPojo.getAuthoritysId())){
                    // 全局数据权限
                    publicDsList.add(dsPojo);
                } else {
                    // 非全局数据权限
                    List<SystemDataSecuredPojo> auDsList = dsListGroupByAu.get(dsPojo.getAuthoritysId());
                    if(auDsList == null){
                        auDsList = new ArrayList<>(0);
                        dsListGroupByAu.put(dsPojo.getAuthoritysId(), auDsList);
                    }
                    auDsList.add(dsPojo);
                }
                allDsMap.put(dsPojo.getId(), dsPojo);
            }
        }

        // 处理角色拥有的数据权限
        if(CollectionUtils.isNotEmpty(roleDsList)) {
            Iterator<SystemDataSecured> roleDsIt = roleDsList.iterator();
            while (roleDsIt.hasNext()) {
                SystemDataSecured roleDs = roleDsIt.next();
                SystemDataSecuredPojo dsPojo = allDsMap.get(roleDs.getId());
                if(null != dsPojo){
                    dsPojo.setCurrUserHas(true);
                }
            }
        }


        // 把全局数据权限构造一个catlog和一个Au放进去
        SystemAuthorityCatlogPojo publicDsCatlog = new SystemAuthorityCatlogPojo();
        publicDsCatlog.setId(ID_PUBLIC_DATA_SECURED_CATLOG);
        publicDsCatlog.setCatlogName("全局数据权限");
        publicDsCatlog.setShowIndex(0);
        SystemAuthorityPojo publicDsAuPojo = new SystemAuthorityPojo();
        publicDsAuPojo.setId(ID_PUBLIC_DATA_SECURED_AUTHORITY);
        publicDsAuPojo.setAuthorityName("全局数据权限");
        publicDsAuPojo.setDsList(publicDsList);
        publicDsCatlog.setAuthorityList(Lists.newArrayList(publicDsAuPojo));

        Map<String, SystemAuthorityCatlogPojo> catlogMap = new HashMap<>(16);
        catlogMap.put(ID_PUBLIC_DATA_SECURED_CATLOG, publicDsCatlog);

        // 遍历权限和权限下的数据权限转为视图bean,并标记传入的角色是否拥有该权限
        for(SystemAuthoritys sysAu : allAuList){
            SystemAuthorityCatlogPojo catlogPojo = catlogMap.get(sysAu.getCatlogId());
            if (catlogPojo == null) {
                SystemAuthoritysCatlog catlog = catlogService.getById(sysAu.getCatlogId());
                catlogPojo = new SystemAuthorityCatlogPojo();
                BeanUtils.copyProperties(catlog, catlogPojo);
                catlogPojo.setAuthorityList(new ArrayList<>(16));
                catlogMap.put(catlogPojo.getId(), catlogPojo);
            }
            SystemAuthorityPojo auPojo = new SystemAuthorityPojo();
            BeanUtils.copyProperties(sysAu, auPojo);
            // 查找当前角色是否有该权限
            if(CollectionUtils.isNotEmpty(roleAuList)) {
                Iterator<SystemAuthoritys> roleAuIt = roleAuList.iterator();
                while (roleAuIt.hasNext()) {
                    SystemAuthoritys roleAu = roleAuIt.next();
                    if (roleAu.getId().equals(auPojo.getId())) {
                        auPojo.setCurrUserHas(true);
                    }
                }
            }
            // 数据权限放入权限
            auPojo.setDsList(dsListGroupByAu.get(auPojo.getId()));
            catlogPojo.getAuthorityList().add(auPojo);
        }

        List<SystemAuthorityCatlogPojo> catlogPojoList = new ArrayList<>(catlogMap.values());
        Collections.sort(catlogPojoList);
        catlogPojoList.stream().forEach(systemAuthorityCatlogPojo -> Collections.sort(systemAuthorityCatlogPojo.getAuthorityList()));
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(catlogPojoList));
    }

    @Override
    public CompletableFuture<JsonResult> saveRoleAuthoritys(String roleId, List<String> auIds) {
        if(roleService.getById(roleId) == null){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("角色不存在"));
        }
        for(String auId : auIds){
            Object finded;
            if(auId.startsWith(ISystemRoleService.DS_ID_PREFIX)){
                finded = dsService.getById(auId.replace(ISystemRoleService.DS_ID_PREFIX, ""));
            } else {
                finded = auService.getById(auId);
            }
            if(null == finded){
                return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("权限不存在"));
            }
        }
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(roleService.replaceAuthority2Role(roleId, auIds)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<JsonResult> saveRole(SystemRole role, String copyAuFromRoleId) {
        Assert.notNull(role, "要保存的角色不能为 null !!!");

        SystemAuthoritysApp app = appService.getById(role.getAppTag());
        if(null == app){
            return ServiceUtil.buildFuture(JsonResult.sendFailedResult("appTag 对应的应用不存在"));
        }

        if(StringUtils.isNotBlank(role.getId())){
            roleService.updateById(role);
        } else {
            if(CollectionUtils.isNotEmpty(role.getAuthorityList())){
                role.setId(UUIDUtil.getRandomUUIDString());
            }
            roleService.save(role);
        }
        boolean isCopy = false;
        if(StringUtils.isNotBlank(copyAuFromRoleId)){
            // 根据 copyAuFromRoleId 查找该角色的权限并设置到要保存的角色中
            role.setAuthorityList(roleService.getRoleAllAuthoritys(copyAuFromRoleId));
            role.setDataSecuredList(roleService.getRoleAllDataSecureds(copyAuFromRoleId));
            isCopy = true;
        }
        if(CollectionUtils.isNotEmpty(role.getAuthorityList())){
            // 保存角色中的权限
            int dsSize = CollectionUtils.isEmpty(role.getDataSecuredList()) ? 0 : role.getDataSecuredList().size();
            List<String> auIdList = new ArrayList<>(role.getAuthorityList().size() + dsSize);
            for(SystemAuthoritys au : role.getAuthorityList()){
                if(!isCopy && auService.getById(au.getId()) == null){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("权限不存在"));
                }
                auIdList.add(au.getId());
            }
            if(dsSize > 0){
                for(SystemDataSecured ds : role.getDataSecuredList()){
                    auIdList.add(ISystemRoleService.DS_ID_PREFIX + ds.getId());
                }
            }
            roleService.replaceAuthority2Role(role.getId(), auIdList);
        }
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<JsonResult> delRole(String roleId) {
        Assert.hasText(roleId, "角色ID不能为空!");
        List<AlitaUserAccount> userList = userService.getUserByRoleId(roleId);
        if(CollectionUtils.isNotEmpty(userList)){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("有用户拥有该角色,不允许删除"));
        }
        roleService.cleanAuthoritysFromRole(roleId);
        roleService.removeById(roleId);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    public CompletableFuture<JsonResult> roleInfo(String roleId){
        Assert.hasText(roleId, "角色ID不能为空!");
        SystemRole role = roleService.getById(roleId);


        List<SystemAuthoritys> roleAuList = roleService.selectSystemAuthoritysWithCatlogByRoleId(role.getId());
        Map<String, Object> tempResult = new HashMap<>(BeanUtil.beanToMap(role));
        tempResult.entrySet().removeIf(entry -> entry.getValue() == null);
        Map<String, Map<String, Object>> catlogListMap = new HashMap<>(16);
        Map<String, SystemAuthoritys> auListMap = new HashMap<>(16);
        for(SystemAuthoritys auInRole : roleAuList) {
            Map<String, Object> catlogMap = catlogListMap.get(auInRole.getCatlogId());
            if (catlogMap == null) {
                catlogMap = new HashMap<>(3);
                catlogListMap.put(auInRole.getCatlogId(), catlogMap);
                catlogMap.put("id", auInRole.getCatlogId());
                catlogMap.put("catlogName", auInRole.getCatlogName());
                catlogMap.put("auList", new ArrayList<SystemAuthoritys>(16));
            }
            ((List<SystemAuthoritys>) catlogMap.get("auList")).add(auInRole);
            auListMap.put(auInRole.getId(), auInRole);
        }
        tempResult.put("catlogList", catlogListMap.values().stream().collect(Collectors.toList()));

        List<SystemDataSecured> roleDsList = roleService.getRoleAllDataSecureds(role.getId());
        for(SystemDataSecured dsInRole : roleDsList) {
            SystemAuthoritys au = auListMap.get(dsInRole.getAuthoritysId());
            if(au != null){
                List<SystemDataSecured> dsInAuList = au.getDataSecuredList();
                if(dsInAuList == null){
                    dsInAuList = new ArrayList<>(16);
                    au.setDataSecuredList(dsInAuList);
                }
                dsInAuList.add(dsInRole);
            }
        }

        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(tempResult));
    }

    @Override
    public CompletableFuture<JsonResult> catlogList(String catlogName, String appTag, Page<SystemAuthoritysCatlog> page) {
        QueryWrapper<SystemAuthoritysCatlog> query = new QueryWrapper();
        if(StringUtils.isNotBlank(catlogName)){
            query.like("catlog_name", catlogName);
        }
        if(StringUtils.isNotBlank(appTag)){
            query.eq("app_tag", appTag);
        }
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.asc("show_index"));
        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(
                catlogService.page(page, query)));
    }

    @Override
    public CompletableFuture<JsonResult> catlogAll(String appTag) {
        QueryWrapper<SystemAuthoritysCatlog> query = new QueryWrapper();
        if(StringUtils.isNotBlank(appTag)){
            query.eq("app_tag", appTag);
        }
        query.orderByAsc("show_index");
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(
                catlogService.list(query)));
    }

    @Override
    public CompletableFuture<JsonResult> saveCatlog(SystemAuthoritysCatlog catlog) {
        Assert.notNull(catlog, "要保存的权限目录不能为 null !!!");
        SystemAuthoritysApp app = appService.getById(catlog.getAppTag());
        if(null == app){
            return ServiceUtil.buildFuture(JsonResult.sendFailedResult("appTag 对应的应用不存在"));
        }
        if(StringUtils.isNotBlank(catlog.getId())){
            catlogService.updateById(catlog);
        } else {
            catlogService.save(catlog);
        }
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    public CompletableFuture<JsonResult> delCatlog(String catlogId) {
        Assert.hasText(catlogId, "权限目录ID不能为空!");
        QueryWrapper<SystemAuthoritys> auQuery = new QueryWrapper();
        auQuery.eq("catlog_id", catlogId);
        int auCountByCatlogId = auService.count(auQuery);
        if(auCountByCatlogId > 0){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("该目录下有权限,不允许删除"));
        }
        catlogService.removeById(catlogId);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    public CompletableFuture<JsonResult> catlogInfo(String catlogId){
        Assert.hasText(catlogId, "权限目录ID不能为空!");
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(catlogService.getById(catlogId)));
    }

    @Override
    public CompletableFuture<JsonResult> authoritysList(String auName, AuthorityTypeEnum auType,
                                                        Page<SystemAuthoritys> page, String authorityAction,
                                                        String catlogName, String appTag) {
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(
                auService.selectSystemAuthoritysList(page,auName, auType == null ? null : auType.name(),
                        authorityAction, catlogName, appTag)));
    }

    @Override
    public CompletableFuture<JsonResult> saveAuthority(SystemAuthoritys au, String httpMethod) {
        Assert.notNull(au, "要保存的权限不能为 null !!!");
        Assert.hasText(au.getCatlogId(), "所属权限目录ID不能为空!");

        SystemAuthoritysCatlog catlog = catlogService.getById(au.getCatlogId());
        if(null == catlog){
            return ServiceUtil.buildFuture(JsonResult.sendFailedResult("所属权限目录不存在 !!!"));
        }

        au.setAppTag(catlog.getAppTag());

        if(AuthorityTypeEnum.URL.equals(au.getAuthorityType())) {
            Assert.hasText(httpMethod, "httpMethod 不能为空");
            au.setAuthorityAction(AuthorityUtil.composeWithSeparator2(httpMethod, au.getAuthorityAction()));
        }
        if(StringUtils.isNotBlank(au.getId())){
            auService.updateById(au);
        } else {
            auService.save(au);
        }
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<JsonResult> delAuthority(String auId) {
        Assert.hasText(auId, "权限ID不能为空!");
        if(roleService.countByAuId(auId) > 0){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("该权限已被角色关联,禁止删除"));
        }
        if(dsService.countByAuId(auId) > 0){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("该权限下有数据权限,禁止删除"));
        }
        auService.removeById(auId);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    public CompletableFuture<JsonResult> auInfo(String auId){
        Assert.hasText(auId, "权限ID不能为空!");
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(auService.getById(auId)));
    }

    @Override
    public CompletableFuture<JsonResult> dataSecuredsByAuthorityAction(String httpMethod, String auAction) {
        Assert.hasText(auAction, "权限路径不能为空!");
        Assert.hasText(httpMethod, "httpMethod不能为空!");

        if(auAction.startsWith("http")){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("权限路径格式不正确,请传入服务端返回的!"));
        }
        SystemAuthoritys au = auService.findByAuAction(AuthorityUtil.composeWithSeparator2(httpMethod, auAction));
        if(EntityUtil.isEntityNoId(au)){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("权限路径格对应的权限不存在!"));
        }
        List<SystemDataSecured> auDsList = dsService.findByAuId(au.getId());
        List<SystemDataSecured> publicDsList = dsService.findByAuId(null);
        auDsList.addAll(publicDsList);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(publicDsList));
    }

    public CompletableFuture<JsonResult> allAuthoritysWithCatlog(String appTag){
        List<SystemAuthoritys> allAuList = auService.selectAllSystemAuthoritysWithCatlog(appTag);
        Map<String, Map<String, Object>> tempResult = new HashMap<>(16);
        for(SystemAuthoritys au : allAuList){
            Map<String, Object> catlogMap = tempResult.get(au.getCatlogId());
            if(catlogMap == null){
                catlogMap = new HashMap<>(3);
                tempResult.put(au.getCatlogId(), catlogMap);
                catlogMap.put("id", au.getCatlogId());
                catlogMap.put("catlogName", au.getCatlogName());
                catlogMap.put("auList", new ArrayList<SystemAuthoritys>(16));
            }
            ((List<SystemAuthoritys>) catlogMap.get("auList")).add(au);
        }
        List<Map<String, Object>> result = new ArrayList<>(tempResult.size() + 1);

        // 把全局数据权限构造一个catlog和一个Au放进去
        Map<String, Object> publicCatlogMap = new HashMap<>(3);
        result.add(publicCatlogMap);
        publicCatlogMap.put("id", ID_PUBLIC_DATA_SECURED_CATLOG);
        publicCatlogMap.put("catlogName", "全局数据权限");

        SystemAuthoritys publicDsAu = new SystemAuthoritys();
        publicDsAu.setId(ID_PUBLIC_DATA_SECURED_AUTHORITY);
        publicDsAu.setAuthorityName("全局数据权限");
        publicCatlogMap.put("auList", Lists.newArrayList(publicDsAu));

        tempResult.forEach((k, v) -> result.add(v));
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(result));
    }

    @Override
    public CompletableFuture<JsonResult> dataSecuredList(String resource, String appTag, Page<SystemDataSecured> page) {
        QueryWrapper<SystemDataSecured> query = new QueryWrapper();
        if(StringUtils.isNotBlank(resource)){
            query.like("resource", resource);
        }
        if(StringUtils.isNotBlank(appTag)){
            query.eq("app_tag", appTag);
        }
        List<OrderItem> orders = new ArrayList<>(1);
        orders.add(OrderItem.desc("resource"));
        page.setOrders(orders);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(
                dsService.page(page, query)));
    }

    @Override
    public CompletableFuture<JsonResult> saveDataSecured(SystemDataSecured ds){
        Assert.notNull(ds, "要保存的数据权限不能为空!!!");

        // 如果所属权限ID是全局的ID,设为null
        if(null != ds.getAuthoritysId() && ID_PUBLIC_DATA_SECURED_AUTHORITY.equals(ds.getAuthoritysId())){
            ds.setAuthoritysId(null);
            // 全局数据权限,检查app是否存在
            Assert.notNull(ds.getAppTag(), "全局数据权限需要 appTag !");
            SystemAuthoritysApp app = appService.getById(ds.getAppTag());
            if(null == app){
                return ServiceUtil.buildFuture(JsonResult.sendFailedResult("appTag 对应的应用不存在"));
            }
        }
        // 如果所属权限ID不为空,检查权限是否存在
        if(StringUtils.isNotBlank(ds.getAuthoritysId())){
            SystemAuthoritys au = auService.getById(ds.getAuthoritysId());
            if(null == au){
                return ServiceUtil.buildFuture(JsonResult.sendFailedResult(AuthorityResultCodeEnum.AUTHORITY_NOT_EXIST, "权限不存在【" + ds.getAuthoritysId() + "】"));
            }
            // 非全局数据权限, appTag 从权限中取
            ds.setAppTag(au.getAppTag());
        }

        if(StringUtils.isNotBlank(ds.getId())){
            dsService.updateById(ds);
        } else {
            // 检查该数据权限是否存在(所属权限ID+权限标识)
            if(EntityUtil.isEntityHasId(dsService.findByResourceAndAuId(ds.getResource(), ds.getAuthoritysId()))){
                return ServiceUtil.buildFuture(JsonResult.sendFailedResult(AuthorityResultCodeEnum.SYSTEM_DATA_SECURED_HAS_EXIST));
            }
            dsService.save(ds);
        }
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    public CompletableFuture<JsonResult> delDataSecured(String dsId) {
        Assert.hasText(dsId, "数据权限ID不能为空!");
        if(roleService.countByDsId(dsId) > 0){
            return ServiceUtil.buildFuture(JsonResult.sendBadParameterResult("该数据权限已与角色关联,不允许删除"));
        }
        dsService.removeById(dsId);
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult());
    }

    @Override
    public CompletableFuture<JsonResult> dataSecuredInfo(String dsId){
        Assert.hasText(dsId, "数据权限ID不能为空!");
        return ServiceUtil.buildFuture(JsonResult.sendSuccessfulResult(dsService.getById(dsId)));
    }

}
