package top.klw8.alita.service.base.entitys;

/**
 * @ClassName: ITextIndexedSupport
 * @Description: 全文索引支持,要全文索引的实体(底层处理分词)需要实现此接口
 * @author klw
 * @date 2019年1月18日 下午4:00:45
 */
public interface ITextIndexedSupport {

    /**
     * @Title: toTextIndexedText
     * @author klw
     * @Description: 获取将要被全文索引的文本,如果多个字段的值需要被索引,则返回这些字段内容的拼接后的字符串,字段内容之间用空格隔开
     * @return
     */
    public String toTextIndexedText();
    
    /**
     * @Title: analyzerResult2TextIndexedField
     * @author klw
     * @Description: 将分词结果放到实体对应字段中
     * @param analyzerResult
     */
    public void analyzerResult2TextIndexedField(String analyzerResult);
    
}
