package jyouhousyori.xml;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;

public class XMLReader {
	private static final String WORDS="- %s  [https://www.google.co.jp/"
			+ "search?q=%s](https://www.google.co.jp/search?q=%s)";
	//コマンドラインの第一引数:読み込みたいxmlのパスを指定する
	public static void main(String args[]){
		getDictionarySource(args[0]).stream()
		.map(s->String.format(WORDS, s,s,s))
		.forEach(System.out::println);
	}
	
	public static List<String> getDictionarySource(String str){
		try{
			return Jsoup.parse(new File(str),"UTF-8")
					//タグ searchに属する子要素をすべて取得する
					.getElementsByTag("search") 
					.stream()
					//タグ searchに属する子要素のテキストを取得する
					//getAllElements().eachText()は子エレメントの
					//テキストをList<String>形式で保持しているので、reduceで
					//文字列を連結する
					.map(s->s.getAllElements().text()
)
					.collect(Collectors.toList());
		}catch(Exception e){
			e.printStackTrace();
			return new ArrayList<String>();
		}
    }
	
	public static List<String> getXmlElements(String str){
		try{
			return Jsoup.parse(new File(str),"UTF-8")
					//タグ searchに属する子要素をすべて取得する
					.getElementsByTag("search") 
					.stream()
					//タグ searchに属する子要素のテキストを取得する
					//getAllElements().eachText()は子エレメントの
					//テキストをList<String>形式で保持しているので、reduceで
					//文字列を連結する
					.map(s->s.text())
					.collect(Collectors.toList());
		}catch(Exception e){
			e.printStackTrace();
			return new ArrayList<String>();
		}
    }
	

}
