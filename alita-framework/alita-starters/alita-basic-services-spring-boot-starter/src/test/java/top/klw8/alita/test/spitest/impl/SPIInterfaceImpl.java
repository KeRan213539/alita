package top.klw8.alita.test.spitest.impl;

import top.klw8.alita.test.spitest.SPIInterface;

/**
 * .
 *
 * @author klw(213539 @ qq.com)
 * @date 2021/4/8 11:44
 */
public class SPIInterfaceImpl implements SPIInterface {
    @Override
    public void print(String str) {
        System.out.println(str);
    }
}
