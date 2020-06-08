package com.leyou.controller;

import org.apache.commons.lang.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Value("${user.httpImageYuming}")
    private String httpImage;

    public static final List<String> FILE_TYPE = Arrays.asList("jpg","png");

    @RequestMapping("image")
    public String uploadImage(@RequestParam("file") MultipartFile file){
        /*
        * 1.验证图片的格式
        * 2.验证图片内容
        * 3.验证图片大小
        * */


        try {
            String filename = file.getOriginalFilename();
            String type1 = filename.substring(filename.lastIndexOf(".") + 1);
            String type2 = StringUtils.substringAfterLast(filename, ".");
            System.out.println(type1+"---"+type2);
            //1.验证文件格式，通过截取字符串的方式获取
            if(!FILE_TYPE.contains(type1)){
                return null;
            }
            //2.校验图片内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage==null){
                return null;
            }
            String filePath = System.currentTimeMillis()+filename;
            file.transferTo(new File("d:/upload/"+filePath));
            /*//加载客户端配置文件，配置文件中指明了tracker服务器的地址
            ClientGlobal.init("fastdfs.conf");
            //验证配置文件是否加载成功
            System.out.println(ClientGlobal.configInfo());

            //创建TrackerClient对象，客户端对象
            TrackerClient trackerClient = new TrackerClient();

            //获取到调度对象，也就是与Tracker服务器取得联系
            TrackerServer trackerServer = trackerClient.getConnection();

            //创建存储客户端对象
            StorageClient storageClient = new StorageClient(trackerServer,null);


//            NameValuePair[] params = new NameValuePair[1];
//            NameValuePair p = new NameValuePair();
//            p.setName("创建时间");p.setValue("333");
//            params[0] = p;
//            String[] upload_file = storageClient.upload_file("D:/test.jpg", "jpg", params);

            String[] upload_file = storageClient.upload_appender_file(file.getBytes(), type1, null);

            for (String string : upload_file) {
                System.out.println(string);
            }

            return httpImage+upload_file[0]+"/"+upload_file[1];*/
            return httpImage+filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
