package com.fy.utils.file;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
  
import javax.faces.context.FacesContext;  
import javax.servlet.ServletContext;  
import javax.servlet.ServletOutputStream;  
import javax.servlet.http.HttpServletResponse;

public class FileUtil {

	/** 
     * �����ļ� 
     * @param path 
     * @param fileName 
     */  
    public static void downloadFile(String path, String fileName) {  
        try {  
            // ���ServletContext����  
            ServletContext servletContext = (ServletContext) FacesContext  
                    .getCurrentInstance().getExternalContext().getContext();  
            // ȡ���ļ��ľ���·��  
            String realName = servletContext.getRealPath(path);  
            HttpServletResponse response = (HttpServletResponse) FacesContext  
                    .getCurrentInstance().getExternalContext().getResponse();  
            downloadFile(response, realName, fileName);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        FacesContext.getCurrentInstance().responseComplete();  
    }
    
    /** 
     * �����ļ� 
     * @param response 
     * @param realName 
     * @param fileName 
     * @throws IOException 
     */  
    public static void downloadFile(HttpServletResponse response,  
            String realName, String fileName) throws IOException {  
        fileName = java.net.URLEncoder.encode(fileName, "UTF-8");  
        File exportFile = new File(realName);  
        response.setHeader("Content-Disposition", "attachment; filename=\""  
                + fileName + "\"");  
        response.setContentType("application/x-msdownload;charset=UTF-8");
        response.setContentLength((int) exportFile.length());
        ServletOutputStream servletOutputStream = response.getOutputStream();  
        byte[] b = new byte[1024];  
        int i = 0;  
        FileInputStream fis = new java.io.FileInputStream(exportFile);  
        while ((i = fis.read(b)) > 0) {  
            servletOutputStream.write(b, 0, i);  
        }  
        servletOutputStream.flush();  
        servletOutputStream.close();  
    }
    /** 
     * �����ļ������� 
     */  
    public void downLoadFileForLocal(String content,String fileName) {  
     try {  
      HttpServletResponse response = (HttpServletResponse) FacesContext  
        .getCurrentInstance().getExternalContext().getResponse();  
      fileName = java.net.URLEncoder.encode(fileName, "UTF-8");  
      response.setHeader("Content-Disposition", "attachment; filename=\""  
        + fileName + "\"");  
      response.setContentType("application/x-msdownload;charset=UTF-8");  
      ServletOutputStream servletOutputStream = response  
        .getOutputStream();  
      servletOutputStream.write(content.getBytes());  
      servletOutputStream.flush();  
      servletOutputStream.close();  
     } catch (IOException e) {  
      e.printStackTrace();  
     }  
     FacesContext.getCurrentInstance().responseComplete();  
    }
    
    /**   
     * ɾ���ļ��������ǵ����ļ����ļ���   
     * @param   fileName    ��ɾ�����ļ���   
     * @return �ļ�ɾ���ɹ�����true,���򷵻�false   
     */    
    public static boolean delete(String fileName){     
        File file = new File(fileName);     
        if(!file.exists()){     
            System.out.println("ɾ���ļ�ʧ�ܣ�"+fileName+"�ļ�������");     
            return false;     
        }else{     
            if(file.isFile()){     
                     
                return deleteFile(fileName);     
            }else{     
                return deleteDirectory(fileName);     
            }     
        }     
    }
    
    /**   
     * ɾ�������ļ�   
     * @param   fileName    ��ɾ���ļ����ļ���   
     * @return �����ļ�ɾ���ɹ�����true,���򷵻�false   
     */    
    public static boolean deleteFile(String fileName){     
        File file = new File(fileName);     
        if(file.isFile() && file.exists()){     
            file.delete();     
            System.out.println("ɾ�������ļ�"+fileName+"�ɹ���");     
            return true;     
        }else{     
            System.out.println("ɾ�������ļ�"+fileName+"ʧ�ܣ�");     
            return false;     
        }     
    }
    
    /**   
     * ɾ��Ŀ¼���ļ��У��Լ�Ŀ¼�µ��ļ�   
     * @param   dir ��ɾ��Ŀ¼���ļ�·��   
     * @return  Ŀ¼ɾ���ɹ�����true,���򷵻�false   
     */    
    public static boolean deleteDirectory(String dir){     
        //���dir�����ļ��ָ�����β���Զ�����ļ��ָ���     
        if(!dir.endsWith(File.separator)){     
            dir = dir+File.separator;     
        }     
        File dirFile = new File(dir);     
        //���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�     
        if(!dirFile.exists() || !dirFile.isDirectory()){     
            System.out.println("ɾ��Ŀ¼ʧ��"+dir+"Ŀ¼�����ڣ�");     
            return false;     
        }     
        boolean flag = true;     
        //ɾ���ļ����µ������ļ�(������Ŀ¼)     
        File[] files = dirFile.listFiles();     
        for(int i=0;i<files.length;i++){     
            //ɾ�����ļ�     
            if(files[i].isFile()){     
                flag = deleteFile(files[i].getAbsolutePath());     
                if(!flag){     
                    break;     
                }     
            }     
            //ɾ����Ŀ¼     
            else{     
                flag = deleteDirectory(files[i].getAbsolutePath());     
                if(!flag){     
                    break;     
                }     
            }     
        }     
             
        if(!flag){     
            System.out.println("ɾ��Ŀ¼ʧ��");     
            return false;     
        }     
             
        //ɾ����ǰĿ¼     
        if(dirFile.delete()){     
            System.out.println("ɾ��Ŀ¼"+dir+"�ɹ���");     
            return true;     
        }else{     
            System.out.println("ɾ��Ŀ¼"+dir+"ʧ�ܣ�");     
            return false;     
        }     
    }
    
    public static void renameFile(String file, String toFile) {

        File toBeRenamed = new File(file);

        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            System.out.println("File does not exist: " + file);
            return;
        }

        File newFile = new File(toFile);

        //Rename
        if (toBeRenamed.renameTo(newFile)) {
            System.out.println("File has been renamed.");
        } else {
            System.out.println("Error renmaing file");
        }
    }
	
}
