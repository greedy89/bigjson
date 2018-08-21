package com.senos.seno.downloadbigjson.utility;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ExtractZip {
    private static String Tag = "MyTag";

    public static boolean extract(InputStream in , String path){
        boolean result = false;
        try{
            path = path + ((path.endsWith("/")||path.endsWith("\\"))?"":System.getProperty("file.separator"));
            ZipInputStream zis = new ZipInputStream(in);
            ZipEntry ze = zis.getNextEntry();
            while(ze!=null){
                byte[] buffer = new byte[1024];
                int length ;

                String zipfolder = path+ze.getName();
                if(ze.isDirectory()){
                    new File(zipfolder).mkdirs();
                }else if(ze.getName().contains("/")){
                    String folderName = ze.getName().substring(0,ze.getName().lastIndexOf("/"));
                    folderName = folderName.replace("/",System.getProperty("file.separator"));
                    zipfolder =path+ze.getName().replace("/", System.getProperty("file.separator"));
                    new File(path+folderName).mkdirs();
                    OutputStream out = new FileOutputStream(zipfolder);
                    while ((length = zis.read(buffer)) > 0) {
                        out.write( buffer, 0, length );
                    }
                    out.flush();
                    out.close();
                }else{
                    OutputStream out = new FileOutputStream(zipfolder);
                    while ((length = zis.read(buffer)) > 0) {
                        out.write( buffer, 0, length );
                    }
                    out.flush();
                    out.close();
                }
                ze =zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            result = true;
        }catch (Exception e){
            Log.d(Tag,e.getMessage().toString());
        }
        return result;
    }


}
