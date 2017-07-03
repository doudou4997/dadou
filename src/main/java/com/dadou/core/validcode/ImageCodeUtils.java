package com.dadou.core.validcode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
public final class ImageCodeUtils {
    /**
     * 图片宽度
     */
    private static final int WIDTH = 120;
    /**
     * 图片高度
     */
    private static final int HEIGHT = 46;
    /**
     * 返回字节数组和验证码字符串
     * @param width
     * @param height
     * @return
     */
    public static  Map<String,Object> createImageCode(){
        ValidateCode validateCode = new SimpleImageCode();
        return validateCode.createImageCode(WIDTH, HEIGHT);
    }
    public static void main(String[] args)throws Exception {
        for(int i =0;i<10;i++){
        Map<String,Object> map = createImageCode();
        byte[] buffer = (byte[]) map.get(ValidateCode.BUFFER);
        File file = new File("E:/yy/"+i+".jpg");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(file);
        out.write(buffer);
        out.close();
        }
    }
   
}
