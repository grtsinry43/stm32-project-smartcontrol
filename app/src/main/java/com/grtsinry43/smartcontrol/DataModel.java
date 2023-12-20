package com.grtsinry43.smartcontrol;

public class DataModel {
    private String 商品名;
    private Person 发件人;
    private Person 收件人;
    private String 收件地址;
    private String 区域;
    private String 路线;

    // Getters and setters for 商品名, 收件地址, 区域, 路线...

    public Person get发件人() {
        return 发件人;
    }

    public void set发件人(Person 发件人) {
        this.发件人 = 发件人;
    }

    public Person get收件人() {
        return 收件人;
    }

    public void set收件人(Person 收件人) {
        this.收件人 = 收件人;
    }

    public static class Person {
        private String 姓名;
        private String 电话;

        @Override
        public String toString() {
            return "姓名: " + 姓名 + '\t' +
                    "电话: " + 电话 + '\t' +
                    '\n';
        }
        // Getters and setters for 姓名, 电话...
    }

    @Override
    public String toString() {
        return "商品名  " + 商品名 + '\n' +
                "发件人\n" + 发件人 +
                "收件人\n" + 收件人 +
                "收件地址   " + 收件地址 + '\n' +
                "区域    " + 区域 + '\n' +
                "路线    " + 路线 ;
    }
}
