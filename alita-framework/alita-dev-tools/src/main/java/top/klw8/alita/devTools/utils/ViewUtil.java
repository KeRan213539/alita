package top.klw8.alita.devTools.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import top.klw8.alita.devTools.controls.MatcherSelectDialog;

/**
 * @ClassName: ViewUtil
 * @Description: 视图工具类
 * @author klw
 * @date 2019年2月26日 下午2:21:39
 */
public class ViewUtil {

    /**
     * @ClassName: ScrolledGroup
     * @Description: 选择属性的滚动条group
     * @author klw
     * @date 2019年2月28日 下午1:56:17
     */
    @RequiredArgsConstructor(access=AccessLevel.PRIVATE)
    @Getter
    public class ScrolledGroup {
	
	@NonNull
	private Group group;
	
	@NonNull
	private ScrolledComposite scrolledComposite;
	
	private String groupName;
	
	private Button allCheckBtn;
	
	private Text classNameText;
	
	private Text classFileDirectoryText;
	
	private List<Button> groupCheckboxList = new ArrayList<>();
	
	public void setGroupName(String groupName) {
	    this.groupName = groupName;
	}
	
	public void layout() {
	    scrolledComposite.setMinSize(group.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	    group.layout();
	}
	
	public void setAllCheckBtn(Button allCheckBtn){
	    this.allCheckBtn = allCheckBtn;
	}
	
	public void addCheckBox(Button checkBox) {
	    groupCheckboxList.add(checkBox);
	}
	
	public void setClassNameText(Text classNameText) {
	    this.classNameText = classNameText;
	}
	
	public void setClassFileDirectoryText(Text classFileDirectoryText) {
	    this.classFileDirectoryText = classFileDirectoryText;
	}
    }

