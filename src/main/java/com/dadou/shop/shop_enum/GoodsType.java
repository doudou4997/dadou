package com.dadou.shop.shop_enum;

/**
 * Created by dadou on 2017/6/22.
 * 商品类型 1零食 2生鲜
 */
public enum GoodsType {
        SNACKS("零食", 1),
        FRESH("生鲜", 2);
        // 成员变量
        private String name;
        private int index;
        // 构造方法
        private GoodsType(String name, int index) {
            this.name = name;
            this.index = index;
        }
        // 普通方法
        public static String getName(int index) {
            for (GoodsType c : GoodsType.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
        // get set 方法
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
    }