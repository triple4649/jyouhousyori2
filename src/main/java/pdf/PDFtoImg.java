package pdf;

import java.awt.image.BufferedImage;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class PDFtoImg {
	private static final String  DICTIONARY_PATH ="./jyohousyori/tessdata";
    public static String extractFromPDF(BufferedImage img) {
		ITesseract instance = new Tesseract();
		try {
		    instance.setLanguage("jpn");
		    instance.setDatapath(DICTIONARY_PATH);
		    System.out.println("ORCスタート");
		    String result = instance.doOCR(img);
		    System.out.println("終了");
		    return result;
		} catch (Throwable ex) {
		    ex.printStackTrace();
		    System.out.println("error!");
		    return "";
		}
    }
}
