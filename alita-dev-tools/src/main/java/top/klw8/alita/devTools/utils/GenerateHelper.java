package top.klw8.alita.devTools.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

import top.klw8.alita.devTools.View;
import top.klw8.alita.devTools.common.NoRepeatList;
import top.klw8.alita.devTools.controls.MatcherSelectDialog;
import top.klw8.alita.devTools.utils.ViewUtil.ClassnameAndFilePathGroup;
import top.klw8.alita.devTools.utils.ViewUtil.ScrolledGroup;

/**
 * @ClassName: GenerateHelper
 * @Description: 生成代码辅助工具
 * @author klw
 * @date 2019年2月26日 下午4:32:00
 */
public class GenerateHelper {
    
    public static View view = null;
    
    /**
     * @author klw
     * @Fields isGenerateDao : 是否生成DAO
     */
    public static boolean isGenerateDao = true;
    
    /**
     * @author klw
     * @Fields isGenerateService : 是否生成Service
     */
    public static boolean isGenerateService = true;
    
    /**
     * @author klw
     * @Fields isGenerateVo : 是否单独生成VO
     */
    public static boolean isGenerateVo = true;
    
    /**
     * @author klw
     * @Fields isGenerateController : 是否生成控制器,默认都生成,除了单独生成VO时
     */
    public static boolean isGenerateController = true;
    
    /**
     * @author klw
     * @Fields isGenerateAddApi : 是否生成添加保存Api(和VO)
     */
    public static boolean isGenerateAddApi = true;
    
    /**
     * @author klw
     * @Fields isGenerateModifyApi : 是否生成修改保存Api(和VO)
     */
    public static boolean isGenerateModifyApi = true;
    
    /**
     * @author klw
     * @Fields isGenerateList : 是否生成不分页列表Api(和VO)
     */
    public static boolean isGenerateList = true;
    
    /**
     * @author klw
     * @Fields isGenerateSkipPageApi : 是否生成skip方式分页Api(和VO)
     */
    public static boolean isGenerateSkipPageApi = true;
    
    /**
     * @author klw
     * @Fields isGenerateComparativePageApi : 是否生成比较方式分页Api(和VO)
     */
    public static boolean isGenerateComparativePageApi = true;
    
    /**
     * @author klw
     * @Fields scrolledGroupList : 缓存 scrolledGroup 用
     */
    private static List<ScrolledGroup> scrolledGroupList = new ArrayList<>();
    
    /**
     * @author klw
     * @Fields scrolledGroupMap : 缓存 scrolledGroup 用
     */
    private static Map<String, ScrolledGroup> scrolledGroupMap = new HashMap<>();
    
    /**
     * @author klw
     * @Fields classnameAndFilePathGroupMap : 缓存 ClassnameAndFilePathGroup 用
     */
    private static Map<String, ClassnameAndFilePathGroup> classnameAndFilePathGroupMap = new HashMap<>();
    
    /**
     * @author klw
     * @Fields isOverwrite : 当将要生成的目标文件存在时,是否覆盖
     */
    public static boolean overwrite = false;
    
    public static void showMessageBox(String title, String msg, int icon) {
	MessageBox msgBox = new MessageBox(view.getViewShell(), icon);
	msgBox.setText(title);
	msgBox.setMessage(msg);
	msgBox.open();
    }
    
