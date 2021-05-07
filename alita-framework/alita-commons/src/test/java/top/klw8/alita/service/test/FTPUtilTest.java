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
package top.klw8.alita.service.test;

import top.klw8.alita.ftp.FtpClient;

import java.io.File;
import java.io.IOException;

/**
 * 测试FTP工具
 * 2020/6/4 11:32
 */
public class FTPUtilTest {

    public static void main(String[] args) {
//        FtpUtil ftpUtil = FtpUtil.connect("139.196.12.148", "21", "2005201451064990782", "123456", null);
//        System.out.println(ftpUtil.checkExists("/2005201451064990782", "bank_settlement.jpg"));
//        try {
//            FileUtils.copyInputStreamToFile(ftpUtil.download("/2005201451064990782", "bank_settlement.jpg", true)
//                    , new File("E:\\test\\bank_settlement.jpg"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        FtpClient ftp = FtpClient.creatClient("139.196.12.148", "21", "2005201451064990782", "123456");
        try {
//            ftp.download("/2005201451064990782/bank_settlement.jpg", "E:\\test\\bank_settlement.jpg");
            ftp.upload("E:\\test\\bank_settlement.jpg", "/test4/test.jpg");
//            System.out.println("1上传完成");

            String imageFileDirectory = "E:\\test\\";
            String remotePath = "test3";
            File businessLicense = new File(imageFileDirectory + "bank_settlement.jpg");
            File bankSettlement = new File(imageFileDirectory + "test.jpg");
            File legalCerificateFront = new File(imageFileDirectory + "test.jpg");
            File legalCerificateBack = new File(imageFileDirectory + "test.jpg");
            boolean uploadResult;
            uploadResult = ftp.upload(businessLicense.getPath(), remotePath + "/business_license.jpg");
            System.out.println("business_license ====" + uploadResult);
            uploadResult = ftp.upload(bankSettlement.getPath(), remotePath+ "/bank_settlement.jpg");
            System.out.println("bank_settlement ====" + uploadResult);
            uploadResult = ftp.upload(legalCerificateFront.getPath(), remotePath + "/legal_cerificate_front.jpg");
            System.out.println("legal_cerificate_front ====" + uploadResult);
            uploadResult = ftp.upload(legalCerificateBack.getPath(), remotePath+ "/legal_cerificate_back.jpg");
            System.out.println("legal_cerificate_back ====" + uploadResult);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
