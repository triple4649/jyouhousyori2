package pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFMaker {
	private static String  filename ="2020r02o_pm_pm2_qs.pdf";
	private static String input_path="./pdf/input/pm/pm2/%s";
	private static String output_parent_path="./pdf/output/pm/pm2/%s";
	private static String output_path=output_parent_path+".%s";
	
	private static ThreadLocal<Integer> THREAD_LOCAL = new ThreadLocal<Integer>();
	
	public static void main(String args[])throws Exception{
		//読み込むPDFファイル
		PDDocument document = PDDocument.load(new File(String.format(input_path,filename)));
		//PDFページを処理する
		Stream<PDPage>stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
				document.getDocumentCatalog().getPages().iterator(),
						Spliterator.ORDERED),false);
		
		System.out.println("start");
		Files.write(Paths.get(String.format(output_path,filename,"html")), 
				stream.map(s->PDFMaker.exePDFpage(s))
				.collect(Collectors.toList()), 
				Charset.forName("UTF-8"),
				StandardOpenOption.CREATE);
	}
	
	//PDFのPageを処理する
	public static String exePDFpage(PDPage p){
		Stream<COSName>stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
				p.getResources().getXObjectNames().iterator(),Spliterator.ORDERED),false);
		return stream.map(s->changePDFtoImg(s,p.getResources()))
		.map(s->s.replaceAll("[\r|\r\n]", "<br>"))		
		.reduce((s,v)->s+v).get();
	}
	
	//PDFのPageをJpgに変換する
	public static String changePDFtoImg(COSName n,PDResources resources){
		try{
			PDXObject xobject = resources.getXObject(n);
			if(xobject instanceof PDImageXObject){
				PDImageXObject image2 = (PDImageXObject) resources.getXObject(n);
				//PDFの中にあるイメージをJPEG形式で保存する
				getImgFromPDF(image2.getImage());
				//イメージを文字列OCRする
				return PDFtoImg.extractFromPDF(image2.getImage());
			}
			return "";
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}	
	}
	public static void getImgFromPDF(BufferedImage img ) {
		try {
			Integer index = THREAD_LOCAL.get();
			if(index == null) {
				index = 0;
			}
			index +=1;
			THREAD_LOCAL.set(index);
			String parent_path = String.format(output_parent_path, "img/"+filename);
			Files.createDirectories(Paths.get(parent_path));
			String path = String.format(output_path, "img/"+filename+"/img"+index,"jpeg");
			System.out.println(path);
			ImageIO.write(img, "jpeg", new File(path));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