    /**
     * @Title: generateScrolledGroup
     * @author klw
     * @Description: 根据选择的生成项目在UI上生成选择组
     * @param parent 选择租的父Composite
     */
    public static void generateScrolledGroup(Composite scrolledGroupParent, Composite classnameAndFilePathComposite) {
	scrolledGroupList.clear();
	scrolledGroupMap.clear();
	classnameAndFilePathGroupMap.clear();
	if(isGenerateDao) {
	    ClassnameAndFilePathGroup daoInfo = ViewUtil.createClassnameAndFilePathGroup(classnameAndFilePathComposite, "DAO选项", 2);
	    classnameAndFilePathGroupMap.put("daoInfo", daoInfo);
	}
	if(isGenerateService) {
	    ClassnameAndFilePathGroup serviceInfo = ViewUtil.createClassnameAndFilePathGroup(classnameAndFilePathComposite, "Service选项", 3);
	    classnameAndFilePathGroupMap.put("serviceInfo", serviceInfo);
	}
	if(isGenerateController) {
	    ClassnameAndFilePathGroup controllerInfo = ViewUtil.createClassnameAndFilePathGroup(classnameAndFilePathComposite, "Controller选项", 1);
	    classnameAndFilePathGroupMap.put("controllerInfo", controllerInfo);
	}
	if(isGenerateVo) {
	    ScrolledGroup sg = ViewUtil.createScrolledGroup(scrolledGroupParent, "选择【普通VO】参数");
	    sg.setGroupName("vo");
	    scrolledGroupList.add(sg);
	    scrolledGroupMap.put("vo", sg);
	}
	if(isGenerateAddApi) {
	    ScrolledGroup sg = ViewUtil.createScrolledGroup(scrolledGroupParent, "选择【添加保存】API参数");
	    sg.setGroupName("addApi");
	    scrolledGroupList.add(sg);
	    scrolledGroupMap.put("addApi", sg);
	}
	if(isGenerateModifyApi) {
	    ScrolledGroup sg = ViewUtil.createScrolledGroup(scrolledGroupParent, "选择【修改保存】API参数");
	    sg.setGroupName("modifyApi");
	    scrolledGroupList.add(sg);
	    scrolledGroupMap.put("modifyApi", sg);
	}
	if(isGenerateList) {
	    ScrolledGroup sg = ViewUtil.createScrolledGroup(scrolledGroupParent, "选择【不分页列表】API参数");
	    sg.setGroupName("listApi");
	    scrolledGroupList.add(sg);
	    scrolledGroupMap.put("listApi", sg);
	}
	if(isGenerateSkipPageApi) {
	    ScrolledGroup sg = ViewUtil.createScrolledGroup(scrolledGroupParent, "选择【skip方式分页】参数");
	    sg.setGroupName("skipPageApi");
	    scrolledGroupList.add(sg);
	    scrolledGroupMap.put("skipPageApi", sg);
	}
	if(isGenerateComparativePageApi) {
	    ScrolledGroup sg = ViewUtil.createScrolledGroup(scrolledGroupParent, "选择比较方式分页】API参数");
	    sg.setGroupName("comparativePageApi");
	    scrolledGroupList.add(sg);
	    scrolledGroupMap.put("comparativePageApi", sg);
	}
	addClassInfo2ScrolledGroup();
    }
    
    /**
     * @Title: addClassInfo2ScrolledGroup
     * @author klw
     * @Description: 向各个ScrolledGroup中添加类名,类保存位置的东西
     */
    private static void addClassInfo2ScrolledGroup() {
	scrolledGroupList.forEach(scrolledGroup -> {
	    
	    GridData gridData = new GridData();
	    gridData.horizontalAlignment = GridData.FILL;
	    gridData.grabExcessHorizontalSpace = true;
	    
	    // 类名输入框
	    Text classNameText = new Text(scrolledGroup.getGroup(), SWT.BORDER);
	    classNameText.setMessage("输入VO的类名(包括包路径)");
	    classNameText.setLayoutData(gridData);
	    scrolledGroup.setClassNameText(classNameText);

	    if(scrolledGroup.getGroupName().equals("vo")) {
		// 项目路径文本框
		Text filePathText = new Text(scrolledGroup.getGroup(), SWT.BORDER);
		filePathText.setEditable(false);
		filePathText.setLayoutData(gridData);
		scrolledGroup.setClassFileDirectoryText(filePathText);

		Button choiceDirectoryBtn = new Button(scrolledGroup.getGroup(), SWT.NONE);
		choiceDirectoryBtn.setText("选择WEB-API项目");
		choiceDirectoryBtn.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
			Map<String, String> serviceDirectoryMap = FilePathUtil
				.searchWebApiDirectory();
			List<String> directoryList = serviceDirectoryMap.entrySet().stream()
				.map(entry -> {
				    return entry.getKey();
				}).collect(Collectors.toList());
			Collections.sort(directoryList);
			MatcherSelectDialog fd = new MatcherSelectDialog(view.getViewShell(),
				"选择VO要放入的WEB-API项目", directoryList);
			String filePath = fd.openDialog();
			if (StringUtils.isNotBlank(filePath)) {
			    filePathText.setText(serviceDirectoryMap.get(filePath));
			}
		    }
		});
	    }
	    
