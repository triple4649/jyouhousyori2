package jyouhousyori.xml;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;

public class XMLReader {
	private static final String WORDS="- %s  [https://www.google.co.jp/"
			+ "search?q=%s](https://www.google.co.jp/search?q=%s)";
	//�R�}���h���C���̑�����:�ǂݍ��݂���xml�̃p�X���w�肷��
	public static void main(String args[]){
		getDictionarySource(args[0]).stream()
		.map(s->String.format(WORDS, s,s,s))
		.forEach(System.out::println);
	}
	
	public static List<String> getDictionarySource(String str){
		try{
			return Jsoup.parse(new File(str),"UTF-8")
					//�^�O search�ɑ�����q�v�f�����ׂĎ擾����
					.getElementsByTag("search") 
					.stream()
					//�^�O search�ɑ�����q�v�f�̃e�L�X�g���擾����
					//getAllElements().eachText()�͎q�G�������g��
					//�e�L�X�g��List<String>�`���ŕێ����Ă���̂ŁAreduce��
					//�������A������
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
					//�^�O search�ɑ�����q�v�f�����ׂĎ擾����
					.getElementsByTag("search") 
					.stream()
					//�^�O search�ɑ�����q�v�f�̃e�L�X�g���擾����
					//getAllElements().eachText()�͎q�G�������g��
					//�e�L�X�g��List<String>�`���ŕێ����Ă���̂ŁAreduce��
					//�������A������
					.map(s->s.text())
					.collect(Collectors.toList());
		}catch(Exception e){
			e.printStackTrace();
			return new ArrayList<String>();
		}
    }
	

}
