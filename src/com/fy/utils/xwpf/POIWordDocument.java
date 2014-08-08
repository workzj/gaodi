package com.fy.utils.xwpf;

import java.io.*;
import java.util.regex.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;

public class POIWordDocument {
	
	public POIWordDocument(){
		
	}
	 
	public void InsertPicture(CTInline inline,String blipId,int id, int width, int height)
    {
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;
        //String blipId = getAllPictures().get(id).getPackageRelationship().getId();

        //CTInline inline = p.createRun().getCTR().addNewDrawing().addNewInline();

        String picXml = "" +
                "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "         <pic:nvPicPr>" +
                "            <pic:cNvPr id=\"" + id + "\" name=\"Generated\"/>" +
                "            <pic:cNvPicPr/>" +
                "         </pic:nvPicPr>" +
                "         <pic:blipFill>" +
                "            <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" +
                "            <a:stretch>" +
                "               <a:fillRect/>" +
                "            </a:stretch>" +
                "         </pic:blipFill>" +
                "         <pic:spPr>" +
                "            <a:xfrm>" +
                "               <a:off x=\"0\" y=\"0\"/>" +
                "               <a:ext cx=\"" + width + "\" cy=\"" + height + "\"/>" +
                "            </a:xfrm>" +
                "            <a:prstGeom prst=\"rect\">" +
                "               <a:avLst/>" +
                "            </a:prstGeom>" +
                "         </pic:spPr>" +
                "      </pic:pic>" +
                "   </a:graphicData>" +
                "</a:graphic>";

        //CTGraphicalObjectData graphicData = inline.addNewGraphic().addNewGraphicData();
        XmlToken xmlToken = null;
        try
        {
            xmlToken = XmlToken.Factory.parse(picXml);
        }
        catch(XmlException xe)
        {
            xe.printStackTrace();
        }
        inline.set(xmlToken);
        //graphicData.set(xmlToken);

        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);

        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);

        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("Picture " + id);
        docPr.setDescr("Generated");
    }

	public void InsertText(XWPFRun aa,String strText){
		if(strText == null || "".equals(strText))
			return;
		
		String content[] = strText.split(System.getProperty("line.separator"));
		aa.setText(content[0],0);
		for(int n=1;n<content.length;n++){
			aa.addBreak();
			aa.setText(content[n],n);
		}
	}
	
	public void ReplaceAll(String templateFile,String saveFilePath,Map<String,String> var) throws Exception {
		XWPFDocument xml  = XWPFTestDataSamples.openSampleDocument(templateFile);
		assert(xml.getDocument()!=null);
	
		//�������ж��������
		for (XWPFParagraph p : xml.getParagraphs()) {
			for (XWPFRun aa : p.getRuns()){
				String strContent = aa.getText(0);
				if(strContent == null)
					continue;
				System.out.println("XWPFRun-Text:" + aa.getText(0));

				/*
				 * 				ƥ��ģʽ
				 * 		���֣� {var}	 		������var
				 * 		ͼƬ�� {pic.jpg,w,h}  ������ͼƬ����pic.jpg ��w �ߣ�h
				 * 
				 * */

				//ͼƬ�滻
				if( strContent.matches("^\\{[^}]+,\\d*,\\d*\\}$") ){
					/*
					 * 		ע�⣺ͼƬ���滻ֻ������������һ�У�����Ƕ�뵽�������棡��
					 * 		��������Ӧ��ֻ��һ�вſ���ƥ���磺{pic.jpg,23,23}
					 * 		
					 * 		�����������Ҫ������䲼�֣������⡣
					 * */
					String[] ary = strContent.replace("{", "").replace("}", "").split(",");
					
					//ע�⣺�����·�������·�������磺"\\1\\pic.jpg"
					String imgPath = var.get(ary[0]);
					if(imgPath!=null){
						InsertText(aa," ");
					    CTInline inline = aa.getCTR().addNewDrawing().addNewInline();
						String relationId = xml.addPictureData(XWPFTestDataSamples.getImage(var.get(ary[0])),
								XWPFDocument.PICTURE_TYPE_JPEG);
						InsertPicture(inline,relationId,xml.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG),
								Integer.valueOf(ary[1]),Integer.valueOf(ary[2]));							
					}
				}else{
					//�����滻
					Matcher m1 = Pattern.compile("\\{[^}]+\\}").matcher(strContent);
					StringBuffer sbContent = new StringBuffer();
					while(m1.find()){
						String strTmp = var.get(m1.group().replace("{", "").replace("}", ""));
						if(strTmp == null){
							strTmp = "δ֪����";
						}
						m1.appendReplacement(sbContent,strTmp);
					}// end of while
					m1.appendTail(sbContent);	
					InsertText(aa,sbContent.toString());					
				}

			}// end of for (int i =0;i<p.getRuns().size();i++)
		}// end of for (XWPFParagraph p : xml.getParagraphs())
		
		//�滻���б���е����ֺ�ͼƬ��������ļ����ǲ��Ͻ��ģ�
		//���һ���������ͼƬ�Ļ������־��Զ�������ˣ�
		for( XWPFTable table : xml.getTables() ){
			for( XWPFTableRow row : table.getRows() ){
				for( XWPFTableCell cell : row.getTableCells() ){
					System.out.println("XWPFTableCell-Text:" + cell.getText());
					String strContent = cell.getText();
					
					//��ʼģʽƥ��
					Pattern pattern = Pattern.compile("\\{[^}]+\\}");
					Matcher m = pattern.matcher(strContent);
					StringBuffer sbContent = new StringBuffer("");
					boolean bInsertPic = false;
					while(m.find()){
						String[] ary = m.group().replace("{", "").replace("}", "").split(",");
						if(ary.length == 1){
							//˵������������
							String strTmp = var.get(ary[0]);
							if(strTmp == null){
								strTmp = "δ֪����";
							}
							m.appendReplacement(sbContent,strTmp);
							
						}else{
							//ͼƬ�滻
							//ע�⣺�����·�������·�������磺"\\1\\pic.jpg"
							String imgPath = var.get(ary[0]);
							if( imgPath != null){
								bInsertPic = true;
								while(cell.getParagraphs().size() > 0){
									cell.removeParagraph(0);
								}
							    CTInline inline = cell.addParagraph().insertNewRun(0).getCTR().addNewDrawing().addNewInline();
								String relationId = xml.addPictureData(XWPFTestDataSamples.getImage(imgPath),
										XWPFDocument.PICTURE_TYPE_JPEG);
								InsertPicture(inline,relationId,xml.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG),
										Integer.valueOf(ary[1]),Integer.valueOf(ary[2]));								
							}
						}
					}// end of while
					
					if(!bInsertPic){
						//������ǲ���ͼƬ�Ļ���
						if(!sbContent.toString().equals("")){
							String fontfamily = "";
							int fontsize = 12;
							while(cell.getParagraphs().size() > 0){
								XWPFParagraph tmPf = cell.getParagraphs().get(0);
								if(tmPf != null){
									XWPFRun tmRun = tmPf.getRuns().get(0);
									if(tmRun != null){
										fontfamily = tmRun.getFontFamily();
										fontsize = tmRun.getFontSize();
									}
								}
								cell.removeParagraph(0);
							}
							XWPFParagraph fp = cell.addParagraph();
							XWPFRun aa = fp.insertNewRun(0);
							m.appendTail(sbContent);	
							InsertText(aa,sbContent.toString());
							if("".equals(fontfamily)){
								aa.setFontFamily(fontfamily);
								aa.setFontSize(fontsize);//�����ֺ�ΪС��	
							}
						}
					}
					
					/*
					DocVariables ret = GetVariables(cell.getText());
					if(ret != null){
						//����MAP
						for (Entry<String, String> e : var.entrySet()) {
							if (ret.getVar().equals(e.getKey())) {
								if(ret.getnType() ==0){
									//�����滻
									cell.removeParagraph(0);
									//XWPFRun aa = cell.addParagraph().insertNewRun(0);
									//cell.setText(var.get(ret.getVar()));
									XWPFParagraph fp = cell.addParagraph();
									String var2 = var.get(ret.getVar());
									if(var2 != null){
										String str[] =  var2.split("\n");
										
										if(str.length <= 1){
											XWPFRun aa = fp.insertNewRun(0);
											aa.setText(str[0],0);
											aa.setFontSize(12);//�����ֺ�ΪС��
										}else{
											for(int i=0;i<str.length;i++){
												XWPFRun aa = fp.insertNewRun(i);
												aa.setText(str[i],0);
												aa.addBreak();
												aa.setFontSize(12);//�����ֺ�ΪС��
											}
										}
									}
									
								}else if (ret.getnType() == 1){
									//ͼƬ�滻
									//ע�⣺�����·�������·�������磺"\\1\\pic.jpg"
									cell.removeParagraph(0);
								    CTInline inline = cell.addParagraph().insertNewRun(0).getCTR().addNewDrawing().addNewInline();
									String relationId = xml.addPictureData(XWPFTestDataSamples.getImage(var.get(ret.getVar())),
											XWPFDocument.PICTURE_TYPE_JPEG);
									InsertPicture(inline,relationId,xml.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG),ret.getnWidth(),ret.getnHight());
								}
							}
						}// for (Entry<String, String> e : var.entrySet())	
					}*/

				}
			}
		}
		
		//����ļ�·��
		XWPFDocument doc = XWPFTestDataSamples.writeOutAndReadBack(xml);
		File saveFile = new File(saveFilePath);
		FileOutputStream fos = new FileOutputStream(saveFile);
		doc.write(fos);
		fos.flush();
		fos.close();
	}
	
	/*
	 public void testOpen() throws Exception {
		Map<String,String> var = new HashMap<String,String>();
		var.put("pic1", "\\pic\\pic1.jpg");
		var.put("name", "�콭");
		var.put("tel", "1589877454");
		ReplaceAll("\\doc\\sample1.docx","d:\\1.docx",var);
	}
	*/
}
