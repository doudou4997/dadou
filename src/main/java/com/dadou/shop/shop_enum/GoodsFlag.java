package com.dadou.shop.shop_enum;

/**
 * Created by dadou on 2017/6/22.
 * 商品状态 1未上架 2上架 3缺货报警
 */
public enum GoodsFlag {
        UNUSED("未上架", 1),
        USED("上架", 2),
        WARNING("缺货报警", 3);
        // 成员变量
        private String name;
        private int index;
        // 构造方法
        private GoodsFlag(String name, int index) {
            this.name = name;
            this.index = index;
        }
        // 普通方法
        public static String getName(int index) {
            for (GoodsFlag c : GoodsFlag.values()) {
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