package com.cy.libreoffice;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class LibreofficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibreofficeApplication.class, args);
	}
	/***
	 * PDF文件转PNG图片，全部页数
	 *
	 * @param PdfFilePath pdf完整路径
	 * @param dstImgFolder 图片存放的文件夹
	 * @param dpi dpi越大转换后越清晰，相对转换速度越慢
	 * @return
	 */
	public static void pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
		File file = new File(PdfFilePath);
		PDDocument pdDocument;
		try {
			String imgPDFPath = file.getParent();
			int dot = file.getName().lastIndexOf('.');
			String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
			String imgFolderPath = null;
			if (dstImgFolder.equals("")) {
				imgFolderPath = imgPDFPath + File.separator + imagePDFName;// 获取图片存放的文件夹路径
			} else {
				imgFolderPath = dstImgFolder + File.separator + imagePDFName;
			}
			if (createDirectory(imgFolderPath)) {

				pdDocument = PDDocument.load(file);
				PDFRenderer renderer = new PDFRenderer(pdDocument);
				/* dpi越大转换后越清晰，相对转换速度越慢 */
				int pages = pdDocument.getNumberOfPages();
				StringBuffer imgFilePath = null;
				for (int i = 0; i < pages; i++) {
					String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
					imgFilePath = new StringBuffer();
					imgFilePath.append(imgFilePathPrefix);
					imgFilePath.append("_");
					imgFilePath.append(String.valueOf(i + 1));
					imgFilePath.append(".png");
					File dstFile = new File(imgFilePath.toString());
					BufferedImage image = renderer.renderImageWithDPI(i, dpi);
					ImageIO.write(image, "png", dstFile);
				}
				System.out.println("PDF文档转PNG图片成功！");

			} else {
				System.out.println("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static boolean createDirectory(String folder) {
		File dir = new File(folder);
		if (dir.exists()) {
			return true;
		} else {
			return dir.mkdirs();
		}
	}
}
