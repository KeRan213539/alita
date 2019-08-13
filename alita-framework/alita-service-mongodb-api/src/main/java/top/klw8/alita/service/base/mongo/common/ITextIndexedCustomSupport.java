package top.klw8.alita.service.base.mongo.common;

/**
 * @ClassName: ITextIndexedCustomSupport
 * @Description: 全文索引支持,要全文索引的实体(自定义处理分词)需要实现此接口
 * @author klw
 * @date 2019年1月21日 下午4:33:36
 */
public interface ITextIndexedCustomSupport {

    /**
     * @Title: buildTextIndexedField
     * @author klw
     * @Description: 自定义索引字段中内容
     */
    public void buildTextIndexedField();
    
}
