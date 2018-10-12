/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alfalfa.fx;

/**
 *
 * @author Usuario
 */
public class FileId {
    public String totalStorageSpace;
    public String fileName;
    public String fileIdentifier;
    public String storageSpace;
    
    public FileId(String fileName, String fileIdentifier)
    {
    this.fileName = fileName;
    this.fileIdentifier = fileIdentifier; 
    }
    
    @Override
    public String toString(){
        return fileName;
    }
    
    
}
