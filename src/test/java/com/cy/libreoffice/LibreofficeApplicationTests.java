package com.cy.libreoffice;

import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibreofficeApplicationTests {
	@Autowired
	private DocumentConverter documentConverter;
	@Test
	public void contextLoads() {
	}
	@Test
	public void pptTopdf(){
		String sourceFile = "F://business-ppt-template-001.pptx";
		String outputFilePath = "F://pdf1/1231.pdf";
		try {
			documentConverter.convert(new File(sourceFile)).to(new File(outputFilePath)).execute();
		} catch (OfficeException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void pptTohtml(){
		String sourceFile = "F://business-ppt-template-001.pptx";
		String outputFilePath = "F://pdf1/pptx.html";
		try {
			documentConverter.convert(new File(sourceFile)).to(new File(outputFilePath)).execute();
		} catch (OfficeException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void pptToimage(){
		String sourceFile = "F://business-ppt-template-001.pptx";
		String outputFilePath = "F://pdf1/pptx.pdf";
//        OpenOfficeConvertToPDF.office2PDF(filePath, outputFilePath);
//        pdf2Image("F://pdf/1231.pdf", "F://image", 300);
		try {
            documentConverter.convert(new File(sourceFile)).to(new File(outputFilePath)).execute();
            LibreofficeApplication.pdf2Image(outputFilePath,"F://libreimage",300);
		} catch (OfficeException e) {
			e.printStackTrace();
		}
	}
}
