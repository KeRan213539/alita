package top.klw8.alita.devTools.controls;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.springframework.util.AntPathMatcher;

import top.klw8.alita.devTools.utils.GenerateHelper;

/**
 * @ClassName: MatcherSelectDialog
 * @Description: 匹配选择对话框
 * @author klw
 * @date 2019年2月27日 上午10:54:10
 */
public class MatcherSelectDialog extends Dialog {
    
    private MatcherSelectDialog _this = this;
    
    private String listSelected = null;
    
    private String title;
    
    private java.util.List<String> listData;
    
    private AntPathMatcher matcher;

    public MatcherSelectDialog(Shell parent, String title, java.util.List<String> listData) {
	super(parent);
	setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	this.title = title;
	this.listData = listData;
	matcher = new AntPathMatcher();
	matcher.setCaseSensitive(false);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
	this.getShell().setText(title);
	
	Composite container = new Composite(parent, SWT.NONE);
	container.setLayout(new GridLayout(1, true));

	Text matcheText = new Text(container, SWT.BORDER);
	matcheText.setLayoutData(new GridData(300, SWT.DEFAULT));
	
	List list = new List(container, SWT.SINGLE|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
	list.setLayoutData(new GridData(285, 300));
	
	list.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseDoubleClick(MouseEvent e) {
		if(StringUtils.isNotBlank(listSelected)) {
		    _this.close();
		}
	    }
	});
	list.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		if(list.getSelection().length > 0) {
		    listSelected = list.getSelection()[0];
		}
	    }
	});
	matcheText.addModifyListener(new ModifyListener() {
	    @Override
	    public void modifyText(ModifyEvent e) {
		if(listData == null || listData.isEmpty()) {
		    return;
		} 
		String pattern = matcheText.getText();
		if(StringUtils.isNotBlank(pattern)) {
		    java.util.List<String> newListData = new ArrayList<>();
		    listData.forEach(str -> {
			String pattern2 = pattern;
			if(!pattern2.startsWith("*")) {
			    pattern2 = "*" + pattern2;
			}
			if(!pattern2.endsWith("*")) {
			    pattern2 = pattern2 + "*";
			}
			if (matcher.match(pattern2, str)) {
			    newListData.add(str);
			}
		    });
		    list.setItems(newListData.toArray(new String[0]));
		} else {
		    list.setItems(listData.toArray(new String[0]));
		}
	    }
	});
	
	if(listData == null || listData.isEmpty()) {
	    GenerateHelper.showMessageBox("警告:", "没有可以选择的数据", SWT.ICON_WARNING);
	} else {
	    list.setItems(listData.toArray(new String[0]));
	}
	return container;
    }

    @Override
    protected void buttonPressed(int buttonId) {
	// 单击对话框中的按钮后执行此方法，参数buttonId是被单击按钮的ID值。
	if (buttonId == IDialogConstants.OK_ID) {
	    if(StringUtils.isBlank(listSelected)) {
		MessageBox msgBox = new MessageBox(this.getShell(), SWT.ICON_ERROR);
		msgBox.setText(title);
		msgBox.setMessage("没有选择实体!");
		msgBox.open();
		return;
	    }
	    
	}
	super.buttonPressed(buttonId);
    }
    
    public String openDialog() {
	int operResult = super.open();
	if(operResult == 0) {
	    return listSelected;
	}
	return null;
    }

}
