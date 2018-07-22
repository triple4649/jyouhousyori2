package jpn;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.atilika.kuromoji.Tokenizer;



public class JapaneseAnalyser {
	private static final String PREFIX ="2017|SC|春|AM2";
	
    public static void main(String[] args) throws Exception{
    	//parse_1.txtにセキュリティスペシャリスト　午前2の問題用紙をOCR読み取りした結果が格納されている
        String input = Files.lines(Paths.get("work/text/2017h29h_sc_am2_qs.txt"), Charset.forName("MS932"))
        .reduce((s,v)->s+v.replaceAll("\\r\\n", "").trim()).get();
		Files.write(Paths.get("work/text/result_2017h29h_sc_am2_qs.txt"), 
				analysis(input), 
				Charset.forName("MS932"),
				StandardOpenOption.CREATE_NEW);

        analysis(input);
    }	
    public static List<String> analysis(String s)throws IOException{
    	List <String> lines= new ArrayList<String>();
    	lines.add("年度|試験区分|時期|パート|出現頻度|出現単語|文字列長");
    	lines.addAll(getResults(s));
    	return lines;
    }
    
    public static List<String> getResults(String s){
    	return new ArrayList<Entry<String,Integer>>(
    	    Tokenizer.builder()
    	    .build()
    	    .tokenize(s)
    	    .stream()
    	    .filter(a ->countOut(a.getPartOfSpeech(),a.getSurfaceForm()))
    	    .map(e -> e.getSurfaceForm())
    	    .sorted()
    	    .collect(
	    	    Collectors.groupingBy(
	    	        b->b,
	    	    	Collectors.summingInt(b->1)
	    	    )
	    	 )
	    	 .entrySet()
    	)
	    .stream()
	    .map((m)->String.format("%s|%d|%s|%d",PREFIX,m.getValue(),m.getKey(),m.getKey().length()))
	    .collect(Collectors.toList());
    }
    
    //除外条件
    public static boolean countOut(String s,String v){
    	return s.indexOf("名詞") >=0
    			&& v.length() > 1
    			&& !(v.indexOf("〟") >=0)
    			&& !(v.indexOf("轟")>=0)
    			&& !(v.indexOf("蝿")>=0)
    			&& !(v.indexOf("晒")>=0)
    			&& !(v.indexOf("羹")>=0)
    			&& !(v.indexOf("_")>=0)
       			&& !(v.indexOf("麒")>=0
    			&& !v.equals("目薬")
    	);
    }
}
