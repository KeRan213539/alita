package top.klw8.alita.mq.messages;

import java.math.BigDecimal;

/**
 * @ClassName: DemoMsgBean
 * @Description: 消息bean Demo
 * @author klw
 * @date 2018年9月12日 下午3:42:56
 */
public class DemoMsgBean implements java.io.Serializable {

    private static final long serialVersionUID = 6912428244694675882L;

    private String orderId;

    private BigDecimal paidMoney;
    
    public DemoMsgBean() {}  //因为该bean会被反序列化,所以需要无参构造方法
    
    public DemoMsgBean(String orderId, BigDecimal paidMoney) {
	this.orderId = orderId;
	this.paidMoney = paidMoney;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney) {
        this.paidMoney = paidMoney;
    }

    @Override
    public String toString() {
	return "DemoMsgBean [orderId=" + orderId + ", paidMoney=" + paidMoney + "]";
    }
}
