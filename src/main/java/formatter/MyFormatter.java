package formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyFormatter {
    public static void main(String[] args) throws Exception{
    	System.out.println(format("11"));
    	System.out.println(format("111"));
    	System.out.println(format("1111"));
    	System.out.println(format("11111"));
    	System.out.println(format("111111"));
    }	
    
    public static String format(String str) {
        Matcher matcher = Pattern.compile("(\\d{3})").matcher(makeStr(str));
        List<String> strs =new ArrayList<String>();
        //前0を除去する
        if(matcher.find()) {
        	strs.add(Integer.parseInt(matcher.group()) + "");
        }
        while(matcher.find()) {
        	strs.add(matcher.group());
        }
        
        //,を付加するのはreduceを使ったほうがすっきりする
    	return strs
    			.stream()
    			.reduce((s,s1)-> s+","+s1)
    			.get();
    	
    }
    public static String makeStr(String str) {
    	if(str.length()%3 ==2)return "0" + str;
    	else if(str.length()%3 ==1)return "00" + str;
    	else return str;
    }

}
