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
	private static final String PREFIX ="2017|SC|�t|AM2";
	
    public static void main(String[] args) throws Exception{
    	//parse_1.txt�ɃZ�L�����e�B�X�y�V�����X�g�@�ߑO2�̖��p����OCR�ǂݎ�肵�����ʂ��i�[����Ă���
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
    	lines.add("�N�x|�����敪|����|�p�[�g|�o���p�x|�o���P��|������");
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
    
    //���O����
    public static boolean countOut(String s,String v){
    	return s.indexOf("����") >=0
    			&& v.length() > 1
    			&& !(v.indexOf("��") >=0)
    			&& !(v.indexOf("��")>=0)
    			&& !(v.indexOf("��")>=0)
    			&& !(v.indexOf("�N")>=0)
    			&& !(v.indexOf("�")>=0)
    			&& !(v.indexOf("_")>=0)
       			&& !(v.indexOf("�i")>=0
    			&& !v.equals("�ږ�")
    	);
    }
}
