package jyouhousyori.xml;


import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class XMLCreater_PM1 {
	public static void main(String args[])throws Exception{
		createXMLFile(
				"./work/text/result/2017h29a_sc_pm1_qs.pdf.txt", 
				"./work/text/xml/2017h29a_sc_pm1_qs.html",
				"./work/converter/tag_converter.txt");
	}
	public static void createXMLFile(String fromdir,String toDir,String converterPath)throws Exception{
		Files.write(Paths.get(toDir), 
				createXMLLines(fromdir,converterPath), 
				Charset.forName("UTF-8"),
				StandardOpenOption.CREATE_NEW);
	}
	
	public static List<String> createXMLLines(String inputpath,String converterPath)throws Exception{
		Map<String,String>map =getConvertStrs(converterPath);
        List<String> list = Files.lines(Paths.get(inputpath), Charset.forName("UTF-8"))
        .reduce(
        		new ArrayList<String>(),
        		(s,v)->{return makeXMLStrings(s,v,map);},
        		(s,v)->v
        );
        return list;
	}
	
	public static Map<String,String>getConvertStrs(String path) throws Exception{
		 return Files.lines(Paths.get(path), Charset.forName("UTF-8"))
		 .map(s->s.split("->"))
		 .collect(Collectors.toMap(s1->s1[0],s1->s1[1]));
	}
	
	public static ArrayList<String> makeXMLStrings(ArrayList<String> list,String target,Map<String,String>mapper){
		if(mapper.get(target)!=null) list.add(mapper.get(target));
		else if(target.indexOf("not_br")>=0)list.add(target.replaceAll("not_br", ""));
		else list.add(target +"<br>");
		return new ArrayList<String>(list);
	}
	
}
