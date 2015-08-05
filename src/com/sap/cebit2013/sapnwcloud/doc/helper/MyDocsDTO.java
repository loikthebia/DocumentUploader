package com.sap.cebit2013.sapnwcloud.doc.helper;

import java.util.Date;  
public class MyDocsDTO {  
    public String authorName;  
    public String authorEmail;  
    public String id;  
    public long fileLength;  
    public Date creationDate;  
    public String filename;  
    public String downloadLink;  
    public String stream;  
    public String mimeType;  
    public String getMimeType() {  
        return mimeType;  
    }  
    public void setMimeType(String mimeType) {  
        this.mimeType = mimeType;  
    }  
    public String getAuthorName() {  
        return authorName;  
    }  
    public void setAuthorName(String authorName) {  
        this.authorName = authorName;  
    }  
    public String getAuthorEmail() {  
        return authorEmail;  
    }  
    public void setAuthorEmail(String authorEmail) {  
        this.authorEmail = authorEmail;  
    }  
    public String getId() {  
        return id;  
    }  
    public void setId(String id) {  
        this.id = id;  
    }  
    public long getFileLength() {  
        return fileLength;  
    }  
    public void setFileLength(long fileLength) {  
        this.fileLength = fileLength;  
    }  
    public Date getCreationDate() {  
        return creationDate;  
    }  
    public void setCreationDate(Date creationDate) {  
        this.creationDate = creationDate;  
    }  
    public String getFilename() {  
        return filename;  
    }  
    public void setFilename(String filename) {  
        this.filename = filename;  
    }  
    public String getDownloadLink() {  
        return downloadLink;  
    }  
    public void setDownloadLink(String downloadLink) {  
        this.downloadLink = downloadLink;  
    }  
    public String getStream() {  
        return stream;  
    }  
    public void setStream(String stream) {  
        this.stream = stream;  
    }  
}  