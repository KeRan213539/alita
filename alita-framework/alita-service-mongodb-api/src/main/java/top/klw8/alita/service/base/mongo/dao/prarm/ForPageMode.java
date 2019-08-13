package top.klw8.alita.service.base.mongo.dao.prarm;

import java.io.Serializable;

import org.springframework.util.Assert;

import lombok.Getter;

/**
 * @ClassName: ForPageMode
 * @Description: 分页方式
 * @author klw
 * @date 2018年12月26日 下午4:36:44
 */
public class ForPageMode implements Serializable, Cloneable {

    private static final long serialVersionUID = -123885716795552945L;

    /**
     * @see ForPageMode#SKIP
     */
    public static final String MODE_NAME_SKIP = "SKIP";

    /**
     * @see ForPageMode#COMPARATIVE_UNIQUE_FIELD
     */
    public static final String MODE_NAME_COMPARATIVE_UNIQUE_FIELD = "COMPARATIVE_UNIQUE_FIELD";

    /**
     * @author klw
     * @Fields modeName : 方式名称
     */
    private String modeName;

    /**
     * @author klw
     * @Fields fieldName : COMPARATIVE_UNIQUE_FIELD 时必须设置, 比较的字段名
     */
    private String fieldName;

    /**
     * @author klw
     * @Fields fieldValue : COMPARATIVE_UNIQUE_FIELD 时必须设置, 比较的字段值
     */
    private String fieldValue;

    /**
     * @author klw
     * @Fields fieldDataType : 比较的字段值的数据类型
     */
    private FieldDataType fieldDataType = FieldDataType.STRING;

    /**
     * @author klw
     * @Fields direction : 比较方式: 大于/小于,默认小于
     */
    private ComparisonMode comparisonMode = ComparisonMode.LESS_THAN;

    /**
     * @author klw
     * @Fields dataPage : 一次获取多少页的数据
     */
    private int dataPage = 1;

    private ForPageMode(String modeName) {
	this.modeName = modeName;
    }

    public Mode build() {
	if (modeName.equals(MODE_NAME_SKIP)) {
	    return new Mode(modeName);
	}
	Assert.hasText(fieldName, "比较分页的字段名不能为空!");
	Assert.notNull(fieldDataType, "比较分页的字段值的类型不能为空!");
	return new Mode(modeName, fieldName, fieldValue, fieldDataType, comparisonMode, dataPage);
    }

    /**
     * @author klw
     * @Fields SKIP : 可以跳页,但是跳过的数据越多,性能越差,因为是采用遍历方式跳过
     */
    public static ForPageMode SKIP() {
	return new ForPageMode(ForPageMode.MODE_NAME_SKIP);
    }

    /**
     * @author klw
     * @Fields COMPARATIVE_UNIQUE_FIELD : 比较指定的唯一值字段的大小,需要传入上一页的最后一条数据的该字段值 该方式性能好,但是无法跳页,可以参考百度等的方式一次多拿几页数据,在前端分页(前端不提供任意页码的方式,只提供有限的可选页码) 例如每页10条数据,页码显示11页,那就一次拿100条数据,前端自己分10页显示,当选的第11页的时候,拿下一个10页的数据 如果有跳页需求,数据量不大的话,可以使用SKIP方式
     */
    public static ForPageMode COMPARATIVE_UNIQUE_FIELD() {
	return new ForPageMode(ForPageMode.MODE_NAME_COMPARATIVE_UNIQUE_FIELD);
    }

    public ForPageMode setFieldName(String fieldName) {
	this.fieldName = fieldName;
	return this;
    }

    public ForPageMode setFieldValue(String fieldValue) {
	this.fieldValue = fieldValue;
	return this;
    }

    public ForPageMode setDirection(ComparisonMode comparisonMode) {
	this.comparisonMode = comparisonMode;
	return this;
    }

    public ForPageMode setDataPage(int dataPage) {
	this.dataPage = dataPage;
	return this;
    }

    public ForPageMode setFieldDataType(FieldDataType fieldDataType) {
	this.fieldDataType = fieldDataType;
	return this;
    }

    @Getter
    public class Mode implements Serializable, Cloneable {

	private static final long serialVersionUID = -3838486169471659427L;

	/**
	 * @see ForPageMode#modeName
	 */
	private String modeName;

	/**
	 * @see ForPageMode#fieldName
	 */
	private String fieldName;

	/**
	 * @see ForPageMode#fieldValue
	 */
	private String fieldValue;

	/**
	 * @see ForPageMode#fieldDataType
	 */
	private FieldDataType fieldDataType;

	/**
	 * @see ForPageMode#direction
	 */
	private ComparisonMode comparisonMode;

	/**
	 * @see ForPageMode#dataPage
	 */
	private int dataPage;

	private Mode(String modeName) {
	    this.modeName = modeName;
	}

	private Mode(String modeName, String fieldName, String fieldValue,
		FieldDataType fieldDataType, ComparisonMode comparisonMode, int dataPage) {
	    this.modeName = modeName;
	    this.fieldName = fieldName;
	    this.fieldValue = fieldValue;
	    this.fieldDataType = fieldDataType;
	    this.comparisonMode = comparisonMode;
	    this.dataPage = dataPage;
	}

	public String name() {
	    return modeName;
	}
	
	public Object getRealFieldValue() {
	    switch (fieldDataType) {
	    case INTEGER:
		return Integer.valueOf(fieldValue);
	    case LONG:
		return Long.valueOf(fieldValue);
	    default:
		return fieldValue;
	    }

	}

    }

    /**
     * @ClassName: FieldType
     * @Description: 字段对应的类型
     * @author klw
     * @date 2018年12月27日 下午2:25:45
     */
    public enum FieldDataType {
	STRING, INTEGER, LONG;
    }
    
    /**
     * @ClassName: ComparisonMode
     * @Description: 比较方式
     * @author klw
     * @date 2018年12月27日 下午5:56:56
     */
    public enum ComparisonMode {
	GREATER_THAN, LESS_THAN;
    }

}
