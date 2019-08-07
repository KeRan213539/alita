package top.klw8.alita.devTools;

import top.klw8.alita.devTools.utils.GenerateHelper;

/**
 * @ClassName: DevToolsMain
 * @Description: 启动器
 * @author klw
 * @date 2019年2月25日 下午4:54:03
 */
public class DevToolsMain {

    public static void main(String[] args) {
	
	try {
	    View window = new View();
	    GenerateHelper.view = window;
	    window.open();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
}
