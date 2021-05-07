package top.klw8.alita.test.spitest;

import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * .
 *
 * 2021/4/8 11:38
 */
public class SPITest2 {

    public static void doSPIInterface() {
        List<SPIInterface> spiInterfaceList = SpringFactoriesLoader.loadFactories(SPIInterface.class, null);
        for (SPIInterface spiInterface : spiInterfaceList) {
            spiInterface.print("================xxx22222");
        }
    }

}
