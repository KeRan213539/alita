package top.klw8.alita.devTools;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.RowLayout;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;

import top.klw8.alita.devTools.controls.MatcherSelectDialog;
import top.klw8.alita.devTools.utils.FilePathUtil;
import top.klw8.alita.devTools.utils.GenerateHelper;
import top.klw8.alita.devTools.utils.JavaParserUtil;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

/**
 * @ClassName: View
 * @Description: UI
 * @author klw
 * @date 2019年2月25日 下午4:55:54
 */
public class View {
    
    private Shell shlDevTools = null;
    
    private StyledText txtConsole;
    
    private Text filePathText;
    
    private String entityComment;
    
    private Group checkBoxGroup;
    
    /**
     * @author klw
     * @Fields middleComposite : 中间的 Composite (几个选择按钮组的父容器)
     */
    private Composite middleComposite;
    
    /**
     * @author klw
     * @Fields topBottomComposite : 上面的 topComposite 的下半部分(DAO,Service,controller等类名和存放位置的表单)
     */
    private Composite topBottomComposite;
    
    public Shell getViewShell() {
	return shlDevTools;
    }
    
    public void appendInfoConsoleTxt(final String msg) {
	if (!shlDevTools.getDisplay().isDisposed()) {
	    shlDevTools.getDisplay().asyncExec(new Runnable() {
		@Override
		public void run() {
		    StyleRange styleRange = getColorStyle(txtConsole.getText().length(), msg.length(), new Color(shlDevTools.getDisplay(), 0, 191, 255));
		    txtConsole.append(msg + "\n");
		    txtConsole.setStyleRange(styleRange);
		    txtConsole.setSelection(txtConsole.getCharCount());
		}
	    });
	}
    }
    
    public void appendWarningConsoleTxt(final String msg) {
	if (!shlDevTools.getDisplay().isDisposed()) {
	    shlDevTools.getDisplay().asyncExec(new Runnable() {
		@Override
		public void run() {
		    StyleRange styleRange = getColorStyle(txtConsole.getText().length(), msg.length(), new Color(shlDevTools.getDisplay(), 255, 127, 0));
		    txtConsole.append(msg + "\n");
		    txtConsole.setStyleRange(styleRange);
		    txtConsole.setSelection(txtConsole.getCharCount());
		}
	    });
	}
    }
    
