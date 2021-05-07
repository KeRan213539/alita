/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.mq.messages;

import java.math.BigDecimal;

/**
 * 消息bean Demo
 * 2018年9月12日 下午3:42:56
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
