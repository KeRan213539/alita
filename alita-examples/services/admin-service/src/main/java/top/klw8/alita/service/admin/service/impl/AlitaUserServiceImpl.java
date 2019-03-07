package top.klw8.alita.service.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import lombok.extern.slf4j.Slf4j;
import top.klw8.alita.base.mongodb.MongoDBConstant;
import top.klw8.alita.entitys.authority.SystemRole;
import top.klw8.alita.entitys.user.AlitaUserAccount;
import top.klw8.alita.service.admin.dao.IAlitaUserDao;
import top.klw8.alita.service.api.user.IAlitaUserService;
import top.klw8.alita.starter.service.BaseServiceImpl;

/**
 * @ClassName: AlitaUserServiceImpl
 * @Description: 用户服务
 * @author klw
 * @date 2018年11月9日 下午5:32:24
 */
@Slf4j
@Service(async=true)
public class AlitaUserServiceImpl extends BaseServiceImpl<AlitaUserAccount> implements IAlitaUserService {
    
    private IAlitaUserDao dao;
    
    public AlitaUserServiceImpl(@Autowired IAlitaUserDao dao) {
	super(dao);
	this.dao = dao;
    }
    
    public int addRole2User(Long userId, SystemRole role) {
	if(null == userId || role == null || null == role.getId()) {
	    return 0;
	}
	Update update = dao.createUpdateOperations();
	update.addToSet("userRoles", role);
	return asyncSendData(dao.updateByQuery(Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(userId)), update));
    }
    
    public int removeRoleFromUser(Long userId, SystemRole role) {
	if(null == userId || role == null || null == role.getId()) {
	    return 0;
	}
	Update update = dao.createUpdateOperations();
	update.pull("userRoles", role);
	return asyncSendData(dao.updateByQuery(Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(userId)), update));
    }
    
    public int replaceRole2User(Long userId, List<SystemRole> roleList) {
	if(null == userId || CollectionUtils.isEmpty(roleList)) {
	    return 0;
	}
	Document collectionDoc = roleList.get(0).getClass().getAnnotation(Document.class);
	String collectionName = collectionDoc.collection();
	List<org.bson.Document> docList = new ArrayList<>();
	for(SystemRole role : roleList) {
	    org.bson.Document auRefDoc = new org.bson.Document();
	    auRefDoc.append("$ref", collectionName);
	    auRefDoc.append("$id", role.getId());
	    docList.add(auRefDoc);
	}
	Update update = dao.createUpdateOperations();
	update.set("userRoles", docList);
	return asyncSendData(dao.updateByQuery(Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(userId)), update));
    }

}
