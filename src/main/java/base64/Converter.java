package base64;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Converter {
	
	public static void main(String args[])throws Exception{
		System.out.println(converToBase64("./work/img/img.png"));
	}

	public static String converToBase64(String path)throws Exception{
		return Base64.getEncoder()
				.encodeToString(
						Files.readAllBytes(Paths.get(path)));
	}
	public static void converBase64ToFile(String src,String dist)throws Exception{
		Files.write(Paths.get(dist), 
				Base64.getDecoder().decode(src), 
				StandardOpenOption.CREATE_NEW);
	}
	
	public static void converter() throws Exception{
		List<String>list = toStringFromExam(
				Files.readAllLines(Paths.get("work/text/2017h29h_sc_am2_qs.txt"),
				Charset.forName("MS932"))
				.stream()
		);
		Files.write(Paths.get("work/text/2017h29h_sc_am2_qs_new.txt"), 
				list,
				Charset.forName("MS932"),
				StandardOpenOption.CREATE_NEW,
				StandardOpenOption.TRUNCATE_EXISTING
		);
	}
	
	public static List<String> toStringFromExam(Stream<String> of){
		return of.map(s->converTostr(s))
				.collect(Collectors.toList());
	}
	
	public static String converTostr(String s){
		StringBuilder bulider = new StringBuilder();
		if(s.matches("^��.*"))  bulider.append("+ ").append(s);
		else if(s.matches("^�A.*"))  bulider.append("#�A ").append(s);
		else if(s.matches("^�C.*"))  bulider.append("#�C ").append(s);
		else if(s.matches("^�E.*"))  bulider.append("#�E ").append(s);
		else if(s.matches("^�G.*"))  bulider.append("#�G ").append(s);
		else                      bulider.append(s);
		return bulider.toString();
	}

}
