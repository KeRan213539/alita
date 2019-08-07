package top.klw8.alita.starter.mongodb.base;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.bson.Document;
//import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
//import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
//
///**
// * @ClassName: LookupPipelineOperation
// * @Description: Lookup的Pipeline方式集合操作
// * @author klw
// * @date 2018年10月19日 下午3:39:22
// */
//public class LookupPipelineOperation implements AggregationOperation {
//    
//    private Document document;
//    
//    private LookupPipelineOperation(Document document) {
//	this.document = document;
//    }
//    
//    @Override
//    public Document toDocument(AggregationOperationContext context) {
//	return document;
//    }
//
//
//    public static FromBuilder newLookup() {
//	return new LookupPipelineOperationBuilder();
//    }
//
//    public static interface FromBuilder {
//
//	/**
//	 * @Title: from
//	 * @author klw
//	 * @Description: 要关联查询的集合(表)名称,必须与主集合(表)在同一个数据库,不能为空
//	 * @param name
//	 * @return
//	 */
//	LetBuilder from(String name);
//    }
//    
//    public static interface LetBuilder{
//	
//	/**
//	 * @Title: addLet
//	 * @author klw
//	 * @Description: 设置主表字段为变量(pipeline中不能访问主表字段,只能访问这里定义的变量)
//	 * @param letName  变量名
//	 * @param sourceField  主表字段名(不需要加 $ 符号)
//	 * @return
//	 */
//	LetBuilder addLet(String letName, String sourceField);
//	
//	/**
//	 * @Title: endLet
//	 * @author klw
//	 * @Description: 结束let的设置,开始设置 pipeline
//	 * @return
//	 */
//	PipelineMatchBuilder endLet();
//    }
//    
//    public static interface PipelineMatchBuilder{
//	
//	PipelineMatchBuilder and();
//	
//	PipelineMatchBuilder or();
//	
//	/**
//	 * @Title: eq
//	 * @author klw
//	 * @Description: 等于
//	 * @param fieldName  字段名
//	 * @param value  对比的值,如果是引用let中定义的变量,则传入 "$$变量名"
//	 * @return
//	 */
//	PipelineMatchBuilder eq(String fieldName, Object value);
//	
//	/**
//	 * @Title: nq
//	 * @author klw
//	 * @Description: 不等于
//	 * @param fieldName
//	 * @param value 对比的值,如果是引用let中定义的变量,则传入 "$$变量名"
//	 * @return
//	 */
//	PipelineMatchBuilder nq(String fieldName, Object value);
//	
//	/**
//	 * @Title: gt
//	 * @author klw
//	 * @Description: 大于
//	 * @param fieldName
//	 * @param value 对比的值,如果是引用let中定义的变量,则传入 "$$变量名"
//	 * @return
//	 */
//	PipelineMatchBuilder gt(String fieldName, Object value);
//	
//	/**
//	 * @Title: gte
//	 * @author klw
//	 * @Description: 大于等于
//	 * @param fieldName
//	 * @param value 对比的值,如果是引用let中定义的变量,则传入 "$$变量名"
//	 * @return
//	 */
//	PipelineMatchBuilder gte(String fieldName, Object value);
//	
//	/**
//	 * @Title: lt
//	 * @author klw
//	 * @Description: 小于
//	 * @param fieldName
//	 * @param value 对比的值,如果是引用let中定义的变量,则传入 "$$变量名"
//	 * @return
//	 */
//	PipelineMatchBuilder lt(String fieldName, Object value);
//	
//	/**
//	 * @Title: lte
//	 * @author klw
//	 * @Description: 小于等于
//	 * @param fieldName
//	 * @param value 对比的值,如果是引用let中定义的变量,则传入 "$$变量名"
//	 * @return
//	 */
//	PipelineMatchBuilder lte(String fieldName, Object value);
//	
//	
//	/**
//	 * @author klw
//	 * @Fields endCurrentCondition : 结束当前的 AND或者OR,退到上一层
//	 */
//	PipelineMatchBuilder endCurrentCondition();
//	
//	/**
//	 * @Title: endCondition2Top
//	 * @author klw
//	 * @Description: 结束当前的 AND或者OR,退到顶层
//	 * @return
//	 */
//	PipelineMatchBuilder endCondition2Top();
//	
//	/**
//	 * @Title: end
//	 * @author klw
//	 * @Description: 结束AND,进入project
//	 * @return
//	 */
//	PipelineProjectBuilder endMatch();
//    }
//    
//    /**
//     * @ClassName: PipelineProjectBuilder
//     * @Description: 用于过滤子查询中的返回结果中的字段, 不配制则默认全部返回
//     * 配制了的话_id也默认会返回,不需要_id需要对ID单独设置不返回,除了ID其他都默认不返回
//     * @author klw
//     * @date 2018年10月22日 上午10:05:48
//     */
//    public static interface PipelineProjectBuilder{
//	
//	/**
//	 * @Title: inResult
//	 * @author klw
//	 * @Description: 将指定字段设置为返回
//	 * @param fieldName
//	 * @return
//	 */
//	PipelineProjectBuilder inResult(String fieldName);
//	
//	/**
//	 * @Title: notInResult
//	 * @author klw
//	 * @Description: 将指定字段设置为不返回
//	 * @param fieldName
//	 * @return
//	 */
//	PipelineProjectBuilder notInResult(String fieldName);
//	
//	/**
//	 * @Title: renameInResult
//	 * @author klw
//	 * @Description: 将指定字段在结果中重命名-该方法需要在装查询结果bean中(例如实体)需要有重命名后的属性
//	 * @param sourceFieldName  原字段名
//	 * @param renameTo  重命名后
//	 * @return
//	 */
//	PipelineProjectBuilder renameInResult(String sourceFieldName, String renameTo);
//	
//	/**
//	 * @Title: end
//	 * @author klw
//	 * @Description: 结束 project, 进入 AS
//	 * @return
//	 */
//	AsBuilder endProject();
//    }
//    
//    public static interface AsBuilder {
//	/**
//	 * @Title: as
//	 * @author klw
//	 * @Description: 将lookup的结果放到主表实体中的那个属性中
//	 * @param name 属性名称
//	 * @return
//	 */
//	LookupPipelineOperation as(String name);
//    }
//    
//    public static final class LookupPipelineOperationBuilder 
//    		implements FromBuilder, LetBuilder, PipelineMatchBuilder, PipelineProjectBuilder, AsBuilder {
//	
//	private Document lookupObject = new Document();
//	
//	private Document let = new Document();
//	
//	List<Document> pipelineList = new ArrayList<>();
//	
//	private Document expr = null;
//	
//	private List<Document> matchTop = new ArrayList<>();
//	
//	private List<Document> pointer = new ArrayList<>();
//	
//	private LinkedList<List<Document>> pointerChain = new LinkedList<>();
//	
//	private Document project = null;
//	
//	public static FromBuilder newBuilder() {
//	    return new LookupPipelineOperationBuilder();
//	}
//	
//	@Override
//	public LetBuilder from(String name) {
//	    lookupObject.append("from", name);
//	    return this;
//	}
//	
//	@Override
//	public LetBuilder addLet(String letName, String sourceField) {
//	    let.append(letName, "$" + sourceField);
//	    return this;
//	}
//
//	@Override
//	public PipelineMatchBuilder endLet() {
//	    if(!let.isEmpty()) {
//		lookupObject.append("let", let);
//	    }
//	    return this;
//	}
//	
//	@Override
//	public PipelineMatchBuilder and() {
//	    if(expr == null) {
//		pointer = new ArrayList<>();
//		matchTop = pointer;
//		Document and = new Document("$and", pointer);
//		expr = new Document("$expr", and);
//	    } else {
//		List<Document> oldPointer = pointer;
//		pointer = new ArrayList<>();
//		oldPointer.add(new Document("$and", pointer));
//	    }
//	    pointerChain.addLast(pointer);
//	    return this;
//	}
//
//	@Override
//	public PipelineMatchBuilder or() {
//	    if(expr == null) {
//		pointer = new ArrayList<>();
//		matchTop = pointer;
//		Document or = new Document("$or", pointer);
//		expr = new Document("$expr", or);
//	    } else {
//		List<Document> oldPointer = pointer;
//		pointer = new ArrayList<>();
//		oldPointer.add(new Document("$or", pointer));
//	    }
//	    pointerChain.addLast(pointer);
//	    return this;
//	}
//	
//	private void addCondition(String conditionName, String fieldName, Object value) {
//	    List<Object> list = new ArrayList<>();
//	    list.add("$" + fieldName);
//	    list.add(value);
//	    pointer.add(new Document(conditionName, list));
//	}
//
//	@Override
//	public PipelineMatchBuilder eq(String fieldName, Object value) {
//	    addCondition("$eq", fieldName, value);
//	    return this;
//	}
//
//	@Override
//	public PipelineMatchBuilder nq(String fieldName, Object value) {
//	    addCondition("$nq", fieldName, value);
//	    return this;
//	}
//
//	@Override
//	public PipelineMatchBuilder gt(String fieldName, Object value) {
//	    addCondition("$gt", fieldName, value);
//	    return this;
//	}
//
//	@Override
//	public PipelineMatchBuilder gte(String fieldName, Object value) {
//	    addCondition("$gte", fieldName, value);
//	    return this;
//	}
//
//	@Override
//	public PipelineMatchBuilder lt(String fieldName, Object value) {
//	    addCondition("$lt", fieldName, value);
//	    return this;
//	}
//
//	@Override
//	public PipelineMatchBuilder lte(String fieldName, Object value) {
//	    addCondition("$lte", fieldName, value);
//	    return this;
//	}
//
//	@Override
//	public PipelineMatchBuilder endCurrentCondition() {
//	    if (pointerChain.isEmpty()) {
//		pointer = matchTop;
//		return this;
//	    }
//	    pointerChain.removeLast();
//	    pointer = pointerChain.getLast();
//	    return this;
//	}
//
//	@Override
//	public PipelineMatchBuilder endCondition2Top() {
//	    pointerChain.clear();
//	    pointer = matchTop;
//	    return this;
//	}
//
//	@Override
//	public PipelineProjectBuilder endMatch() {
//	    pipelineList.add(new Document("$match",expr));
//	    project = new Document();
//	    return this;
//	}
//	
//	@Override
//	public PipelineProjectBuilder inResult(String fieldName) {
//	    project.put(fieldName, 1);
//	    return this;
//	}
//
//	@Override
//	public PipelineProjectBuilder notInResult(String fieldName) {
//	    project.put(fieldName, 0);
//	    return this;
//	}
//
//	@Override
//	public PipelineProjectBuilder renameInResult(String sourceFieldName, String renameTo) {
//	    project.put(renameTo, "$" + sourceFieldName);
//	    return this;
//	}
//
//	@Override
//	public AsBuilder endProject() {
//	    pipelineList.add(new Document("$project",project));
//	    lookupObject.append("pipeline", pipelineList);
//	    return this;
//	}
//	
//	@Override
//	public LookupPipelineOperation as(String name) {
//	    lookupObject.append("as", name);
//	    return new LookupPipelineOperation(new Document("$lookup", lookupObject));
//	}
//
//    }
//    
//}
