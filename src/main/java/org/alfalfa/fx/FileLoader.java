/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alfalfa.fx;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author Usuario
 */
public class FileLoader {
    public String address;
    public ArrayList<FileId> filelist;
    //public TextField urlInputField; 
    //public Label infoText;
    //public TextField fileInputField;
    public BufferedReader reader;
    public BufferedOutputStream out;
    public BufferedInputStream fileReader;
    
    
    public FileLoader()
    {
        //this.fileInputField = fileInputField;
        //this.infoText = infoText;
        //this.urlInputField = urlInputField;
    }
    
    
    public File fileReader(String fileName) throws FileNotFoundException
    {
       
        File file = new File(fileName);
        try
        {    
            if(!file.exists())
            {
                //this.infoText.setText("File doesn't exist under the current working directory");
            }
            else
            {
                fileReader = new BufferedInputStream( new FileInputStream(file));
            }            
        }
        catch(FileNotFoundException e1)
        {
            System.out.println(e1.toString());
        }
        
        return file;
        }    
  
    public ArrayList<FileId> getFiles(String input)
    {   
        this.address = input;
        //System.out.println(this.address);
        filelist = new ArrayList();
        
        
        
        // Implementing REST API Get request
        try
        {
        
            URL url = new URL(this.address + "/files");
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = ( HttpURLConnection ) conn; // we cast it as HTTP connection
            httpConn.setAllowUserInteraction( false );
            httpConn.setInstanceFollowRedirects( true ); // useful if we want to migrate a server   
            httpConn.setRequestMethod( "GET" );
            httpConn.connect();
            //int responseCode;
            //this.infoText.setText("Fetching list of available files");
            //System.out.println("blabla");
            
            String str = readInputStreamToString(httpConn);
            JSONArray json = new JSONArray(str);
            //JSONArray json = new JSONArray("[{\"id\":\"097135onk1\",\"name\":\"BlaBla.txt\"},{\"id\":\"123657\",\"name\":\"Despacito.txt\"}]");
            
            for(int i=0; i < json.length();i++)
            {
                String fileName = json.getJSONObject(i).getString("name");
                String fileId = json.getJSONObject(i).getString("id");
                filelist.add(new FileId(fileName, fileId));
                
            }
            
           
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
    return filelist;  
    }
    
    
    
    public void downloadFile(int choice){
        String fileCode = this.filelist.get(choice).fileIdentifier;
        System.out.println(fileCode);
        try
        {
            URL url = new URL(this.address + "/files/" + fileCode );
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = ( HttpURLConnection ) conn; // we cast it as HTTP connection
            httpConn.setAllowUserInteraction( false );
            httpConn.setInstanceFollowRedirects( true ); // useful if we want to migrate a server   
            httpConn.setRequestMethod( "GET" );
            httpConn.connect();
            
            String str = readInputStreamToString(httpConn);
            System.out.println(str);
            JSONObject json = new JSONObject(str);
        
            String fileName = json.getString("name");
            String fileData = json.getString("data");
            //filelist.add(new FileId(fileName, fileData));
            writeToFile(fileName, fileData);   
            
            
        }
        
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
    }
    
    public String readInputStreamToString(HttpURLConnection conn)
    {
        String result = "";
        StringBuilder str = new StringBuilder();
        try{
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
            result = reader.readLine();
            
            //result = reader.readLine();
            //System.out.println(result);
            while(result != null)
            {
                str.append(result);
                result = reader.readLine();
            }
            //System.out.println(str.toString());
            reader.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        return str.toString();
    }
    
    public void writeToFile(String fileName, String fileData)
    {
        try{
        FileOutputStream fileOutput = new FileOutputStream(fileName);     
        fileOutput.write(fileData.getBytes());
        

        fileOutput.close();
        } catch(Exception e){
            System.out.println(e.toString());
        }
        
        
    }
    
    public String deleteFile(int choice){
        String fileCode = this.filelist.get(choice).fileIdentifier;
        System.out.println(fileCode);
        String fileName ="";
        try
        {
            URL url = new URL(this.address + "/files/" + fileCode );
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = ( HttpURLConnection ) conn; // we cast it as HTTP connection
            httpConn.setAllowUserInteraction( false );
            httpConn.setInstanceFollowRedirects( true ); // useful if we want to migrate a server   
            httpConn.setRequestMethod( "DELETE" );
            httpConn.connect();
            
            String str = readInputStreamToString(httpConn);
            System.out.println(str);
            JSONObject json = new JSONObject(str);
        
            fileName = json.getString("name");            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        return fileName;
    }
    
    
    public String uploadFile(String uploadFileName)
    {
        String state="";
        String result;
        StringBuilder str = new StringBuilder();
        JSONObject jsonToSend = new JSONObject();
        //System.out.println(uploadFileName);
        File f = new File(uploadFileName);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
                

            
        try
        {
            if(f.exists())
            {
                FileReader inputFile = new FileReader(uploadFileName);
                //System.out.println(inputFile.toString());
                reader = new BufferedReader(inputFile);
                result = reader.readLine();
                //System.out.println(result);
                while( result != null )
                {
                    str.append(result);//  
                    result = reader.readLine();
                }
                //System.out.println(str.toString());
                jsonToSend.put("name",uploadFileName);
                jsonToSend.put("data",str.toString());
                
            }
            else
            {
            state = "File doesn't exist under the current working directory!";
            }
            
            URL url = new URL(this.address + "/files");
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestProperty( "Content-Type", "application/json" );
            httpConn.setRequestProperty("Accept", "application/json");
            httpConn.setRequestMethod("POST");
            httpConn.connect();
            
            OutputStream os = httpConn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            jsonToSend.write(osw);
            osw.flush();
            osw.close();
            
            
            
            String response = readInputStreamToString(httpConn);
            System.out.println(response);
            JSONObject jsonResponse = new JSONObject(response);
        
            state = jsonResponse.getString("id");            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        return state;
    }
    
    
}

