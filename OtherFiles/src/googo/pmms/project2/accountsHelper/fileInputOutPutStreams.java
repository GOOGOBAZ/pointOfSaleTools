/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Stanchart
 */
public class fileInputOutPutStreams {

    
  public synchronized  void intFileWriterReplace(String fileName, String itemToWrite) {
  
      try {
          File f = new File(fileName);
          Integer x = parseInt(itemToWrite);
          BufferedWriter writer = null;
       
              forceFileExistance(fileName);
        
          
      
              writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, false), "UTF-8"));
          
        
              writer.write(x.toString());
         
          writer.close();
          
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }

  }
  
   
   public synchronized boolean deleteFileFirst(String fileName) {
    boolean existsDeleted=false;  
       
       File f = new File(fileName);
  
     if(f.exists()){
        
       existsDeleted=       f.delete();
       
}   

     
 return existsDeleted;    
  }
  
   public synchronized boolean exitsFile(String fileName) {
    boolean exists=false;  
       
       File f = new File(fileName);
  
     if(f.exists()){
        
     exists=true;
       
}   

     
 return exists;    
  }
  
  public synchronized  void stringFileWriter(String fileName, String itemToWrite) {
   
      try {
          File f = new File(fileName);
          
          BufferedWriter writer = null;
          forceFileExistance(fileName);
          writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, false), "UTF-8"));
          writer.write(itemToWrite);
          writer.close();
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
} 
   
    public synchronized void doubleFileWriter(String fileName, String itemToWrite) {
      try {
          File f = new File(fileName);
          Double x = parseDouble(itemToWrite);
          BufferedWriter writer = null;
          forceFileExistance(fileName);
          writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, false), "UTF-8"));
          writer.write(x.toString());
          writer.close();
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
    
    
    }
     public synchronized int intFileReader(String fileName) {
     int itemReadInt=0;
      try {
          File file = new File(fileName);
          forceFileExistance(fileName);
          BufferedReader b =new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        itemReadInt = parseInt(b.readLine());
          b.close();
         
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      } return itemReadInt;
     }
     
     
    public synchronized  String stringFileReader(String fileName){
   String itemReadString =" ";
      try {
          File file = new File(fileName);
          forceFileExistance(fileName);
       try (BufferedReader brs = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"))) {
           itemReadString = brs.readLine();
       }
          
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }return itemReadString;
    }
    
    
   public synchronized double doubleFileReader(String fileName){
     double itemReadDouble =0.0;
       
      try {
          File file = new File(fileName);
          forceFileExistance(fileName);
          BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
         itemReadDouble =parseDouble( br.readLine());
          br.close();
         
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
 return itemReadDouble;
   } 
   
  public synchronized void intFileWriterAppend(String fileName, String itemToWrite) {
      try {
          File f = new File(fileName);
          Integer x = parseInt(itemToWrite);
          BufferedWriter writer = null;
          forceFileExistanceAppend(fileName);
          writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, true), "UTF-8"));
          writer.write(x.toString());
          writer.newLine();
          writer.close();
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
}
  
   public synchronized void stringFileWriterAppend(String fileName, String itemToWrite) {
   
      try {
          File f = new File(fileName);
          
          BufferedWriter writer = null;
          forceFileExistanceAppend(fileName);
          writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, true), "UTF-8"));
          writer.write(itemToWrite);
          
          writer.close();
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
} 
   
    public synchronized void doubleFileWriterAppend(String fileName, String itemToWrite){
      try {
          File f = new File(fileName);
          Double x = parseDouble(itemToWrite);
          BufferedWriter writer = null;
          forceFileExistanceAppend(fileName);
          writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, true), "UTF-8"));
          writer.write(x.toString());
          writer.close();
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
    
    
    } 
    public synchronized void forceFileExistance(String fileName) {
      File f = new File(fileName);
        Integer x = 1;
        BufferedWriter writer = null;
     if(!f.exists()){
          try {
              f.createNewFile();
              writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, true), "UTF-8"));
              writer.write(x.toString());
              writer.close();
          } catch (IOException ex) {
              Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
          }
}   
                
    }
    public synchronized String createFileName(String subdirectory1,String subdirectory2, String fileName){
    
     String filename  = System.getProperty("user.dir") + File.separator +subdirectory1 +File.separator+subdirectory2+File.separator+ fileName;
  return filename;
    }
    
    public synchronized  String createFileName1(String subdirectory1,String subdirectory2, String fileName){
    
     String filename  = System.getProperty("user.home") + File.separator +createDirectory1(subdirectory1)+File.separator+createDirectory1(subdirectory2)+File.separator+ fileName;
  return filename;
    }
    
     public synchronized  String createDirectoryName(String subdirectory1,String subdirectory2){
    
     String filename  = System.getProperty("user.dir") + File.separator +subdirectory1 +File.separator+subdirectory2;
     
  return filename;
    }
    public synchronized  String createOneDirectoryName(String DirectoryName){
    
     String dirName  = System.getProperty("user.dir") + File.separator +DirectoryName;
     
  return dirName;
    }  
    public synchronized  String createDirectory(String directoryName){
    
    String usedDirectoryName=createOneDirectoryName(directoryName);
    File theDir = new File(usedDirectoryName);

// if the directory does not exist, create it
if (!theDir.exists()) {
  
   

    try{
        theDir.mkdir();
   
    } 
    catch(SecurityException se){
      se.toString();
    }        
    
}
 return  usedDirectoryName; 
    } 
   public synchronized String createDirectory1(String directoryName){
    

    File theDir = new File(directoryName);

// if the directory does not exist, create it
if (!theDir.exists()) {
  
   

    try{
        theDir.mkdir();
   
    } 
    catch(SecurityException se){
      se.toString();
    }        
    
}
 return  directoryName; 
    }  
    
    
    public synchronized void fileNewLine(String fileName) {
      try {
          File f = new File(fileName);
          
          BufferedWriter writer = null;
          forceFileExistance(fileName);
          writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, true), "UTF-8"));
          writer.newLine();
          writer.close();
      } catch (UnsupportedEncodingException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      } catch (FileNotFoundException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
} 