    public void appendSuccessConsoleTxt(final String msg) {
	if (!shlDevTools.getDisplay().isDisposed()) {
	    shlDevTools.getDisplay().asyncExec(new Runnable() {
		@Override
		public void run() {
		    StyleRange styleRange = getColorStyle(txtConsole.getText().length(), msg.length(), SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		    txtConsole.append(msg + "\n");
		    txtConsole.setStyleRange(styleRange);
		    txtConsole.setSelection(txtConsole.getCharCount());
		}
	    });
	}
    }
    
    public void appendErrorConsoleTxt(final String msg) {
	if (!shlDevTools.getDisplay().isDisposed()) {
	    shlDevTools.getDisplay().asyncExec(new Runnable() {
		@Override
		public void run() {
		    StyleRange styleRange = getColorStyle(txtConsole.getText().length(), msg.length(), SWTResourceManager.getColor(SWT.COLOR_RED));
		    txtConsole.append(msg + "\n");
		    txtConsole.setStyleRange(styleRange);
		    txtConsole.setSelection(txtConsole.getCharCount());
		}
	    });
	}
    }
    
    private StyleRange getColorStyle(int startOffset, int length, Color color) {
	return new StyleRange(startOffset, length, color, null);
    }
    
    public File getEntityFile() {
	return new File(filePathText.getText().trim());
    }
    
    public String getEntityComment() {
	return entityComment;
    }
    
    /**
     * Open the window.
     * 
     * @wbp.parser.entryPoint
     */
    public void open() {
	Display display = Display.getDefault();
	shlDevTools = new Shell(display);
	shlDevTools.setSize(1460, 900);
	shlDevTools.setText("Alita-代码生成工具");
	shlDevTools.setLayout(new FillLayout(SWT.VERTICAL));

	Composite topComposite = new Composite(shlDevTools, SWT.NONE); // 上面的 topComposite(文件选择,生成类型选择,几个按钮的爷爷容器)
	topComposite.setLayout(new FillLayout(SWT.VERTICAL));
	Composite topUpComposite = new Composite(topComposite, SWT.NONE); // 上面的 topComposite 的上半部分(文件选择,生成类型选择,几个按钮的父容器)
	topUpComposite.setLayout(new FormLayout());
	topBottomComposite = new Composite(topComposite, SWT.NONE);  // 上面的 topComposite 的下半部分(DAO,Service,controller等类名和存放位置的表单)
	topBottomComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
	
	
	filePathText = new Text(topUpComposite, SWT.BORDER);
	FormData fd_filePathText = new FormData();
	fd_filePathText.top = new FormAttachment(0, 10);
	fd_filePathText.left = new FormAttachment(0, 10);
	filePathText.setLayoutData(fd_filePathText);
	filePathText.setEditable(false);
	Button choiceJavaFileBtn = new Button(topUpComposite, SWT.NONE);
	fd_filePathText.right = new FormAttachment(choiceJavaFileBtn, -6);
	FormData fd_choiceJavaFileBtn = new FormData();
	fd_choiceJavaFileBtn.top = new FormAttachment(0, 8);
	fd_choiceJavaFileBtn.right = new FormAttachment(100, -10);
	choiceJavaFileBtn.setLayoutData(fd_choiceJavaFileBtn);
	choiceJavaFileBtn.setText("选择实体文件");
	
	
	Button analysisFileBtn = new Button(topUpComposite, SWT.NONE);
	FormData fd_analysisFileBtn = new FormData();
	fd_analysisFileBtn.left = new FormAttachment(filePathText, 0, SWT.LEFT);
	analysisFileBtn.setLayoutData(fd_analysisFileBtn);
	analysisFileBtn.setEnabled(false);
	analysisFileBtn.setText("解析文件");
	Button buildFileBtn = new Button(topUpComposite, SWT.NONE);
	FormData fd_buildFileBtn = new FormData();
	fd_buildFileBtn.left = new FormAttachment(filePathText, 0, SWT.LEFT);
	buildFileBtn.setLayoutData(fd_buildFileBtn);
	buildFileBtn.setEnabled(false);
	buildFileBtn.setText("生成代码");
	
	Button btnClean = new Button(topUpComposite, SWT.NONE);
	fd_buildFileBtn.bottom = new FormAttachment(btnClean, -6);
	FormData fd_btnClean = new FormData();
	fd_btnClean.bottom = new FormAttachment(100, -10);
	fd_btnClean.right = new FormAttachment(analysisFileBtn, 0, SWT.RIGHT);
	btnClean.setLayoutData(fd_btnClean);
	btnClean.setText("清空消息");
	
	Composite consoleComposite = new Composite(topUpComposite, SWT.NONE);
	FormData fd_consoleComposite = new FormData();
	fd_consoleComposite.right = new FormAttachment(100);
	fd_consoleComposite.top = new FormAttachment(0, 88);
	fd_consoleComposite.bottom = new FormAttachment(100);
	consoleComposite.setLayoutData(fd_consoleComposite);
	consoleComposite.setLayout(new GridLayout(1, true));
//	consoleComposite.setLayoutData(new RowData(800,500));
	
	txtConsole = new StyledText(consoleComposite, SWT.WRAP | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	GridData gridData = new GridData();
	gridData.widthHint = 0;
	gridData.heightHint = 122;
	gridData.horizontalAlignment = GridData.FILL;
	gridData.verticalAlignment = GridData.FILL;
	gridData.grabExcessVerticalSpace = true;
	gridData.grabExcessHorizontalSpace = true;
	txtConsole.setLayoutData(gridData);
	txtConsole.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
	txtConsole.setEditable(false);
	txtConsole.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
	
	
	checkBoxGroup = new Group(topUpComposite, SWT.NONE);
	fd_analysisFileBtn.top = new FormAttachment(checkBoxGroup, 6);
	FormData fd_checkBoxGroup = new FormData();
	fd_checkBoxGroup.right = new FormAttachment(choiceJavaFileBtn, 0, SWT.RIGHT);
	fd_checkBoxGroup.top = new FormAttachment(filePathText, 6);
	fd_checkBoxGroup.left = new FormAttachment(0, 10);
	checkBoxGroup.setLayoutData(fd_checkBoxGroup);
	checkBoxGroup.setLayout(new RowLayout());
	checkBoxGroup.setText("选择生成内容");
	
	Button isGenerateAllCheckBtn = new Button(checkBoxGroup, SWT.CHECK);
	isGenerateAllCheckBtn.setText("全选");
	isGenerateAllCheckBtn.setSelection(false);
//	Button isGenerateDaoBtn = new Button(checkBoxGroup, SWT.CHECK);
//	isGenerateDaoBtn.setText("生成DAO");
//	isGenerateDaoBtn.setSelection(true);
//	Button isGenerateServiceBtn = new Button(checkBoxGroup, SWT.CHECK);
//	isGenerateServiceBtn.setText("生成Service");
//	isGenerateServiceBtn.setSelection(true);
	Button isGenerateVoBtn = new Button(checkBoxGroup, SWT.CHECK);
	isGenerateVoBtn.setText("单独生成普通VO");
	isGenerateVoBtn.setSelection(false);
	Button isGenerateAddApiBtn = new Button(checkBoxGroup, SWT.CHECK);
	isGenerateAddApiBtn.setText("生成添加保存Api(和VO)");
	isGenerateAddApiBtn.setSelection(true);
	Button isGenerateModifyApiBtn = new Button(checkBoxGroup, SWT.CHECK);
	isGenerateModifyApiBtn.setText("生成修改保存Api(和VO)");
	isGenerateModifyApiBtn.setSelection(true);
	Button isGenerateListBtn = new Button(checkBoxGroup, SWT.CHECK);
	isGenerateListBtn.setText("生成不分页列表Api(和VO)");
	isGenerateListBtn.setSelection(true);
	Button isGenerateSkipPageApiBtn = new Button(checkBoxGroup, SWT.CHECK);
	isGenerateSkipPageApiBtn.setText("生成skip方式分页Api(和VO)");
	isGenerateSkipPageApiBtn.setSelection(true);
	Button isGenerateComparativePageApiBtn = new Button(checkBoxGroup, SWT.CHECK);
	isGenerateComparativePageApiBtn.setText("生成比较方式分页Api(和VO)");
	isGenerateComparativePageApiBtn.setSelection(true);
	setCheckboxGroupEnabled(false);
	
	Button button = new Button(topUpComposite, SWT.CHECK);
	fd_consoleComposite.left = new FormAttachment(button);
	button.addSelectionListener(new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
		    Button _this = (Button) e.widget;
		    GenerateHelper.overwrite = _this.getSelection();
		}
	});
	FormData fd_button = new FormData();
	fd_button.top = new FormAttachment(analysisFileBtn, 6);
	fd_button.left = new FormAttachment(0, 10);
	button.setLayoutData(fd_button);
	button.setText("覆盖已有文件");
	
	btnClean.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		txtConsole.setText("");
	    }
	});

	// 中间的 Composite (几个选择按钮组的父容器)
	middleComposite = new Composite(shlDevTools, SWT.BORDER);
	middleComposite.setLayout(new FillLayout());
	
	
	for (Control control : checkBoxGroup.getChildren()) {
	    if (control instanceof Button) {
		Button checkBoxBtn = ((Button) control);
		if (checkBoxBtn.getText().equals("全选")) {
		    checkBoxBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			    Button _this = (Button) e.widget;
			    boolean allChecked = _this.getSelection();
			    for (Control control : checkBoxGroup.getChildren()) {
				if (control instanceof Button) {
				    ((Button) control).setSelection(allChecked);
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
				for (Control control : checkBoxGroup.getChildren()) {
				    if (control instanceof Button) {
					Button checkbox = (Button) control;
					if (!checkbox.getText().equals("全选")
						&& !checkbox.getSelection()) {
					    flag = false;
					}
				    }
				}
				isGenerateAllCheckBtn.setSelection(flag);
			    } else {
				isGenerateAllCheckBtn.setSelection(false);
			    }
			}
		    });
		}
	    }
	}
	
	
	buildFileBtn.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		GenerateHelper.buildFiles();
	    }
	});

	choiceJavaFileBtn.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		Map<String, Map<String, String>> fileMap = FilePathUtil.searchEntityFiles();
		List<String> fileList = fileMap.entrySet().stream().map(entry -> {
		    return entry.getKey();
		}).collect(Collectors.toList());
		Collections.sort(fileList);
		MatcherSelectDialog ms = new MatcherSelectDialog(shlDevTools, "选择实体", fileList);
		String msResult = ms.openDialog();
		if (StringUtils.isNotBlank(msResult)) {
		    String filePath = fileMap.get(msResult).get("filePath");
		    // 验证是否是实体
		    ParseResult<CompilationUnit> cu = JavaParserUtil.parse(filePath);
		    cu.getResult().get().getChildNodes().forEach(node -> {
			if (node instanceof ClassOrInterfaceDeclaration) {
			    ClassOrInterfaceDeclaration classDec = (ClassOrInterfaceDeclaration) node;
			    Optional<AnnotationExpr> annotation = classDec
				    .getAnnotationByName("Document");
			    if (!annotation.isPresent()) {
				appendErrorConsoleTxt("选择的不是实体!");
				GenerateHelper.showMessageBox("错误:", "选择的不是实体", SWT.ICON_ERROR);
			    } else {
				filePathText.setText(filePath);
				analysisFileBtn.setEnabled(true);
				setCheckboxGroupEnabled(true);
				cleanDynamicallyGeneratedUi();
				buildFileBtn.setEnabled(false);
			    }
			}
		    });
		}
	    }
	});

	analysisFileBtn.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		appendInfoConsoleTxt("开始解析...");
		setCheckboxGroupEnabled(false);
		analysisFileBtn.setEnabled(false);
		cleanDynamicallyGeneratedUi();
		
		GenerateHelper.isGenerateVo = isGenerateVoBtn.getSelection();
		GenerateHelper.isGenerateAddApi = isGenerateAddApiBtn.getSelection();
		GenerateHelper.isGenerateModifyApi = isGenerateModifyApiBtn.getSelection();
		GenerateHelper.isGenerateList = isGenerateListBtn.getSelection();
		GenerateHelper.isGenerateSkipPageApi = isGenerateSkipPageApiBtn.getSelection();
		GenerateHelper.isGenerateComparativePageApi = isGenerateComparativePageApiBtn.getSelection();
		// 判断是否只选择了生成VO
		if(GenerateHelper.isGenerateVo && !GenerateHelper.isGenerateAddApi && !GenerateHelper.isGenerateModifyApi 
			&& !GenerateHelper.isGenerateList && !GenerateHelper.isGenerateSkipPageApi && !GenerateHelper.isGenerateComparativePageApi) {
		    GenerateHelper.isGenerateDao = false;
		    GenerateHelper.isGenerateService = false;
		    GenerateHelper.isGenerateController = false;
		} else {
		    GenerateHelper.isGenerateDao = true;
		    GenerateHelper.isGenerateService = true;
		    GenerateHelper.isGenerateController = true;
		}
		
		
		GenerateHelper.generateScrolledGroup(middleComposite, topBottomComposite);
		
		ParseResult<CompilationUnit> cu = JavaParserUtil.parse(filePathText.getText().trim());
		cu.getResult().get().getChildNodes().forEach(node -> {
		    if (node instanceof ClassOrInterfaceDeclaration) {
			ClassOrInterfaceDeclaration classDec = (ClassOrInterfaceDeclaration) node;
			entityComment = JavaParserUtil.parseClassComment(classDec.getComment().get().getContent());
			classDec.getMembers().forEach(member -> {
			    if (member instanceof FieldDeclaration) {
				FieldDeclaration fieldDec = (FieldDeclaration) member;
				String fieldName = fieldDec.getVariable(0).getNameAsString();
				if(fieldName.equals("serialVersionUID")) {
				    return;
				}
				String fieldComment = "TODO: 添加注释";
				if (fieldDec.getComment().isPresent()) {
				    fieldComment = JavaParserUtil.parseFieldComment(fieldName,
						fieldDec.getComment().get().getContent());
				}
				Map<String, String> extData = new HashMap<>();
				extData.put("fieldName", fieldName);
				extData.put("fieldComment", fieldComment);
				extData.put("fieldType", fieldDec.getElementType().asString());
				GenerateHelper.addCheckBox2ScrolledGroups(fieldName, extData);
			    }
			});
		    }
		});
		GenerateHelper.layoutScrolledGroups();
		buildFileBtn.setEnabled(true);
		shlDevTools.layout();
		middleComposite.layout();
		appendSuccessConsoleTxt("解析完成!");
	    }
	});

	shlDevTools.open();
	shlDevTools.layout();
	while (!shlDevTools.isDisposed()) {
	    if (!display.readAndDispatch()) {
		display.sleep();
	    }
	}
    }
    
    /**
     * @Title: cleanDynamicallyGeneratedUi
     * @author klw
     * @Description: 清空所有动态生成的UI
     */
    private void cleanDynamicallyGeneratedUi() {
	Control[] chlidens = middleComposite.getChildren();
	for (Control chliden : chlidens) {
	    chliden.dispose();
	}
	Control[] chlidens2 = topBottomComposite.getChildren();
	for (Control chliden : chlidens2) {
	    chliden.dispose();
	}
    }
    
    /**
     * @Title: setCheckboxGroupEnabled
     * @author klw
     * @Description: 设置生成选项的checkbox的可用状态
     * @param enabled
     */
    private void setCheckboxGroupEnabled(boolean enabled) {
	for (Control control : checkBoxGroup.getChildren()) {
	    if (control instanceof Button) {
		Button checkbox = (Button) control;
		checkbox.setEnabled(enabled);
	    }
	}
    }
}
