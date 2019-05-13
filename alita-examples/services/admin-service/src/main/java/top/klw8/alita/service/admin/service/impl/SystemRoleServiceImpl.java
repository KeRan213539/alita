package top.klw8.alita.service.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.apache.dubbo.config.annotation.Service;
import org.bson.types.ObjectId;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.base.mongodb.MongoDBConstant;
import top.klw8.alita.entitys.authority.SystemAuthoritys;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.service.admin.dao.ISystemRoleDao;
import top.klw8.alita.service.api.authority.ISystemRoleService;
import top.klw8.alita.starter.service.BaseServiceImpl;

/**
 * @author klw
 * @ClassName: SystemRoleServiceImpl
 * @Description: 系统角色Service_实现
 * @date 2018年11月28日 下午3:53:01
 */
@Slf4j
@Service(async = true)
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRole> implements ISystemRoleService {

    private ISystemRoleDao dao;

    public SystemRoleServiceImpl(@Autowired ISystemRoleDao dao) {
        super(dao);
        this.dao = dao;
    }

    public Integer addAuthority2Role(ObjectId roleId, SystemAuthoritys au) {
        if (null == roleId || au == null || null == au.getId()) {
            return 0;
        }
        Update update = dao.createUpdateOperations();
        update.addToSet("authorityList", au);
        return asyncSendData(dao.updateByQuery(Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(roleId)), update));
    }

    public Integer removeAuthorityFromRole(ObjectId roleId, SystemAuthoritys au) {
        if (null == roleId || au == null || null == au.getId()) {
            return 0;
        }
        Update update = dao.createUpdateOperations();
        update.pull("authorityList", au);
        return asyncSendData(dao.updateByQuery(Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(roleId)), update));
    }

    public Integer replaceAuthority2Role(ObjectId roleId, List<SystemAuthoritys> auList) {
        if (null == roleId || CollectionUtils.isEmpty(auList)) {
            return 0;
        }
        Document collectionDoc = auList.get(0).getClass().getAnnotation(Document.class);
        String auCollectionName = collectionDoc.collection();
        List<org.bson.Document> auDocList = new ArrayList<>();
        for (SystemAuthoritys au : auList) {
            org.bson.Document auRefDoc = new org.bson.Document();
            auRefDoc.append("$ref", auCollectionName);
            auRefDoc.append("$id", au.getId());
            auDocList.add(auRefDoc);
        }
        Update update = dao.createUpdateOperations();
        update.set("authorityList", auDocList);
        return asyncSendData(dao.updateByQuery(Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(roleId)), update));
    }
}
