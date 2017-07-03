package com.dadou.shop.upload;

import com.alibaba.fastjson.JSONObject;
import com.framework.core.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.framework.core.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 图片上传
 * @author liyanan
 * @date Jan 10, 2017 3:07:22 PM
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {

    @RequestMapping("/imageuplod")
    @ResponseBody
    public JSONObject upload(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String pathDir = "/assets/installimages/";
        /** 得到图片保存目录的真实路径 **/
        String realPath = request.getSession().getServletContext()
                .getRealPath(pathDir);
        /** 根据真实路径创建目录 **/
        File saveFilePath = new File(realPath);
        if (!saveFilePath.exists())
            saveFilePath.mkdirs();
        /** 页面控件的文件流 **/
        List<MultipartFile> multipartFiles = multipartRequest
                .getFiles("inputPic");
        /** 获取文件的后缀 **/
        int size = multipartFiles.size();
        for (int i = 0; i < size; i++) {
            MultipartFile multipartFile = multipartFiles.get(i);
            System.out.println(multipartFile.getOriginalFilename());
            String suffix = multipartFile.getOriginalFilename().substring(
                    multipartFile.getOriginalFilename().lastIndexOf("."));
            /** 拼成完整的文件保存路径加文件 **/
            String name = StringUtils.randomUUID() + suffix;
            String fileName = realPath + File.separator + name;
            result.put("name", name);
            result.put("fileName", fileName);
            File file = new File(fileName);
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                result.put("result", 0);
                return result;
            }
        }
        result.put("result", 1);
        return result;
    }
}
