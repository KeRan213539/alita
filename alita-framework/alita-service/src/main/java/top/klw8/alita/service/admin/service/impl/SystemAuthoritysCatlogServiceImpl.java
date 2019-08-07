package top.klw8.alita.service.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import top.klw8.alita.entitys.authority.SystemAuthoritysCatlog;
import top.klw8.alita.service.admin.dao.ISystemAuthoritysCatlogDao;
import top.klw8.alita.service.api.authority.ISystemAuthoritysCatlogService;
import top.klw8.alita.starter.service.BaseServiceImpl;

/**
 * @ClassName: SystemAuthoritysCatlogServiceImpl
 * @Description: 权限的目录Service_实现
 * @author klw
 * @date 2018年11月28日 下午3:52:21
 */
@Slf4j
@Service(async=true)
public class SystemAuthoritysCatlogServiceImpl extends BaseServiceImpl<SystemAuthoritysCatlog> implements ISystemAuthoritysCatlogService {

    private ISystemAuthoritysCatlogDao dao;
    
    public SystemAuthoritysCatlogServiceImpl(@Autowired ISystemAuthoritysCatlogDao dao) {
	super(dao);
	this.dao = dao;
    }
    
    public Integer addAuthority2Catlog(ObjectId catlogId, SystemAuthoritys au) {
	if(null == catlogId || au == null || null == au.getId()) {
	    return 0;
	}
	Update update = dao.createUpdateOperations();
	update.addToSet("authorityList", au);
	return asyncSendData(dao.updateByQuery(Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(catlogId)), update));
    }
    
    public Integer removeAuthorityFromCatlog(ObjectId catlogId, SystemAuthoritys au) {
	if(null == catlogId || au == null || null == au.getId()) {
	    return 0;
	}
	Update update = dao.createUpdateOperations();
	update.pull("authorityList", au);
	return asyncSendData(dao.updateByQuery(Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(catlogId)), update));
    }
    
    public Integer replaceAuthority2Catlog(ObjectId catlogId, List<SystemAuthoritys> auList) {
	if(null == catlogId || null == auList) {
	    return 0;
	}
	Document collectionDoc = auList.get(0).getClass().getAnnotation(Document.class);
	String auCollectionName = collectionDoc.collection();
	List<org.bson.Document> auDocList = new ArrayList<>();
	for(SystemAuthoritys au : auList) {
	    org.bson.Document auRefDoc = new org.bson.Document();
	    auRefDoc.append("$ref", auCollectionName);
	    auRefDoc.append("$id", au.getId());
	    auDocList.add(auRefDoc);
	}
	Update update = dao.createUpdateOperations();
	update.set("authorityList", auDocList);
	return asyncSendData(dao.updateByQuery(Query.query(Criteria.where(MongoDBConstant.ID_KEY).is(catlogId)), update));
    }
    
    public SystemAuthoritysCatlog findByCatlogName(String catlogName) {
	Query query = dao.createQuery().addCriteria(Criteria.where("catlogName").is(catlogName));
	return asyncSendData(dao.getMongoTemplate().findOne(query, SystemAuthoritysCatlog.class));
    }
    
}