public synchronized void forceFileExistanceAppend(String fileName) {
      File f = new File(fileName);
          if(!f.exists()){
          try {
              f.createNewFile();
          } catch (IOException ex) {
              Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
          }
     }

}
public synchronized void deleteFileExistance(String fileName) {
      File f = new File(fileName);
          if(f.exists()){
        f.delete();
     }

}

public synchronized  String[] fileNamesInDirectory(String directoryName) {
    String[] filenames=null; File f = new File(directoryName);
if(f.exists()==true){ 
if(f.isDirectory()==true){
 filenames= f.list();
}}
return filenames;
}

public synchronized void forceFileExistanceHundred(String fileName) {
      File f = new File(fileName);
        Integer x = 10000;
        BufferedWriter writer = null;
     if(!f.exists()){
          try {
              f.createNewFile();
              writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, true), "UTF-8"));
              writer.write(x.toString());
              writer.close();
          } catch (IOException ex) {
              Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
          }
}   
                
    }


public synchronized  void forceFileExistanceTen(String fileName) {
      File f = new File(fileName);
        Integer x = 10;
        BufferedWriter writer = null;
     if(!f.exists()){
          try {
              f.createNewFile();
              writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, true), "UTF-8"));
              writer.write(x.toString());
              writer.close();
          } catch (IOException ex) {
              Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
          }
}   
                
    }
public synchronized void forceFileExistanceZero(String fileName) {
      File f = new File(fileName);
        Integer x = 0;
        BufferedWriter writer = null;
     if(!f.exists()){
          try {
              f.createNewFile();
              writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, true), "UTF-8"));
              writer.write(x.toString());
              writer.close();
          } catch (IOException ex) {
              Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
          }
}   
                
    }

 public synchronized String stringFileReaderEmpty(String fileName){
   String itemReadString =" ";
      try {
          File file = new File(fileName);
        
       try (BufferedReader brs = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"))) {
           itemReadString = brs.readLine();
       }
          
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }return itemReadString;
    }
 public synchronized BufferedReader FileBufferedReaderReader(String fileName){
   BufferedReader br=null;
       
      try {
          File file = new File(fileName);
          forceFileExistance(fileName);
         br= new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
 
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
 return  br;
   } 
   
  public synchronized  BufferedWriter FileBufferedWriter(String fileName) {
     
      BufferedWriter writer = null;
      try {
          File f = new File(fileName);
         
          
         
          writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f, false), "UTF-8"));
         
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
      return  writer ;
}
public synchronized  InputStreamReader FileInputStreamReader(String fileName){
   InputStreamReader sr=null;
       
      try {
          File file = new File(fileName);
          forceFileExistance(fileName);
         sr= new InputStreamReader(new FileInputStream(file),"UTF-8");
 
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
 return  sr;
   } 
   
  public synchronized  OutputStreamWriter FileOutputStreamWriter(String fileName) {
     
      OutputStreamWriter sw = null;
      try {
          File f = new File(fileName);
         
          
          forceFileExistanceAppend(fileName);
          sw = new OutputStreamWriter( new FileOutputStream(f, true), "UTF-8");
         
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
      return  sw ;
}

public synchronized  FileInputStream FileInputStream(String fileName){
   FileInputStream is=null;
       
      try {
          File file = new File(fileName);
          forceFileExistance(fileName);
         is= new FileInputStream(file);
 
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
 return  is;
   } 
   
  public synchronized  FileOutputStream FileOutputStream(String fileName) {
     
      FileOutputStream os = null;
      try {
          File f = new File(fileName);
         
          
          forceFileExistanceAppend(fileName);
          os =  new FileOutputStream(f, true);
         
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
      return  os ;
}

public synchronized void writeImageToDiffrentLocation(String file1,String file2){
      File f1 = new File(file1);
          File f2 = new File(file2);
      try {
        
         BufferedImage image = ImageIO.read(f1);
          if(image!=null){
          ImageIO.write(image, "jpg",f2);
          }
      } catch (IOException ex) {
          Logger.getLogger(fileInputOutPutStreams.class.getName()).log(Level.SEVERE, null, ex);
      }
   
}
          
        





















} 
        




