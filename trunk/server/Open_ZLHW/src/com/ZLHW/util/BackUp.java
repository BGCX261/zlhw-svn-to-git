package com.ZLHW.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class BackUp {

    public static void main(String args[]) {
//    	totalBackUp(new Date());
//      incrementalBackUp();\\
//    	String str = ;
//    	System.out.println(str);
    }

    //增量备份
    public static void incrementalBackUp(String incrementalBackUpPath) {
    	// 源文件夹
        String url1 = BackUp.class.getClassLoader().getResource("../../").getPath() + "images";
        // 目标文件夹
        String url3 = "";
        String backUpPath = incrementalBackUpPath;
        
    	url3 = backUpPath + "\\" + "图片文件增量备份";
        File[] oldFiles = new File(url3).listFiles();
        
        // 目录下没有文件夹，创建文件
        if (oldFiles==null){
            (new File(url3)).mkdirs();
        }else if(oldFiles.length == 0) {
            (new File(url3)).mkdirs();
        } else {
            // 临时变量，判断是否要新建文件夹
            boolean temp = true;
            for (File oldFile : oldFiles) {
                if (oldFile.getName().equals("图片文件增量备份")) {
                    temp = false;
                }
            }
            if (temp) {
                (new File(url3)).mkdirs();
            }
        }
        // 获取源文件夹下的文件或目录
        File[] file = (new File(url1)).listFiles();
        for (int i = 0; i < file.length; i++) {
            // 过滤.svn的目录
            if (file[i].getName().endsWith(".svn")) {
                continue;
            }
            if (file[i].isDirectory()) {
                // 复制img和 map目录下的
                if ("img".equals(file[i].getName()) || "map".equals(file[i].getName())) {
                    String sourceDir = url1 + File.separator + file[i].getName();
                    String targetDir = url3 + File.separator + file[i].getName();
                    try {
                        copyDirectory(sourceDir, targetDir, 2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // 日志写入txt文档
        writeTxt(backUpPath);
    }
    
    //增量备份日志写入txt文档
    public static void writeTxt(String url3){
    	String txtUrl = url3 + "\\图片文件增量备份日志.txt";
    	Date currentTime = new Date();   
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
    	String dateString = formatter.format(currentTime);   
    	try {
    		// 如果图片文件增量备份日志.txt不存在，新建txt文件
    		File file = new File(txtUrl);
    		
    		// 写入图片文件增量备份日志.txt
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		    writer.write(dateString + " 图片文件增量备份");
		    writer.newLine();
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    //全备份
    public static void totalBackUp(Date date, String totalBackUpPath) {
    	// 源文件夹
        String url1 = BackUp.class.getClassLoader().getResource("../../").getPath() + "images";
        // 目标文件夹
    	String url2 = totalBackUpPath;
    	
        // 创建目标文件夹
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH点mm分ss秒");
        String str = format.format(date);
        url2 = url2 + "\\" + "图片文件全备份-" + str;
        (new File(url2)).mkdirs();

        // 获取源文件夹下的文件或目录
        File[] file = (new File(url1)).listFiles();
        for (int i = 0; i < file.length; i++) {
            // 过滤.svn的目录
            if (file[i].getName().endsWith(".svn")) {
                continue;
            }
            if (file[i].isDirectory()) {
                // 复制img和 map目录下的
                if ("img".equals(file[i].getName()) || "map".equals(file[i].getName())) {
                    String sourceDir = url1 + File.separator + file[i].getName();
                    String targetDir = url2 + File.separator + file[i].getName();
                    try {
                        copyDirectory(sourceDir, targetDir, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {

        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();

        // 关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }

    // 复制文件夹
    public static void copyDirectory(String sourceDir, String targetDir, int type)
                                                                                  throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].getName().endsWith(".svn")) {
                continue;
            }
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // img文件夹下必须为bmp文件才能备份
                if ("img".equals(sourceFile.getParentFile().getName())
                    && !sourceFile.getName().endsWith(".bmp")) {
                    continue;
                }
                // map文件夹下必须为dwg文件才能备份
                if ("map".equals(sourceFile.getParentFile().getName())
                    && !sourceFile.getName().endsWith(".dwg")) {
                    continue;
                }
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator
                                           + file[i].getName());
                // type=2为增量备份
                if (type == 2 && (sourceFile.lastModified() == targetFile.lastModified())) {
                    continue;
                }
                copyFile(sourceFile, targetFile);
                // 设置文件修改时间
                targetFile.setLastModified(sourceFile.lastModified());
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectory(dir1, dir2, type);
            }
        }
    }

}