	    Button allCheckBtn = new Button(scrolledGroup.getGroup(), SWT.CHECK);
	    allCheckBtn.setText("全选");
	    allCheckBtn.setSelection(true);
	    scrolledGroup.setAllCheckBtn(allCheckBtn);
	    
	    Button btnCheckButton = new Button(scrolledGroup.getGroup(), SWT.CHECK);
	    btnCheckButton.setText("id");
	    btnCheckButton.setSelection(true);
	    Map<String, String> extData = new HashMap<>();
	    extData.put("fieldName", "id");
	    extData.put("fieldComment", "ID主键");
	    extData.put("fieldType", "Long");
	    btnCheckButton.setData(extData);
	    scrolledGroup.addCheckBox(btnCheckButton);
	    
	});
    }
    
    /**
     * @Title: addCheckBox2ScrolledGroups
     * @author klw
     * @Description: 添加选择组中的选择项
     * @param checkBoxText
     */
    public static void addCheckBox2ScrolledGroups(String checkBoxText, Map<String, String> extData) {
	scrolledGroupList.forEach(scrolledGroup -> {
	    Button btnCheckButton = new Button(scrolledGroup.getGroup(), SWT.CHECK);
	    btnCheckButton.setText(checkBoxText);
	    btnCheckButton.setSelection(true);
	    btnCheckButton.setData(extData);
	    scrolledGroup.addCheckBox(btnCheckButton);
	});
    }
    
    /**
     * @Title: layoutScrolledGroups
     * @author klw
     * @Description: 布局(刷新显示)所有选择租
     */
    public static void layoutScrolledGroups() {
	scrolledGroupList.forEach(scrolledGroup -> {
	    for (Control control : scrolledGroup.getGroup().getChildren()) {
		if (control instanceof Button) {
		    Button checkBoxBtn = ((Button) control);
		    if (checkBoxBtn.getText().equals("全选")) {
			checkBoxBtn.addSelectionListener(new SelectionAdapter() {
			    @Override
			    public void widgetSelected(SelectionEvent e) {
				Button _this = (Button) e.widget;
				boolean allChecked = _this.getSelection();
				for (Control control : scrolledGroup.getGroup().getChildren()) {
				    if (control instanceof Button) {
					Button checkBtn = ((Button) control);
					if (!checkBtn.getText().equals("选择WEB-API项目")) {
					    checkBtn.setSelection(allChecked);
					}
				    }
				}
			    }
			});
		    } else {
			checkBoxBtn.addSelectionListener(new SelectionAdapter() {
			    @Override
			    public void widgetSelected(SelectionEvent e) {
				Button _this = (Button) e.widget;
				boolean checked = _this.getSelection();
				if (checked) {
				    boolean flag = true;
				    for (Control control : scrolledGroup.getGroup().getChildren()) {
					if (control instanceof Button) {
					    Button checkbox = (Button) control;
					    if (!checkbox.getText().equals("全选") 
						    && !checkbox.getText().equals("选择WEB-API项目") 
						    && !checkbox.getSelection()) {
						flag = false;
					    }
					}
				    }
				    scrolledGroup.getAllCheckBtn().setSelection(flag);
				} else {
				    scrolledGroup.getAllCheckBtn().setSelection(false);
				}
			    }
			});
		    }
		}
	    }
	    scrolledGroup.layout();
	});
    }
    
    /**
     * @Title: buildFiles
     * @author klw
     * @Description: 生成文件
     */
    public static void buildFiles() {
	if (isGenerateDao) {
	    view.appendInfoConsoleTxt("开始生成DAO...");
	    generateDao();
	    view.appendSuccessConsoleTxt("DAO生成完毕...");
	}
	
	if (isGenerateService) {
	    view.appendInfoConsoleTxt("开始生成Service...");
	    generateService();
	    view.appendSuccessConsoleTxt("Service生成完毕...");
	}
	
	if(isGenerateController) {
	    view.appendInfoConsoleTxt("开始生成Controller及对应VO...");
	    generateControllerAndVo();
	    view.appendSuccessConsoleTxt("Controller及对应VO生成完毕...");
	}
	if(isGenerateVo) {
	    view.appendInfoConsoleTxt("开始生成单独VO...");
	    Map<String, String> result = generateVo("vo", null);
	    if(result == null) {
		return;
	    }
	    view.appendSuccessConsoleTxt("单独VO生成完毕...");
	}
    }
    
    private static void generateControllerAndVo() {
	Map<String, String> entityFileInfo = FilePathUtil.getJavaFileInfo(view.getEntityFile());
	String entityFilePackageName = entityFileInfo.get("packagePath");
	String entityName = entityFilePackageName
		.substring(entityFilePackageName.lastIndexOf(".") + 1);
	ClassnameAndFilePathGroup controllerInfo = classnameAndFilePathGroupMap.get("controllerInfo");
	ClassnameAndFilePathGroup serviceInfo = classnameAndFilePathGroupMap.get("serviceInfo");
	String serviceInterfacePackageName = serviceInfo.getInterfaceNameText().getText().trim();
	String baseUri = controllerInfo.getBaseUriText().getText().trim();
	String catlogIndex = controllerInfo.getCatlogIndexText().getText().trim();
	String classPackageName = controllerInfo.getClassNameText().getText().trim();
	String classFileDirectory = controllerInfo.getClassFileDirectoryText().getText().trim();
	if (StringUtils.isBlank(serviceInterfacePackageName)) {
	    view.appendErrorConsoleTxt("service接口名为空,生成失败...");
	    return;
	}
	if (StringUtils.isBlank(baseUri)) {
	    view.appendErrorConsoleTxt("接口uri目录为空,生成失败...");
	    return;
	}
	if (StringUtils.isBlank(catlogIndex)) {
	    view.appendErrorConsoleTxt("后台系统菜单顺序为空,生成失败...");
	    return;
	}
	try {
	    Integer.valueOf(catlogIndex);
	}catch (NumberFormatException e) {
	    view.appendErrorConsoleTxt("后台系统菜单顺序不是数字(Integer),生成失败...");
	    return;
	}
	
	if (StringUtils.isBlank(classPackageName)) {
	    view.appendErrorConsoleTxt("controller名为空,生成失败...");
	    return;
	}
	if (StringUtils.isBlank(classFileDirectory)) {
	    view.appendErrorConsoleTxt("controller保存到的web-api项目未选择,生成失败...");
	    return;
	}
	String serviceInterfaceName = serviceInterfacePackageName
		.substring(serviceInterfacePackageName.lastIndexOf(".") + 1);
	String classPackage = classPackageName.substring(0,
		classPackageName.lastIndexOf("."));
	String className = classPackageName
		.substring(classPackageName.lastIndexOf(".") + 1);
	String classFileParentPath = classFileDirectory + "\\src\\main\\java\\"
		+ classPackage.replace(".", "\\") + "\\";
	String classFilePath = classFileParentPath + className + ".java";
	
	boolean isGenerateApi = true;
	if(!isGenerateAddApi && !isGenerateModifyApi && !isGenerateList && !isGenerateSkipPageApi && !isGenerateComparativePageApi) {
	    isGenerateApi = false;
	}
	Map<String, Object> classModel = new HashMap<>();
	List<String> classImportList = new NoRepeatList<>();
	classImportList.add(serviceInterfacePackageName);
	classModel.put("serviceInterfaceName", serviceInterfaceName);
	classModel.put("baseUri", baseUri);
	classModel.put("catlogIndex", catlogIndex);
	classModel.put("package", classPackage);
	classModel.put("className", className);
	classModel.put("classComment", view.getEntityComment());
	classModel.put("isGenerateApi", isGenerateApi);
	if(isGenerateApi) {
	    classImportList.add(entityFilePackageName);
	    classModel.put("entityName", entityName);
	    classModel.put("isGenerateAddApi", isGenerateAddApi);
	    classModel.put("isGenerateModifyApi", isGenerateModifyApi);
	    classModel.put("isGenerateList", isGenerateList);
	    classModel.put("isGenerateSkipPageApi", isGenerateSkipPageApi);
	    classModel.put("isGenerateComparativePageApi", isGenerateComparativePageApi);
	    if(isGenerateAddApi) {
		Map<String, String> result = generateVo("addApi", classFileDirectory);
		if(result == null) {
		    return;
		}
		String voName = result.get("voName");
		classImportList.add(result.get("voPackageName"));
		classModel.put("addVoName", voName);
	    }
	    if(isGenerateModifyApi) {
		Map<String, String> result = generateVo("modifyApi", classFileDirectory);
		if(result == null) {
		    return;
		}
		String voName = result.get("voName");
		classImportList.add(result.get("voPackageName"));
		classModel.put("modifyVoName", voName);
	    }
	    if(isGenerateList) {
		Map<String, String> result = generateVo("listApi", classFileDirectory);
		if(result == null) {
		    return;
		}
		String voName = result.get("voName");
		classImportList.add(result.get("voPackageName"));
		classModel.put("listVoName", voName);
	    }
	    if(isGenerateSkipPageApi) {
		Map<String, String> result = generateVo("skipPageApi", classFileDirectory);
		if(result == null) {
		    return;
		}
		String voName = result.get("voName");
		classImportList.add(result.get("voPackageName"));
		classModel.put("pageVoName", voName);
	    }
	    if(isGenerateComparativePageApi) {
		Map<String, String> result = generateVo("comparativePageApi", classFileDirectory);
		if(result == null) {
		    return;
		}
		String voName = result.get("voName");
		classImportList.add(result.get("voPackageName"));
		classModel.put("comparativePageVoName", voName);
	    }
	}
	classModel.put("importList", classImportList);

	File interfaceFile = new File(classFilePath);
	if (interfaceFile.exists() && !overwrite) {
	    view.appendErrorConsoleTxt("controller生成失败,该文件已存在!如需覆盖,请勾选是否覆盖选项!");
	    return;
	}
	File interfaceFileParent = new File(classFileParentPath);
	if (!interfaceFileParent.exists()) {
	    interfaceFileParent.mkdirs();
	}
	FreemarkerTemplateUtil.INSTANCE.processTemplateIntoFile(interfaceFile, "controller",
		classModel);
	view.appendSuccessConsoleTxt(className + "生成完成,路径: ");
	view.appendSuccessConsoleTxt(classFilePath);
	
    }
    
    @SuppressWarnings("unchecked")
    private static Map<String, String> generateVo(String voType, String voFileDirectory) {
	Map<String, String> entityFileInfo = FilePathUtil.getJavaFileInfo(view.getEntityFile());
	String entityFilePackageName = entityFileInfo.get("packagePath");
	String entityName = entityFilePackageName
		.substring(entityFilePackageName.lastIndexOf(".") + 1);
	
	ScrolledGroup voInfo = scrolledGroupMap.get(voType);
	String voPackageName = voInfo.getClassNameText().getText().trim();
	if(voType.equals("vo")) {
	    voFileDirectory = voInfo.getClassFileDirectoryText().getText().trim();
	}
	if (StringUtils.isBlank(voPackageName)) {
	    view.appendErrorConsoleTxt("vo名为空,生成失败...");
	    return null;
	}
	if (StringUtils.isBlank(voFileDirectory)) {
	    view.appendErrorConsoleTxt("vo保存到的web-api项目未选择,生成失败...");
	    return null;
	}
	String voPackage = voPackageName.substring(0,
		voPackageName.lastIndexOf("."));
	String voName = voPackageName
		.substring(voPackageName.lastIndexOf(".") + 1);
	String voFileParentPath = voFileDirectory + "\\src\\main\\java\\"
		+ voPackage.replace(".", "\\") + "\\";
	String voFilePath = voFileParentPath + voName + ".java";
	
	Map<String, Object> voModel = new HashMap<>();
	List<String> voImportList = new NoRepeatList<>();
	voImportList.add(entityFilePackageName);
	voModel.put("package", voPackage);
	voModel.put("voType", voType);
	voModel.put("className", voName);
	voModel.put("classComment", view.getEntityComment());
	voModel.put("entityName", entityName);
	
	List<Map<String, String>> fieldList = new ArrayList<>();
	voInfo.getGroupCheckboxList().forEach(checkbox -> {
	    if(checkbox.getSelection()) {
		Map<String, String> extData = (Map<String, String>) checkbox.getData();
		Map<String, String> fieldData = new HashMap<>();
		String fieldType = extData.get("fieldType");
		String fieldComment = extData.get("fieldComment");
		fieldData.put("name", extData.get("fieldName"));
		fieldData.put("type", fieldType);
		if(fieldType.endsWith("Enum")) {
		    // 处理枚举类型
		    Map<String, String> enumInfo = FilePathUtil.getJavaFileInfo(fieldType);
		    if (enumInfo == null || enumInfo.isEmpty()) {
			view.appendWarningConsoleTxt("枚举: 【" + fieldType + "】对应的源文件不存在,无法解析!将使用默认字段说明");
		    } else {
			voImportList.add(enumInfo.get("packagePath"));
			StringBuilder fieldCommentSb = new StringBuilder();
			ParseResult<CompilationUnit> cu = JavaParserUtil.parse(enumInfo.get("filePath"));
			cu.getResult().get().getChildNodes().forEach(node -> {
			    if (node instanceof ClassOrInterfaceDeclaration) {
				ClassOrInterfaceDeclaration classDec = (ClassOrInterfaceDeclaration) node;
				classDec.getMembers().forEach(member -> {
				    if (member instanceof FieldDeclaration) {
					FieldDeclaration fieldDec = (FieldDeclaration) member;
					if (fieldDec.getComment().isPresent()) {
					    String enumName = fieldDec.getVariable(0)
						    .getNameAsString();
					    String enumComment = JavaParserUtil.parseFieldComment(
						    enumName,
						    fieldDec.getComment().get().getContent());
					    fieldCommentSb.append(enumComment).append("<br />").append(System.lineSeparator());
					}
				    }
				});
			    }
			});
			fieldComment = fieldCommentSb.toString();
		    }
		} else if(fieldType.equals("BigDecimal")) {
		    voImportList.add("java.math.BigDecimal");
		} else if(fieldType.equals("GeoPoint")) {
		    voImportList.add("top.klw8.alita.service.common.GeoPoint");
		} else if(fieldType.equals("LocalDateTime")) {
		    voImportList.add("java.time.LocalDateTime");
		}
		fieldData.put("comment", fieldComment);
		fieldList.add(fieldData);
	    }
	});
	
	voModel.put("fieldList", fieldList);
	voModel.put("importList", voImportList);
	
	File voFile = new File(voFilePath);
	if (voFile.exists() && !overwrite) {
	    view.appendErrorConsoleTxt("vo生成失败,该文件已存在!如需覆盖,请勾选是否覆盖选项!");
	    return null;
	}
	File voFileParent = new File(voFileParentPath);
	if (!voFileParent.exists()) {
	    voFileParent.mkdirs();
	}
	FreemarkerTemplateUtil.INSTANCE.processTemplateIntoFile(voFile, "vo",
		voModel);
	view.appendSuccessConsoleTxt(voName + "生成完成,路径: ");
	view.appendSuccessConsoleTxt(voFilePath);
	Map<String, String> result = new HashMap<>();
	result.put("voName", voName);
	result.put("voPackageName", voPackageName);
	return result;
    }
    
    private static void generateService() {
	Map<String, String> entityFileInfo = FilePathUtil.getJavaFileInfo(view.getEntityFile());
	String entityFilePackageName = entityFileInfo.get("packagePath");
	String entityName = entityFilePackageName
		.substring(entityFilePackageName.lastIndexOf(".") + 1);
	ClassnameAndFilePathGroup daoInfo = classnameAndFilePathGroupMap.get("daoInfo");
	ClassnameAndFilePathGroup serviceInfo = classnameAndFilePathGroupMap.get("serviceInfo");

	String daoInterfacePackageName = daoInfo.getInterfaceNameText().getText().trim();
	String interfacePackageName = serviceInfo.getInterfaceNameText().getText().trim();
	String implPackageName = serviceInfo.getClassNameText().getText().trim();
	String classFileDirectory = daoInfo.getClassFileDirectoryText().getText().trim();
	if (StringUtils.isBlank(daoInterfacePackageName)) {
	    view.appendErrorConsoleTxt("dao接口名为空,生成失败...");
	    return;
	}
	if (StringUtils.isBlank(interfacePackageName)) {
	    view.appendErrorConsoleTxt("service接口名为空,生成失败...");
	    return;
	}
	if (StringUtils.isBlank(implPackageName)) {
	    view.appendErrorConsoleTxt("service实现名为空,生成失败...");
	    return;
	}
	if (StringUtils.isBlank(classFileDirectory)) {
	    view.appendErrorConsoleTxt("service保存到的service项目未选择,生成失败...");
	    return;
	}

	// 生成Service接口
	String interfacePackage = interfacePackageName.substring(0,
		interfacePackageName.lastIndexOf("."));
	String interfaceName = interfacePackageName
		.substring(interfacePackageName.lastIndexOf(".") + 1);
	String interfaceFileParentPath = FilePathUtil.apiProjectBasePath()
		+ interfacePackage.replace(".", "\\") + "\\";
	String interfaceFilePath = interfaceFileParentPath + interfaceName + ".java";
	Map<String, Object> interfaceModel = new HashMap<>();
	List<String> interfaceImportList = new NoRepeatList<>();
	interfaceImportList.add(entityFilePackageName);
	interfaceModel.put("package", interfacePackage);
	interfaceModel.put("importList", interfaceImportList);
	interfaceModel.put("className", interfaceName);
	interfaceModel.put("classComment", view.getEntityComment());
	interfaceModel.put("entityName", entityName);

	File interfaceFile = new File(interfaceFilePath);
	if (interfaceFile.exists() && !overwrite) {
	    view.appendErrorConsoleTxt("service接口生成失败,该文件已存在!如需覆盖,请勾选是否覆盖选项!");
	    return;
	}
	File interfaceFileParent = new File(interfaceFileParentPath);
	if (!interfaceFileParent.exists()) {
	    interfaceFileParent.mkdirs();
	}
	FreemarkerTemplateUtil.INSTANCE.processTemplateIntoFile(interfaceFile, "serviceInterface",
		interfaceModel);
	view.appendSuccessConsoleTxt(interfaceName + "生成完成,路径: ");
	view.appendSuccessConsoleTxt(interfaceFilePath);

	// 生成Service实现
	String daoInterfaceName = daoInterfacePackageName
		.substring(daoInterfacePackageName.lastIndexOf(".") + 1);
	String implPackage = implPackageName.substring(0, implPackageName.lastIndexOf("."));
	String implName = implPackageName.substring(implPackageName.lastIndexOf(".") + 1);
	String implFileParentPath = classFileDirectory + "\\src\\main\\java\\"
		+ implPackage.replace(".", "\\") + "\\";
	String implFilePath = implFileParentPath + implName + ".java";
	Map<String, Object> implModel = new HashMap<>();
	List<String> implImportList = new NoRepeatList<>();
	implImportList.add(entityFilePackageName);
	implImportList.add(interfacePackageName);
	implImportList.add(daoInterfacePackageName);
	implModel.put("package", implPackage);
	implModel.put("importList", implImportList);
	implModel.put("className", implName);
	implModel.put("classComment", view.getEntityComment());
	implModel.put("entityName", entityName);
	implModel.put("interfaceName", interfaceName);
	implModel.put("daoInterfaceName", daoInterfaceName);
	File implFile = new File(implFilePath);
	if (implFile.exists() && !overwrite) {
	    view.appendErrorConsoleTxt("service实现生成失败,该文件已存在!如需覆盖,请勾选是否覆盖选项!");
	    return;
	}
	File implFileParent = new File(implFileParentPath);
	if (!implFileParent.exists()) {
	    implFileParent.mkdirs();
	}
	FreemarkerTemplateUtil.INSTANCE.processTemplateIntoFile(implFile, "serviceImpl", implModel);
	view.appendSuccessConsoleTxt(implName + "生成完成,路径: ");
	view.appendSuccessConsoleTxt(implFilePath);
    }
    
    private static void generateDao() {
	Map<String, String> entityFileInfo = FilePathUtil.getJavaFileInfo(view.getEntityFile());
	String entityFilePackageName = entityFileInfo.get("packagePath");
	String entityName = entityFilePackageName
		.substring(entityFilePackageName.lastIndexOf(".") + 1);
	ClassnameAndFilePathGroup daoInfo = classnameAndFilePathGroupMap.get("daoInfo");

	String interfacePackageName = daoInfo.getInterfaceNameText().getText().trim();
	String implPackageName = daoInfo.getClassNameText().getText().trim();
	String classFileDirectory = daoInfo.getClassFileDirectoryText().getText().trim();
	if (StringUtils.isBlank(interfacePackageName)) {
	    view.appendErrorConsoleTxt("DAO接口名为空,生成失败...");
	    return;
	}
	if (StringUtils.isBlank(implPackageName)) {
	    view.appendErrorConsoleTxt("DAO实现名为空,生成失败...");
	    return;
	}
	if (StringUtils.isBlank(classFileDirectory)) {
	    view.appendErrorConsoleTxt("DAO保存到的service项目未选择,生成失败...");
	    return;
	}

	// 生成DAO接口
	String interfacePackage = interfacePackageName.substring(0,
		interfacePackageName.lastIndexOf("."));
	String interfaceName = interfacePackageName
		.substring(interfacePackageName.lastIndexOf(".") + 1);
	String interfaceFileParentPath = classFileDirectory + "\\src\\main\\java\\"
		+ interfacePackage.replace(".", "\\") + "\\";
	String interfaceFilePath = interfaceFileParentPath + interfaceName + ".java";
	Map<String, Object> interfaceModel = new HashMap<>();
	List<String> interfaceImportList = new NoRepeatList<>();
	interfaceImportList.add(entityFilePackageName);
	interfaceModel.put("package", interfacePackage);
	interfaceModel.put("importList", interfaceImportList);
	interfaceModel.put("className", interfaceName);
	interfaceModel.put("classComment", view.getEntityComment());
	interfaceModel.put("entityName", entityName);

	File interfaceFile = new File(interfaceFilePath);
	if (interfaceFile.exists() && !overwrite) {
	    view.appendErrorConsoleTxt("DAO接口生成失败,该文件已存在!如需覆盖,请勾选是否覆盖选项!");
	    return;
	}
	File interfaceFileParent = new File(interfaceFileParentPath);
	if (!interfaceFileParent.exists()) {
	    interfaceFileParent.mkdirs();
	}
	FreemarkerTemplateUtil.INSTANCE.processTemplateIntoFile(interfaceFile, "daoInterface",
		interfaceModel);
	view.appendSuccessConsoleTxt(interfaceName + "生成完成,路径: ");
	view.appendSuccessConsoleTxt(interfaceFilePath);

	// 生成DAO实现
	String implPackage = implPackageName.substring(0, implPackageName.lastIndexOf("."));
	String implName = implPackageName.substring(implPackageName.lastIndexOf(".") + 1);
	String implFileParentPath = classFileDirectory + "\\src\\main\\java\\"
		+ implPackage.replace(".", "\\") + "\\";
	String implFilePath = implFileParentPath + implName + ".java";
	Map<String, Object> implModel = new HashMap<>();
	List<String> implImportList = new NoRepeatList<>();
	implImportList.add(entityFilePackageName);
	implImportList.add(interfacePackageName);
	implModel.put("package", implPackage);
	implModel.put("importList", implImportList);
	implModel.put("className", implName);
	implModel.put("classComment", view.getEntityComment());
	implModel.put("entityName", entityName);
	implModel.put("interfaceName", interfaceName);
	File implFile = new File(implFilePath);
	if (implFile.exists() && !overwrite) {
	    view.appendErrorConsoleTxt("DAO实现生成失败,该文件已存在!如需覆盖,请勾选是否覆盖选项!");
	    return;
	}
	File implFileParent = new File(implFileParentPath);
	if (!implFileParent.exists()) {
	    implFileParent.mkdirs();
	}
	FreemarkerTemplateUtil.INSTANCE.processTemplateIntoFile(implFile, "daoImpl", implModel);
	view.appendSuccessConsoleTxt(implName + "生成完成,路径: ");
	view.appendSuccessConsoleTxt(implFilePath);
    }
    

}
