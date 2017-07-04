package com.dadou.shop.shop_enum;

/**
 * Created by dadou on 2017/6/22.
 * 货柜类型 1四层货柜 2五层货柜 3初级货柜 4高级货柜
 */
public enum ShelvesType {
        FOUR("四层货柜", 1),
        FIVE("五层货柜", 2),
        JUNIOR("初级货柜", 3),
        SENIOR("高级货柜", 4);
        // 成员变量
        private String name;
        private int index;
        // 构造方法
        private ShelvesType(String name, int index) {
            this.name = name;
            this.index = index;
        }
        // 普通方法
        public static String getName(int index) {
            for (ShelvesType c : ShelvesType.values()) {
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