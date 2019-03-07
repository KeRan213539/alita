package top.klw8.alita.devTools.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.klw8.alita.devTools.View;

/**
 * @ClassName: FilePathUtil
 * @Description: 文件路径工具类
 * @author klw
 * @date 2019年2月27日 下午4:02:03
 */
public class FilePathUtil {
    
    private static String entitysBasePath = null;
    
    private static String projectBasePath = null;
    
    
    private static String apiProjectBasePath = "alita-service-api\\src\\main\\java\\";
    
    static {
	String classFilePath = View.class.getResource("").toString();
	projectBasePath = classFilePath.substring(0, classFilePath
		.indexOf("alita-dev-tools/target/classes/top/klw8/alita/devTools/"));
	projectBasePath = projectBasePath.substring("file:/".length());
	entitysBasePath = projectBasePath
		+ "alita-service-api/src/main/java/top/klw8/alita/entitys/";
    }
    
    public static String apiProjectBasePath() {
	return projectBasePath + apiProjectBasePath;
    }
    
    /**
     * @Title: getJavaFileInfo
     * @author klw
     * @Description: 根据类名获取类文件的包路径和文件路径
     * @param className
     * @return
     */
    public static Map<String, String> getJavaFileInfo(String className){
	File file = findFile(new File(entitysBasePath), className + ".java");
	if(file == null) {
	    return null;
	}
	return getJavaFileInfo(file);
    }
    
    public static Map<String, String> getJavaFileInfo(File classFile){
	if(classFile.isDirectory() || !classFile.exists()) {
	    return null;
	}
	Map<String, String> result = new HashMap<>();
	String fileName = classFile.getName();
	fileName = fileName.substring(0, fileName.lastIndexOf("."));
	String filePath = classFile.getPath();
	result.put("packagePath",
		filePath.substring(
			filePath.indexOf(apiProjectBasePath) + apiProjectBasePath.length(),
			filePath.lastIndexOf(".java")).replace("\\", "."));
	result.put("filePath", filePath);
	return result;
    }
    
    /**
     * @Title: findEnums
     * @author klw
     * @Description: 查找枚举文件
     * @param folder
     * @return
     */
    private static File findFile(File folder, String fileName) {
	if (folder.isFile() && folder.getName().equals(fileName)) {
	    return folder;
	}
	File[] subFolderOrFile = folder.listFiles();
	if (subFolderOrFile != null) {
	    for (File file : subFolderOrFile) {
		File result = findFile(file, fileName);
		if (result != null) {
		    return result;
		}
	    }
	}
	return null;
    }
    
    public static String entitysBasePath() {
	return entitysBasePath;
    }
    
    /**
     * @Title: searchWebApiDirectory
     * @author klw
     * @Description: 查找web-api项目目录
     * @return
     */
    public static Map<String, String> searchWebApiDirectory() {
	return searchProjectDirectory("web-apis");
    }
    
    /**
     * @Title: searchServiceDirectory
     * @author klw
     * @Description: 查找service项目目录
     * @return
     */
    public static Map<String, String> searchServiceDirectory() {
	return searchProjectDirectory("services");
    }
    
    /**
     * @Title: searchProjectDirectory
     * @author klw
     * @Description: 查找项目文件夹
     * @param folderSubPath 项目所在目录(相对与项目根目录)
     * @return 文件夹名为key,文件夹路径为value的Map
     */
    private static Map<String, String> searchProjectDirectory(String folderSubPath) {
	File projectPath = new File(projectBasePath + folderSubPath + "/");
	File[] projectDirectorys = projectPath.listFiles(new FileFilter() {
	    @Override
	    public boolean accept(File file) {
		if (file.isDirectory()) {
		    return true;
		}
		return false;
	    }
	});
	Map<String, String> tempMap = new HashMap<>();
	for(File directory : projectDirectorys) {
	    String directoryName = directory.getName();
	    tempMap.put(directoryName, directory.getPath());
	}
	return tempMap;
    }
    

    /**
     * @Title: searchEntityFiles
     * @author klw
     * @Description: 查找实体类
     * @return 文件名(不带扩展名)为key,包路径和文件路径为的Map为value的Map
     */
    public static Map<String, Map<String, String>> searchEntityFiles() {
	List<File> fileList = FilePathUtil.searchFiles(new File(entitysBasePath), ".java", true);
	Map<String, Map<String, String>> tempMap = new HashMap<>();
	fileList.forEach(file -> {
	    Map<String, String> valueMap = new HashMap<>();
	    String fileName = file.getName();
	    fileName = fileName.substring(0, fileName.lastIndexOf("."));
	    String filePath = file.getPath();
	    valueMap.put("packagePath", filePath.substring(filePath.indexOf(apiProjectBasePath) + apiProjectBasePath.length(), filePath.lastIndexOf(".java")).replace("\\", "."));
	    valueMap.put("filePath", filePath);
	    tempMap.put(fileName, valueMap);
	});
	return tempMap;
    }
    
    /**
     * @Title: searchFiles
     * @author klw
     * @Description: 查找文件夹
     * @param folder  目录
     * @param keyword 文件名关键字,如果以 "." 开头则是查找扩展名
     * @return File list
     */
    public static List<File> searchFiles(File folder, final String keyword){
	return searchFiles(folder, keyword, false);
    }
    
    /**
     * @Title: searchFiles
     * @author klw
     * @Description: 查找文件夹
     * @param folder  目录
     * @param keyword 文件名关键字,如果以 "." 开头则是查找扩展名
     * @return File list
     */
    private static List<File> searchFiles(File folder, final String keyword, boolean isNolyFindEntity) {
	if (folder.isFile()) {
	    System.err.println("该路径是文件,不是文件夹!");
	    return null;
	}
	List<File> result = new ArrayList<File>();
	FileFilter fileFilter = null;
	if(keyword.startsWith(".")) {
	    // 查找扩展名
	    fileFilter = new FileFilter() {
		@Override
		public boolean accept(File file) {
		    
		    if (file.isDirectory()) {
			return true;
		    }
		    if(isNolyFindEntity) {
			if (file.getName().endsWith("Enum.java")) {
			    return false;
			}
		    }
		    if (file.getName().toLowerCase().endsWith(keyword)) {
			return true;
		    }
		    return false;
		}
	    };
	} else {
	    // 查找文件名
	    fileFilter = new FileFilter() {
		@Override
		public boolean accept(File file) {
		    if (file.isDirectory()) {
			return true;
		    }
		    if(isNolyFindEntity) {
			if (file.getName().endsWith("Enum.java")) {
			    return false;
			}
		    }
		    if (file.getName().toLowerCase().contains(keyword)) {
			return true;
		    }
		    return false;
		}
	    };
	}
	File[] subFolders = folder.listFiles(fileFilter);

	if (subFolders != null) {
	    for (File file : subFolders) {
		if (file.isFile()) {
		    // 如果是文件则将文件添加到结果列表中
		    result.add(file);
		} else {
		    // 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
		    result.addAll(searchFiles(file, keyword, isNolyFindEntity));
		}
	    }
	}
	return result;
    }

}
