package top.klw8.alita.devTools.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @ClassName: FreemarkerTemplateUtil
 * @Description: Freemarker 工具类
 * @author klw
 * @date 2019-02-28 16:20:40
 */
public enum FreemarkerTemplateUtil {
    
    INSTANCE;

    private Configuration config;

    private FreemarkerTemplateUtil() {
	config = new Configuration(Configuration.VERSION_2_3_28);
	config.setClassForTemplateLoading(FreemarkerTemplateUtil.class, "/ftls");
	config.setObjectWrapper(
		new DefaultObjectWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));

	config.setTemplateUpdateDelayMilliseconds(1000L * 5);
	config.setEncoding(Locale.getDefault(), "UTF-8");
	config.setDefaultEncoding("UTF-8");
	config.setURLEscapingCharset("UTF-8");
	config.setLocale(Locale.CHINA);
	config.setNumberFormat("0.######");
	config.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
	config.setDateFormat("yyyy-MM-dd");
	config.setTimeFormat("HH:mm:ss");
	config.setBooleanFormat("true,false");
	config.setWhitespaceStripping(true);
	config.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
    }

    public String processTemplateIntoString(String templatePath, Map<String, Object> model) {
	// 添加自定义模板
	Writer out = null;
	try {
	    Template template = config.getTemplate(templatePath + ".ftl", "UTF-8");
	    out = new StringWriter();
	    template.process(model, out);
	    out.flush();
	    out.close();

	    return out.toString();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (TemplateException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (out != null) {
		    out.flush();
		    out.close();
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	   
	}

	return StringUtils.EMPTY;
    }
    
    public void processTemplateIntoFile(File file, String templatePath, Map<String, Object> model) {
	// 添加自定义模板
	Writer out = null;
	try {
	    Template template = config.getTemplate(templatePath + ".ftl", "UTF-8");
	    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
	    template.process(model, out);
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (TemplateException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (out != null) {
		    out.flush();
		    out.close();
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	   
	}
    }

}
