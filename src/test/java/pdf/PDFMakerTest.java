package pdf;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import jyouhousyori.xml.XMLCreater;

class PDFMakerTest {

	@Test
	void testParsePDF() throws Exception{
		String input_path="./pdf/input/2018h30h_sc_am2_qs.pdf";
		String output_path="./pdf/output/2018h30h_sc_am2_qs.txt";
		
		//�ǂݍ���PDF�t�@�C��
		PDDocument document = PDDocument.load(new File(input_path));
		//PDF�y�[�W����������
		Stream<PDPage>stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
				document.getDocumentCatalog().getPages().iterator(),
						Spliterator.ORDERED),false);
		
		System.out.println("OCR start");
		Files.write(Paths.get(output_path), 
				stream.map(s->PDFMaker.exePDFpage(s)).collect(Collectors.toList()), 
				Charset.forName("MS932"),
				StandardOpenOption.CREATE);
		System.out.println("OCR end");
		
		XMLCreater.createXMLFile("work/text/result/2018h30h_sc_am2_qs.pdf.txt",
				"./pdf/xml/2018h30h_sc_am2_qs.xml");
	}
	
	@Test
	void testCreateXML() throws Exception{
		XMLCreater.createXMLFile("work/text/result/2018h30h_sc_am2_qs.pdf.txt",
				"./pdf/xml/2018h30h_sc_am2_qs.xml");
	}

}
