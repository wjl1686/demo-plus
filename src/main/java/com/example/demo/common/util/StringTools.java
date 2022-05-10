package com.example.demo.common.util;

import com.alibaba.fastjson.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class StringTools {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getPassword(String userName, String password) {
        Encrypt encrypt = new Encrypt();
        String encodePasswordOne = encrypt.SHA512(password);
        String encodePasswordTow = encrypt.SHA512(encodePasswordOne + "eJoBim" + userName);
        return encodePasswordTow;
    }

    public static String clearNumber(String number) {
        int start = 0;
        for (int i = 0; i < number.length(); i++) {

            if (Character.isDigit(number.charAt(i))) {
                start++;
            } else if (number.charAt(i) == '.') {
                start++;
            } else if (number.charAt(i) == '#' || number.charAt(i) == '号') {
                start = 0;
                break;
            } else {
                break;
            }

        }
        return number.substring(start, number.length());
    }

    /**
     * 最大位数
     *
     * @param index
     * @param maxCount
     * @return
     */
    public static String getIndexString(int index, int maxCount) {
        if (maxCount == 3) {
            if (index < 10) {
                return "00" + index;
            } else if (index < 100) {
                return "0" + index;
            } else {
                return "" + index;
            }
        } else {
            if (index < 10) {
                return "0" + index;
            } else {
                return "" + index;
            }
        }
    }

    public static String getTaskCode(int taskType, String factoryCode, Integer taskCircle, int year, int index) {
        StringBuffer taskCode = new StringBuffer();
        taskCode.append(factoryCode);
        if (taskType == 3) {
            taskCode.append("WB");
        } else if (taskType == 4) {
            taskCode.append("FF");
        } else if (taskType == 2) {
            taskCode.append("DXCZ");
        }

        if (taskCircle != null) {
            if (taskCircle == 1) {
                taskCode.append("YD");
            }
            if (taskCircle == 2) {
                taskCode.append("JD");
            }
            if (taskCircle == 3) {
                taskCode.append("BN");
            }
            if (taskCircle == 4) {
                taskCode.append("ND");
            }
        }
        taskCode.append(year);
        taskCode.append(StringTools.getIndexString(index, 3));
        return taskCode.toString();
    }

    public static String freshOrgCode(String info) {
        //String info = "[{orgType=5, name=太平厂, roleList=[155661572763300009, 155410633624100016, 155410622795000005, 155410604662000002], value=155367199485100003}, {orgType=5, name=麒麟厂, roleList=[155661572763300009, 155410629689600012, 155410625672600008], value=155334976927400008}, {orgType=5, name=安丰营厂, roleList=[155661572763300009, 155410627896500010, 155410625672600008, 155410622795000005], value=155367208556000004}, {orgType=3, name=昆明中心, roleList=[155661572763300009, 155590424673700012, 155529270779900003, 155529214119500002, 155410644417600027, 155410643439700026, 155410639350600022, 155410637498400020], value=155334602252100004}, {orgType=5, name=安宁二污, roleList=[155684974990600036, 155684972433200035, 155661596963800013, 155661583369200011, 155661575467000010, 155661572763300009, 155529214119500002, 155417111648600033, 155417109900200032, 155417107366300031, 155410635538000018, 155410631796000014, 155410630797900013, 155410628740600011, 155410627896500010, 155410626944500009, 155410624743100007, 155410622795000005, 155410604662000002], value=155334609811000006}]";
        info = info.replace("{", "{\"");
        info = info.replace("}", "\"}");
        info = info.replace(" ", "");
        info = info.replace("=", "\":\"");
        info = info.replace(",", "\",\"");
        info = info.replace("]\"", "\"]");
        info = info.replace("\"[", "[\"");
        info = info.replace("}\"", "}");
        info = info.replace("\"{", "{");

        return info;
    }

    public static double formatDouble(double value, int digit) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(digit);
        nf.setGroupingUsed(false);
        String str = nf.format(value);
        return Double.valueOf(str);
    }

    /**
     * 从时间字符中获取日期 仅针对10:00模式的字符
     *
     * @param str
     * @return
     */
    public static int getHourFromString(String str) {
        String hourString = str.substring(0, 2);
        return Integer.parseInt(hourString);
    }

    public static String formatDoubleStr(double value, int digit) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(digit);
        nf.setGroupingUsed(false);
        return nf.format(value);
    }

    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }

    }

    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.add("08:00");
        list.add("16:00");
        list.add("00:00");
        Collections.sort(list);
        System.out.println(JSONObject.toJSONString(list));
    }

}
