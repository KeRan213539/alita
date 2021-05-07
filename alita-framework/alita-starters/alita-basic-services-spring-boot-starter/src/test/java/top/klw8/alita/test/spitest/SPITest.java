package top.klw8.alita.test.spitest;

import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * .
 *
 * 2021/4/8 11:37
 */
@Component
public class SPITest {

    public void doSPIInterface() {
        List<SPIInterface> spiInterfaceList = SpringFactoriesLoader.loadFactories(SPIInterface.class, null);
        for (SPIInterface spiInterface : spiInterfaceList) {
            spiInterface.print("================xxx");
        }
    }

}
