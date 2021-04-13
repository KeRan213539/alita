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
package top.klw8.alita.fileupload.helpers;

/**
 * 上传的文件类型和对应文件夹
 * 2018年11月19日 下午3:14:50
 */
public enum FileUploadTypeEnum {
    
    /**
     * OSS_BUCKET_NAME : OSS BUCKET 的名称
     */
    OSS_BUCKET_NAME("alita-test"),

    /** 临时图片路径 */
    IMG_TMP_PATH("tmp"),

    /** 头像 */
    IMG_HEAD_PATH("head"),

    /** 音频 */
    IMG_AUDO_PATH("audio"),

    /** 视频 */
    IMG_VIDEO_PATH("video"),

    /** 文件 */
    IMG_FILE_PATH("file"),

    /** 图片 */
    IMG_IMG_PATH("img"),

    /** 公司 */
    IMG_COMPANY_PATH("company"),

    /** 用户 */
    IMG_USER_PATH("user"),

    /** 二维码 */
    IMG_Code2D_PATH("code2D"),;

    private String typePath;

    private FileUploadTypeEnum(String typePath) {
	this.typePath = typePath;
    }

    public String getTypePath() {
	return typePath;
    }

}
