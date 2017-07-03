package com.dadou.core.validcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.framework.core.utils.ExceptionUtils;

public class SimpleImageCode extends ValidateCode{
    /**
     * 日志记录类
     */
    public static Log logger = LogFactory.getLog(SimpleImageCode.class);
    /**
     * 随机对象
     */
    public static final Random random = new Random();
    public Map<String, Object> createImageCode(int width, int height) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        // 定义图像宽度和高度
        // 创建内存图象并获得其图形上下文
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 产生随机的认证码
        Random random = new Random();
        //设置背景颜色
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        String sRand = "";
        //随机生成各种颜色的字符
        for (int i = 0; i < 4; i++) {
            char c = randomChar();
            g.setFont(new Font(
                    (String) fonts.get(random.nextInt(fonts.size())),
                    getFace(), getSize()));
            sRand += c;
            g.setColor(new Color(20 + random.nextInt(140), 20 + random
                    .nextInt(140), 20 + random.nextInt(140)));
            int x = 27 * i + 6;
            int y = 36;
            double r = Math.random() - 0.5;
            g.rotate(r, x, y);
            g.drawString(c + "", x, y);
            g.rotate(-r, x, y);
        }
        g.setStroke(new BasicStroke(1.0f));
        g.setPaint(null);
        // 4条划线
        for (int i = 0; i < 4; i++) {
            g.setColor(new Color((int) (Math.random() * 0x00FAEF69F)));
            g.drawLine(random.nextInt(200), random.nextInt(50), random
                    .nextInt(200), random.nextInt(50));
        }
        // 结束图像的绘制过程,完成图像
        g.dispose();
        //存到结果集中
        resultMap.put(RAND, sRand);
        // 写到浏览器中
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", bos);
        } catch (IOException e) {
            logger.error(ExceptionUtils.formatStackTrace(e));
        }finally{
            try {
                bos.close();
            } catch (IOException e) {
                logger.error(ExceptionUtils.formatStackTrace(e));
            }
        }
        //把流存到结果集中
        resultMap.put(BUFFER, bos.toByteArray());
        return resultMap;
    }
    private  Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
    /**
     * 生成随机字母
     * 
     * @return
     */
    private char randomChar() {
        return charArray[random.nextInt(charArray.length)];
    }

}