    /**
     * @Title: createScrolledGroup
     * @author klw
     * @Description: 创建选择组
     * @param parent
     * @param groupTitle
     * @return
     */
    public static ScrolledGroup createScrolledGroup(Composite parent, String groupTitle) {
	ScrolledComposite scrolledComposite = new ScrolledComposite(parent,
		SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	Group group = new Group(scrolledComposite, SWT.NONE);
	group.setLayout(new GridLayout(1, true));
	group.setText(groupTitle);
	scrolledComposite.setMinSize(group.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	scrolledComposite.setContent(group);
	scrolledComposite.setExpandVertical(true);
	scrolledComposite.setExpandHorizontal(true);
	scrolledComposite.setAlwaysShowScrollBars(true);
	return new ViewUtil().new ScrolledGroup(group, scrolledComposite);
    }
    
    /**
     * @ClassName: ClassnameAndFilePathGroup
     * @Description: 类名和类保存位置的Group
     * @author klw
     * @date 2019年2月28日 下午1:56:58
     */
    @RequiredArgsConstructor(access=AccessLevel.PRIVATE)
    @Getter
    public class ClassnameAndFilePathGroup {
	
	@NonNull
	private Group group;
	
	private Text classNameText;
	
	private Text interfaceNameText;
	
	private Text classFileDirectoryText;
	
	/**
	 * @author klw
	 * @Fields baseUriText : 接口uri目录(开头不要加斜杠),如 user, 该Controller生成的API请求路径为 /user/xxx
	 */
	private Text baseUriText;
	
	/**
	 * @author klw
	 * @Fields catlogIndexText : 后台系统菜单顺序
	 */
	private Text catlogIndexText;
	
	public void layout() {
	    group.layout();
	}
	
	public void setClassNameText(Text classNameText) {
	    this.classNameText = classNameText;
	}
	
	public void setInterfaceNameText(Text interfaceNameText) {
	    this.interfaceNameText = interfaceNameText;
	}
	
	public void setClassFileDirectoryText(Text classFileDirectoryText) {
	    this.classFileDirectoryText = classFileDirectoryText;
	}
	
	public void setBaseUriText(Text baseUriText) {
	    this.baseUriText = baseUriText;
	}
	
	public void setCatlogIndexText(Text catlogIndexText) {
	    this.catlogIndexText = catlogIndexText;
	}
    }
    
    /**
     * @Title: createClassnameAndFilePathGroup
     * @author klw
     * @Description: 生成类名,位置的选择器
     * @param parent
     * @param groupTitle
     * @param grouptype 1: controller, 2: dao, 3: service
     * @return
     */
    public static ClassnameAndFilePathGroup createClassnameAndFilePathGroup(Composite parent, String groupTitle, int grouptype) {
	GridData gridData = new GridData();
	gridData.grabExcessHorizontalSpace = true;
	gridData.horizontalAlignment = GridData.FILL;
	
	Group group = new Group(parent, SWT.NONE);
	group.setLayout(new GridLayout(1, true));
	group.setText(groupTitle);
	
	ClassnameAndFilePathGroup classnameAndFilePathGroup = new ViewUtil().new ClassnameAndFilePathGroup(group);

	if(grouptype >= 2) {
	    // 接口输入框
	    Text interfaceNameText = new Text(group, SWT.BORDER);
	    interfaceNameText.setMessage("输入接口名(包括包路径)");
	    interfaceNameText.setLayoutData(gridData);
	    classnameAndFilePathGroup.setInterfaceNameText(interfaceNameText);
	}else {
	    // 控制器
	    Text baseUriText = new Text(group, SWT.BORDER);
	    baseUriText.setMessage("接口uri目录(开头不要加斜杠),如 user, 该Controller生成的API请求路径为 /user/xxx");
	    baseUriText.setLayoutData(gridData);
	    classnameAndFilePathGroup.setBaseUriText(baseUriText);
	    
	    Text catlogIndexText = new Text(group, SWT.BORDER);
	    catlogIndexText.setMessage("后台系统菜单顺序");
	    catlogIndexText.setLayoutData(gridData);
	    classnameAndFilePathGroup.setCatlogIndexText(catlogIndexText);
	}
	
	// 类名输入框
	Text classNameText = new Text(group, SWT.BORDER);
	classNameText.setMessage("输入类名(包括包路径)");
	classNameText.setLayoutData(gridData);
	classnameAndFilePathGroup.setClassNameText(classNameText);
	if (grouptype == 3) {
	    // service 不选择项目,直接取DAO的
	    Label lblNewLabel = new Label(group, SWT.NONE);
	    lblNewLabel.setText("service保存的项目同DAO");
	} else {  
	    // 项目路径文本框
	    Text filePathText = new Text(group, SWT.BORDER);
	    filePathText.setEditable(false);
	    filePathText.setLayoutData(gridData);
	    classnameAndFilePathGroup.setClassFileDirectoryText(filePathText);

	    Button choiceDirectoryBtn = new Button(group, SWT.NONE);
	    choiceDirectoryBtn.setText("选择保存位置(项目)");

	    choiceDirectoryBtn.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
		    Map<String, String> serviceDirectoryMap = null;
		    if (grouptype == 1) {
			// 选择web项目
			serviceDirectoryMap = FilePathUtil.searchWebApiDirectory();
		    } else {
			// 选择service(DAO)项目
			serviceDirectoryMap = FilePathUtil.searchServiceDirectory();
		    }
		    List<String> directoryList = serviceDirectoryMap.entrySet().stream()
			    .map(entry -> {
				return entry.getKey();
			    }).collect(Collectors.toList());
		    Collections.sort(directoryList);
		    MatcherSelectDialog fd = new MatcherSelectDialog(
			    GenerateHelper.view.getViewShell(), "选择保存位置(项目)", directoryList);
		    String filePath = fd.openDialog();
		    if (StringUtils.isNotBlank(filePath)) {
			filePathText.setText(serviceDirectoryMap.get(filePath));
		    }
		}
	    });
	}
	classnameAndFilePathGroup.layout();
	parent.layout();
	return classnameAndFilePathGroup;
    }

}
