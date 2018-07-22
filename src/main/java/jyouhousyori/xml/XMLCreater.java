package jyouhousyori.xml;


import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XMLCreater {
	private static final String SELECTION_REG = "^#(ア|イ|ウ|エ)";
	private static final String QUETION_REG = "～";
	private static final String SELECTION_TAG ="<selection option='%s'> %s </selection>";
	private static final String QUETION_TAG ="<question num='%s'> %s ";
	private static final String REPLACE_STR = "./jyohousyori/pdf/replace.txt";

	public static void main(String args[])throws Exception{
	}
	
	
	public static void createXMLFile(String fromdir,String toDir)throws Exception{
		Files.write(Paths.get(toDir), 
				createXMLLines(fromdir), 
				Charset.forName("UTF-8"),
				StandardOpenOption.CREATE);
	}
	
	public static List<String> createXMLLines(String path)throws Exception{
        List<String> list = Files.lines(Paths.get(path), Charset.forName("MS932"))
        .map(f->mapStrs(f))
        .reduce(
        		new ArrayList<String>(),
        		(s,v)->{return makeXMLStrings(s,v);},
        		(s,v)->v
        );
        list.add(createQuetionCloseTag());
        return list;
	}
	
	public static String mapStrs(String target) {
		try {
			return Files.lines(Paths.get(REPLACE_STR), Charset.forName("MS932"))
					.reduce(target.replaceAll(" ", ""	),
							(s,v)->s.replace(v.split(",")[0],v.split(",")[1]),
			        		(s,v)->v);
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public static ArrayList<String> makeXMLStrings(ArrayList<String> list,String target){
		if(target.indexOf(QUETION_REG)>=0)list.add(createQuetionTag(target,list.size()));
		else if(target.matches(SELECTION_REG+".*")) list.add(createSelectionTag(target));
		else list.add(target);
		
		return new ArrayList<String>(list);
	}
	
	public static String createQuetionTag(String str,int index){
		String[] strs = str.split(QUETION_REG);
		String questionnum = getFirstMatched(".*問(\\d+)",str);
		return (index != 0 ? createQuetionCloseTag():"")
				+System.lineSeparator() 
				+System.lineSeparator() 
				+String.format(QUETION_TAG, questionnum,strs[1].replaceFirst(QUETION_REG, ""));
	}
	public static String createQuetionCloseTag(){
		return "</question>";
	}
	public static String createSelectionTag(String str){
		String[] strs = str.split(SELECTION_REG);
		return String.format(SELECTION_TAG, 
				getFirstMatched(SELECTION_REG,str),
				strs[1]);
	}
	
	public static String getFirstMatched(String reg,String str){
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		if(m.find()){
			return m.group(1);
		}
		return "";
	}
}
