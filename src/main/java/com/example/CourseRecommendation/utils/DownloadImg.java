package com.example.CourseRecommendation.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

/**
 * @description: 根据文件链接地址下载文件
 * @author: zyb
 * @date: 2020/10/13 15:47
 */
public class DownloadImg {


    /**
     * 根据链接地址下载文件
     * @param downloadUrl 文件链接地址
     * @param path        下载存放文件地址 + 文件名
     */
    public static void downloadA(String downloadUrl,String path) {
        URL url = null;
        DataInputStream dataInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            url = new URL(downloadUrl);
            dataInputStream = new DataInputStream(url.openStream());
            fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dataInputStream != null){
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * @param downloadUrl 文件链接地址
     * @param filename    保存文件名
     * @param filePath    保存文件路径
     */
    public static void downloadB(String downloadUrl, String filename, String filePath) {
        URL url = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            url = new URL(downloadUrl);
            // 打开连接
            URLConnection con = url.openConnection();
            // 请求超时:5s
            con.setConnectTimeout(5 * 1000);
            inputStream = con.getInputStream();

            byte[] bytes = new byte[1024];
            // 读取到的数据长度
            int length;
            File savePath = new File(filePath);
            if (!savePath.exists()) {
                // 如果不存在当前文件夹，则创建该文件夹
                boolean mkdir = savePath.mkdirs();
                if (!mkdir) {
                    System.out.println("创建文件夹失败");
                    return;
                }
            }
            outputStream = new FileOutputStream(savePath.getPath() + "\\" + filename);
            // 读取
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static public void saveImageFromBase64(String base64String, String filePath) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        File outputfile = new File(filePath);
        ImageIO.write(image, "jpg", outputfile);
    }
}

