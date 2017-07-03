package com.dadou.core.validcode;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class ValidateCode {
    /**
     * 随机对象
     */
    public static final Random random = new Random();
    /**
     * 字符
     */
    public static final char[] charArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
    public final static List<String> fonts = new ArrayList<String>();
    /**
     * 类加载时,把字体加载进来
     */
    static {

        initFonts();
    }

    /**
     * 初始化中文字体 仿宋、宋体等
     */
    private static void initFonts() {
        //获取环境中所有输入法
        // GraphicsEnvironment.getLocalGraphicsEnvironment().preferLocaleFonts();
        // String[] names = GraphicsEnvironment.getLocalGraphicsEnvironment()
        // .getAvailableFontFamilyNames(Locale.CHINA);
        // for (String s : names) {
        // char c = Character.toUpperCase(s.charAt(0));
        // if ((c >= 64 && c <= 90)) {
        // fonts.add(s);
        // }
        // }
        fonts.add("Arial");
        fonts.add("Times New Roman");
        fonts.add("宋体");
        fonts.add("楷体");
        fonts.add("黑体");
    }

    /**
     * 随机字符串
     */
    public static final String RAND = "rand";
    /**
     * 随机字节数组
     */
    public static final String BUFFER = "buffer";

    public Map<String, Object> createImageCode(int width, int height) {
        return null;
    }

    public int getFace() {
        if (Math.random() * 10 > 5) {
            return Font.BOLD | Font.TYPE1_FONT | Font.LAYOUT_NO_LIMIT_CONTEXT;
        } else {
            return Font.ITALIC | Font.LAYOUT_LEFT_TO_RIGHT
                    | Font.LAYOUT_NO_START_CONTEXT;
        }
    }

    public int getSize() {
       //字体大小
        return 35;
    }
}
