package top.klw8.alita.starter.mongodb.base;
//
//import org.bson.Document;
//import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
//import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;
//
//
///**
// * @ClassName: CustomAggregationOperation
// * @Description: 自定义集合操作
// * @author klw
// * @date 2018年10月18日 下午4:27:28
// */
//public class CustomAggregationOperation implements AggregationOperation {
//    private Document document;
//
//    public CustomAggregationOperation (Document document) {
//        this.document = document;
//    }
//
//    @Override
//    public Document toDocument(AggregationOperationContext context) {
//	return document;
//    }
//}
