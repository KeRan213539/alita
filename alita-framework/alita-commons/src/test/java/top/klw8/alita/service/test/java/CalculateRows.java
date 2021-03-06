/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.service.test.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 统计java代码行数
 * 2019年1月29日 下午6:13:42
 */
public class CalculateRows {

    private static Integer code = 0;
    private static Integer codeComments = 0;
    private static Integer codeBlank = 0;
    
    public static void main(String[] args) {
        File file = new File("D:\\klwWork\\eclipseWorkspace\\WorkspacesMe\\alita\\");
        factFiles(file);
        System.out.println("代码行数" + code);
        System.out.println("空白行数" + codeBlank);
        System.out.println("注释行数" + codeComments);
    }
    
    public static void factFiles(File file) {
        BufferedReader br = null;
        String s = null;
        
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File f : files) {
                factFiles(f);
            }
        } else {
	    if (file.getName().endsWith(".java")) {
		try {
		    br = new BufferedReader(new FileReader(file));
		    boolean comm = false;
		    while ((s = br.readLine()) != null) {
			if (s.startsWith("/*") && s.endsWith("*/")) {
			    codeComments++;
			} else if (s.trim().startsWith("//")) {
			    codeComments++;
			} else if (s.startsWith("/*") && !s.endsWith("*/")) {
			    codeComments++;
			    comm = true;
			} else if (!s.startsWith("/*") && s.endsWith("*/")) {
			    codeComments++;
			    comm = false;
			} else if (comm) {
			    codeComments++;
			} else if (s.trim().length() < 1) {
			    codeBlank++;
			} else {
			    code++;
			}
		    }
		    br.close();
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
        }
    }
    
}
