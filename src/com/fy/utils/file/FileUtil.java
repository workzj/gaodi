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
     * 下载文件 
     * @param path 
     * @param fileName 
     */  
    public static void downloadFile(String path, String fileName) {  
        try {  
            // 获得ServletContext对象  
            ServletContext servletContext = (ServletContext) FacesContext  
                    .getCurrentInstance().getExternalContext().getContext();  
            // 取得文件的绝对路径  
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
     * 下载文件 
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
     * 下载文件到本地 
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
     * 删除文件，可以是单个文件或文件夹   
     * @param   fileName    待删除的文件名   
     * @return 文件删除成功返回true,否则返回false   
     */    
    public static boolean delete(String fileName){     
        File file = new File(fileName);     
        if(!file.exists()){     
            System.out.println("删除文件失败："+fileName+"文件不存在");     
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
     * 删除单个文件   
     * @param   fileName    被删除文件的文件名   
     * @return 单个文件删除成功返回true,否则返回false   
     */    
    public static boolean deleteFile(String fileName){     
        File file = new File(fileName);     
        if(file.isFile() && file.exists()){     
            file.delete();     
            System.out.println("删除单个文件"+fileName+"成功！");     
            return true;     
        }else{     
            System.out.println("删除单个文件"+fileName+"失败！");     
            return false;     
        }     
    }
    
    /**   
     * 删除目录（文件夹）以及目录下的文件   
     * @param   dir 被删除目录的文件路径   
     * @return  目录删除成功返回true,否则返回false   
     */    
    public static boolean deleteDirectory(String dir){     
        //如果dir不以文件分隔符结尾，自动添加文件分隔符     
        if(!dir.endsWith(File.separator)){     
            dir = dir+File.separator;     
        }     
        File dirFile = new File(dir);     
        //如果dir对应的文件不存在，或者不是一个目录，则退出     
        if(!dirFile.exists() || !dirFile.isDirectory()){     
            System.out.println("删除目录失败"+dir+"目录不存在！");     
            return false;     
        }     
        boolean flag = true;     
        //删除文件夹下的所有文件(包括子目录)     
        File[] files = dirFile.listFiles();     
        for(int i=0;i<files.length;i++){     
            //删除子文件     
            if(files[i].isFile()){     
                flag = deleteFile(files[i].getAbsolutePath());     
                if(!flag){     
                    break;     
                }     
            }     
            //删除子目录     
            else{     
                flag = deleteDirectory(files[i].getAbsolutePath());     
                if(!flag){     
                    break;     
                }     
            }     
        }     
             
        if(!flag){     
            System.out.println("删除目录失败");     
            return false;     
        }     
             
        //删除当前目录     
        if(dirFile.delete()){     
            System.out.println("删除目录"+dir+"成功！");     
            return true;     
        }else{     
            System.out.println("删除目录"+dir+"失败！");     
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
