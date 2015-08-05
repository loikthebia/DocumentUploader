package com.sap.cebit2013.sapnwcloud.doc.servlets;


import java.io.File;  
import java.io.IOException;  
import java.util.List;

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
import com.sap.cebit2013.sapnwcloud.doc.helper.CmisHelper;  
import com.sap.cebit2013.sapnwcloud.doc.helper.MyDocsDTO; 
import com.grayscale.Grayscale;

/** 
* Servlet implementation class HelloWorld 
*/  
public class HelloWorld extends HttpServlet {  
    private static final long serialVersionUID = 1L;  
    /** 
     * @see HttpServlet#HttpServlet() 
     */  
    public HelloWorld() {  
        super();  
    }  
    /** 
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) 
     */  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // Read-in the action parameter  
        String action = request.getParameter("action");  
        // Show the list of all files  
        if (action == null || action.equalsIgnoreCase("list")) {  
            response.getWriter().println("<html><head><title>SAP HANA Cloud Platform: MyDocs App</title></head><body>");  
            List<MyDocsDTO> docs = CmisHelper.getDocumentsList();  
            response.getWriter().println(buildHtmlPageForShowingAllDocuments(docs, null, 0));  
            response.getWriter().println("</body></html>");  
        }  
        // If the user wants to get the file out of the repository...  
        if (action != null && action.equalsIgnoreCase("getfile")) {  
            String docId = request.getParameter("docid");  
            CmisHelper cmisHelper = retrieveCmisHelperClass(request);  
            cmisHelper.streamOutDocument(response, docId);  
        }  
    }  
    
    
    
    
    /** 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) 
     */  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//deleting all the documents from the documents service repository
    	CmisHelper.deleteAllDocs();
    	//image upload
        String realPathOfApp = getServletContext().getRealPath("");  
        CmisHelper cmis = new CmisHelper();  
        File file = cmis.uploadDocument(realPathOfApp, request);
        //retrieval of the number of loops parameter
        //String n = request.getParameter("number_of_loops");
        String n = "5";
        int iterations = Integer.parseInt(n);
        //definition and initialization of the clock
        long estimatedTime[] = new long[iterations];
        long startTime;
        //grayscale call loop
        for(int i=0; i<iterations; i++){
        	startTime = System.nanoTime();
        	File gfile = Grayscale.grayscale(file, i, false);
        	estimatedTime[i] = System.nanoTime() - startTime;
        }  
        //saving a grayscale image for display purpose
        File gfile = Grayscale.grayscale(file, 1, true);
        cmis.addDocument(gfile);
        //deleting file from the buffer
        gfile.delete();
        //view generation
        response.getWriter().println("<html><head><title>SAP HANA Cloud Platform: MyDocs App</title></head><body>");  
        List<MyDocsDTO> docs = CmisHelper.getDocumentsList();  
        response.getWriter().println(buildHtmlPageForShowingAllDocuments(docs, estimatedTime, iterations));  
        response.getWriter().println("</body></html>");  
    }  
    
    
     	
	
	/** 
     * 
     * @param docs 
     * @return HTML string with the field for the file upload and the lkist of all files 
     */  
    private String buildHtmlPageForShowingAllDocuments(List<MyDocsDTO> docs, long[] estimatedTime, int iterations) {  
        String result = "";  
        // Print the "Upload File" field prior to the list of files, so that the end user can upload files  
        result += "<fieldset><legend>Upload File</legend><form action='HelloWorld'";  
        result += "method='post' enctype='multipart/form-data'>";  
        result += "<label for='filename'>File: </label><input id='filename' type='file' name='filename' size='50'/><br/>";  
        result += "<input type='number' name='n' value='1'/>";
        result += "<input type='submit' value='Upload File'/></form></fieldset>";  
        // And now print the list of files  
        result += "<h1>List of all files</h1>";
        result += "<li>nanoseconds</li>";
        for(int i=0; i<iterations; i++){
        	result += "<p>"+estimatedTime[i]+"</p>";
        }
        result += "<ul>";  
        if (docs != null && docs.size() > 0) {  
            for (int i = 0; i < docs.size(); i++) {  
                MyDocsDTO doc = docs.get(i);  
                String row = "<li>" + "<a href=" + '"' + doc.getDownloadLink() + '"' + ">" + doc.getFilename() + "<a> (" + doc.getFileLength() + " bytes)</li>";  
                result += row;  
            }  
        }  
        result += "</ul>";  
        return result;  
    }  
    /** 
     * This method ensures that an existing CmisHelper class that was saved in the HttpServletRequest is re-used whenever 
     * possible instead of creating new instances of CmisHelper classes everytime you call the doGet or doPost from the 
     * method 
     * 
     * @param request 
     *            The HttpServletRequest 
     * @return The CmisHelper class 
     */  
    private CmisHelper retrieveCmisHelperClass(HttpServletRequest request) {  
        CmisHelper result = null;  
        if (request != null) {  
            HttpSession httpSession = request.getSession();  
            if (httpSession != null) {  
                CmisHelper cmisHelperHttpSession = (CmisHelper) httpSession.getAttribute("myCmisHelper");  
                // If an instance of CmisHelper is already there, use it  
                if (cmisHelperHttpSession != null) {  
                    result = cmisHelperHttpSession;  
                }  
                // If there isn't one, create a new one and store it in the session of the HttpServletRequest so that it  
                // can be re-used the next time  
                else {  
                    result = new CmisHelper();  
                    httpSession.setAttribute("myCmisHelper", result);  
                }  
            }  
        }  
        return result;  
    }  
}  