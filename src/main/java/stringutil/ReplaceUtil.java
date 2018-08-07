package stringutil;
import java.util.stream.Stream;

public class ReplaceUtil {
	
	public static String replacewhiteSpace(String target,String separator,String marker) {
		return Stream.of(target.split(separator))
		.map(s->replacer(s,marker))
		.reduce((s1,s2)->s1+s2 )
		.orElse("");
	}
	
	private static  String replacer(String target,String marker) {
		if(target.matches(String.format("%s[\\w| ].*%s", marker,marker))) {
			return target.replaceAll(String.format("%s([\\w| ].*)%s", marker,marker), "$1");
		}else {
			return target.replaceAll(" ", "");
		}
	}

}
