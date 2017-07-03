package com.dadou.core.validcode;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.framework.core.utils.ExceptionUtils;


public class ComplexImageCode extends ValidateCode {

    /**
     * 日志记录类
     */
    public static Log logger = LogFactory.getLog(ComplexImageCode.class);
    
    
    public Map<String, Object> createImageCode(int width, int height) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 定义图像宽度和高度
        // 创建内存图象并获得其图形上下文
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, width, height);
        // 随机数
        String rand = "";
        // 随机对象
        for (int i = 0; i < 4; i++) {
            char c = randomChar();
            rand += c;
            Point p = getPoint(i);
            int size = getSize();
            g.setColor(new Color(random.nextInt(256), random.nextInt(256),
                    random.nextInt(256)));
            g.setPaint(getPaint(p, size));
            g.setFont(new Font(
                    (String) fonts.get(random.nextInt(fonts.size())),
                    getFace(), size));
            g.drawString("" + c, p.x, p.y);
        }
        g.setStroke(new BasicStroke(1.0f));
        g.setPaint(null);
        // 4条划线
        for (int i = 0; i < 4; i++) {
            g.setColor(new Color((int) (Math.random() * 0x00FAEF69F)));
            g.drawLine(random.nextInt(200), random.nextInt(50), random
                    .nextInt(200), random.nextInt(50));
        }
        // 绘制完
        g.dispose();
        // 存到结果集中
        resultMap.put(RAND, rand);
        // 写到浏览器中
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", bos);
        } catch (IOException e) {
            logger.error(ExceptionUtils.formatStackTrace(e));
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                logger.error(ExceptionUtils.formatStackTrace(e));
            }
        }
        // 把流存到结果集中
        resultMap.put(BUFFER, bos.toByteArray());
        return resultMap;
    }

    /**
     * 生成随机字母
     * 
     * @return
     */
    private char randomChar() {
        return charArray[random.nextInt(charArray.length)];
    }

    public Stroke getStroke() {
        BasicStroke bs = new BasicStroke((float) (Math.random() * 3));
        return bs;
    }

    public Paint getPaint(Point p, int size) {
        GradientPaint gp = new GradientPaint(p.x, p.y, new Color(random
                .nextInt(256), random.nextInt(256), random.nextInt(256), random
                .nextInt(256)), p.x, p.y - size, new Color(random.nextInt(256),
                random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        return gp;
    }

    /**
     * 获取点坐标
     * 
     * @param index
     * @return
     */
    public Point getPoint(int index) {
        int a = 1;
        switch (index) {
            case 0:
                a = 10 * index + 16;
                break;
            case 1:
                a = 10 * index + 29;
                break;
            case 2:
                a = 10 * index + 45;
                break;
            case 3:
                a = 10 * index + 56;
                break;
        }
        return new Point(a, 40);
    }

}
