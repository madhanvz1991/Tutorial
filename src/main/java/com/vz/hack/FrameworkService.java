package framework;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class FrameworkService {

	private  List<String> fileList = new ArrayList<String>();
	private static List<String> newTestFileList = new ArrayList<String>();
	private static Map<String, ArrayList<String>> testClassTemplate = new HashMap<String, ArrayList<String>>();
	

	/*public static void main(String[] args) throws FileNotFoundException,
			DocumentException {
		listSourceFile("D://MyEclipseWS//MockTool//src//main//java");

		for (int i = 0; i < fileList.size(); i++) {
			System.out.println(fileList.get(i));
			String temp = fileList.get(i);
			String removeMain = temp.replaceAll("main", "test");

			testDirList.add(removeMain.substring(0, removeMain.length() - 5)
					.concat("Test.java"));
		}
		createTestDirectory(testDirList);
		// ////createDocument(newTestFileList);
		Set setOfKeys = testClassTemplate.keySet();
		Iterator iterator = setOfKeys.iterator();
		while (iterator.hasNext()) {
		System.out.println("***********Template Info********************");
			String key = (String) iterator.next();
			System.out.println("Key"+key);
			ArrayList<String> value = testClassTemplate.get(key);
			for (int i = 0; i < value.size(); i++) {
				System.out.println(value.get(i));
			}
			
		System.out.println("***********Template Info********************");
		}

	}*/

	

	public List<ClassDetailVO> createTestDirectory(List<String> testDirList, String testFailFlag) {
		List<ClassDetailVO> detailVOList=new ArrayList<ClassDetailVO>();
		ClassDetailVO detailVO=null;
		
		TestCasesTemplate template = new TestCasesTemplate();
		
		for (int i = 0; i < testDirList.size(); i++) {
			String testFileName = StringEscapeUtils.escapeJava((testDirList
					.get(i)));
			String testDirName = testFileName.substring(0,
					testFileName.lastIndexOf('\\') - 1);
			System.out.println("fileName----------------:" + testFileName);
			System.out.println("dirName:" + testDirName);

			
			String packageName = testDirName.split("java")[1] ;
			//System.out.println( "for package names: " + packageName );
			packageName = packageName.replaceFirst("\\\\", "");
			//System.out.println("packageName: " + packageName); 
		//	packageName = packageName.replaceFirst("\\\\", "") ;
			packageName = packageName.replaceAll("\\\\", "jj") ;
			//System.out.println("packageName: " + packageName);
			packageName = packageName.replaceAll("jjjj", ".") ;
			//System.out.println("packageName: " + packageName);
			packageName = packageName.replaceAll("jj", "") ;
			//System.out.println("packageName: " + packageName);

			
			File dirObj = new File(testDirName);
			File fileObj = new File(testFileName);

			if (!dirObj.exists()) {
				System.out.println("Directory Doesnt exists:creating directory");
				dirObj.mkdir();
			}
			if (!fileObj.exists()) {
				System.out.println("File Doesnt exists:creating File");
				try {

					// FileOutputStream is = new FileOutputStream(fileObj);
					
					
					newTestFileList.add(testFileName);
					/*
					 * for every new test file that we in test directory, get
					 * class information to the corresponding src file
					 */
					System.out.println("*****************************************");
					System.out.println("testfileName" + testFileName);
					String srcFileName = testFileName
							.replaceAll("test", "main").replaceAll("Test.java",
									".java");
					System.out.println(srcFileName);
					System.out.println("*****************************************");
					
					Map<String, ArrayList<String>> methodList = getClassDetails(srcFileName); 
       				//testClassTemplate.put(testFileName,(ArrayList<String>) methods);
       				
       				detailVO = new ClassDetailVO();
       				detailVO.setMethodList(methodList);
       				detailVO.setSrcFileName(srcFileName);
       				detailVO.setTestFileName(testFileName);
       				// detailVOList.add(detailVO);
       				
       				try {
       	    			CompilationUnit cu = template.createTestTemplate(detailVO, packageName, testFailFlag);
       	    			StringBuffer fileBuf = new StringBuffer(cu .toString() );
       	    			String fileOutText = fileBuf.toString() ;
       	    			fileOutText = fileOutText.replace("//@Test", "@Test") ;
       	    			PrintStream out = new PrintStream(new FileOutputStream(fileObj) );
       	    			    out.print(fileOutText);
       				} catch (Exception e) {
       					e.printStackTrace();
       				}
       				
				} catch (Exception e) {
					System.err
							.println("Problem writing to the file statsTest.txt");
				}
			}
		}
      return detailVOList;
	}

	private  Map<String, ArrayList<String>> getClassDetails(String fileName) {
		System.out.println("Inside getClassDetails..");
		Map<String, ArrayList<String>> methodList = new HashMap<String, ArrayList<String>>();
		try {
			FileInputStream in = new FileInputStream(fileName);

			com.github.javaparser.ast.CompilationUnit cu;
			try {
				// parse the file
				cu = com.github.javaparser.JavaParser.parse(in);
				List<TypeDeclaration> types = cu.getTypes();
				for (TypeDeclaration type : types) {
					List<BodyDeclaration> members = type.getMembers();
					for (BodyDeclaration member : members) {
						if (member instanceof MethodDeclaration) {
							MethodDeclaration method = (MethodDeclaration) member;
							System.out.println("Method Description:"
									+ method.getDeclarationAsString());
							String methodName=method.getDeclarationAsString();
							
							System.out.println(method.getName() + ")))))))");
							List<Parameter> methodParam=method.getParameters();
							ArrayList<String> methodParamList=new ArrayList<String>();
							
	                           for (int i = 0; i < methodParam.size(); i++) {
	                        	   String paramName=methodParam.get(i).toString();
	                        	   System.out.println("Printing Method Arguments...."+paramName);
	                        	   System.out.println("i value---"+i);
	                        	   methodParamList.add(paramName);

	                           } 
							
	                           
                          // methodList.put(methodName, methodParamList); 
	                           methodList.put(methodName,  methodParamList);
						}
					}
				}
			} finally {
				in.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return methodList;
	} 
	
	
	public  void createDocument(List<String> newTestFileList)
			throws FileNotFoundException, DocumentException {
		Document document = new Document(PageSize.A4, 50, 50, 50, 150);
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(
						"H://17thJuly_DEV2_WS//MockTool//target//NewTestFile.pdf"));
		document.open();

		Paragraph title1 = new Paragraph("", FontFactory.getFont(
				FontFactory.HELVETICA, 18, Font.BOLDITALIC, new CMYKColor(0,
						255, 255, 17)));

		Chapter chapter1 = new Chapter(title1, 1);
		chapter1.setNumberDepth(0);

		Paragraph title11 = new Paragraph(
				"Please Find The List Of New Test Files Created",
				FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD,
						new CMYKColor(0, 255, 255, 17)));

		Section section1 = chapter1.addSection(title11);
		Paragraph someSectionText = null;

		PdfPTable t = new PdfPTable(2);
		t.setSpacingBefore(25);
		t.setSpacingAfter(25);
		PdfPCell c1 = new PdfPCell(new Phrase("S.No"));
		t.addCell(c1);
		PdfPCell c2 = new PdfPCell(new Phrase("File Name"));
		t.addCell(c2);
		t.setWidths(new int[] { 15, 80 });

		for (Integer i = 0; i < newTestFileList.size(); i++) {
			Integer temp = i + 1;
			t.addCell(temp.toString());
			t.addCell(newTestFileList.get(i));
		}
		section1.add(t);
		document.add(chapter1);
		document.close();
	}

}
