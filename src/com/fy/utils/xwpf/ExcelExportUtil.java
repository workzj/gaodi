package com.fy.utils.xwpf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelExportUtil {
	private static String sheetName = "data";  
	private HSSFWorkbook wb;  
	private HSSFSheet sheet;  
	private HSSFRow row;  
	private HSSFCell cell;  
	private HSSFFont font;  
	private HSSFCellStyle cellStyle;  
	private FileOutputStream fileOut;  
	
	public ExcelExportUtil() {  
		wb = new HSSFWorkbook();  
	}  
	/**  
	* @param excelName  
	* excel名称。  
	* @param list  
	* 这个list里面存放的是对象数组。数组元素可以转化为字符串显示的。这个对象数组一般对应数据库里的几列。  
	* @param firstRowValue  
	*/  
	public  void outputExcel(String excelName, List list, String[] firstRowValue) {  
		try {  
			this.createSheet(firstRowValue);  
			this.setValueToRow(excelName, list);  
		} catch (Exception ex) {  
			System.out.print(ex);  
		}  
		// System.out.println("文件名是:" + excelName);  
		downloadFile(excelName);  
	}  
	public void outputExcel(String excelName, List list) {  
		try {  
			this.setValueToRow(excelName, list);  
		} catch (Exception e) {  
			// TODO: handle exception  
		}  
		downloadFile(excelName);  
	}  
	private  void setValueToRow(String excelName, List list) {  
		// 获得JSF上下文环境  
		FacesContext context = FacesContext.getCurrentInstance();  
		// 获得ServletContext对象  
		ServletContext servletContext = (ServletContext) context  
		.getExternalContext().getContext();  
		// 取得文件的绝对路径  
		File file = new File(servletContext.getRealPath("/UploadFile"));
		  //判断文件夹是否存在,如果不存在则创建文件夹
		  if (!file.exists()) {
		   file.mkdir();
		  }
		excelName = servletContext.getRealPath("/UploadFile") + "/" + excelName;   
		Object[] obj;  
		try {  
			for (int i = 0; i < list.size(); i++) {  
				row = sheet.createRow(i + 1);  
				obj = (Object[]) list.get(i);  
				this.createCell(row, obj);  
			}  
			fileOut = new FileOutputStream(excelName);  
			wb.write(fileOut);  
		} catch (Exception ex) {  
			System.out.print("生成报表有误:" + ex);  
		} finally {  
			try {  
				fileOut.flush();  
				fileOut.close();  
			} catch (Exception e) {  
				System.out.println("ExcelUtil.setValueToRow()");  
			}  
		}  
	}  
	private void createSheet(String[] firstRowValue) {  
		try {  
			sheet = wb.createSheet(ExcelExportUtil.sheetName);  
			row = sheet.createRow(0);  
			font = wb.createFont();  
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
			cellStyle = wb.createCellStyle();  
			cellStyle.setFont(font);  
			for (int i = 0; i < firstRowValue.length; i++) {  
				cell = row.createCell((short) i);  
				cell.setCellStyle(cellStyle);  
				cell.setCellValue(firstRowValue[i]);  
			}  
		} catch (Exception ex) {  
			System.out.print(ex);  
		}  
	}  
	private void createCell(HSSFRow row, Object[] obj) {  
		try {  
			for (int i = 0; i < obj.length; i++) {  
				cell = row.createCell((short) i);    
				cell.setCellValue(obj[i].toString());  
			}  
		} catch (Exception ex) {  
			System.out.print(ex);  
		}  
	}  
	/**  
	*   
	* 功能说明：根据提供的文件名下载文件,不支持中文文件名  
	*   
	* 此方法由yongtree添加,实现文件生成后的下载  
	*  
	* @param strfileName  
	* String  
	* @return void  
	*/  
	private static void downloadFile(String strfileName) {  
		try {  
			// 获得JSF上下文环境  
			FacesContext context = FacesContext.getCurrentInstance();  
			// 获得ServletContext对象  
			ServletContext servletContext = (ServletContext)context.getExternalContext().getContext();  
			// 取得文件的绝对路径  
			String excelName = servletContext.getRealPath("/UploadFile") + "/"  
			+ strfileName;  
			File exportFile = new File(excelName);  
			HttpServletResponse httpServletResponse = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();  
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + strfileName);  
			httpServletResponse.setContentLength((int) exportFile.length());  
			httpServletResponse.setContentType("application/x-download");  
			// httpServletResponse.setContentType("application/vnd.ms-excel");  
			byte[] b = new byte[1024];  
			int i = 0;  
			FileInputStream fis = new java.io.FileInputStream(exportFile);  
			while ((i = fis.read(b)) > 0) {  
				servletOutputStream.write(b, 0, i);  
			}  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		FacesContext.getCurrentInstance().responseComplete();  
	}  
	
	public static String getDateTime() {
		Calendar cal = Calendar.getInstance();   
		SimpleDateFormat   formatter   =   new   SimpleDateFormat("yyyyMMddHHmmss");   
	    String mDateTime = formatter.format(cal.getTime());
	  
	   return mDateTime;
	}
}
