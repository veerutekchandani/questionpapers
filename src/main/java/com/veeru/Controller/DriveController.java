package com.veeru.Controller;

import com.mysql.cj.protocol.Resultset;
import com.veeru.Model.DownloadFile;
import com.veeru.Model.QuestionPaper;
import com.veeru.Model.UploadPaper;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.veeru.Service.DBConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@PropertySource(ignoreResourceNotFound=true,value="classpath:application.properties")
@Controller
public class DriveController {

    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String USER_IDENTIFIER_KEY = "MY_DUMMY_USER";

    @Value("${google.oauth.callback.uri}")
    private String CALLBACK_URI;

    @Value("${google.secret.key.path}")
    private Resource SecretKeys;

    @Value("${google.credentials.folder.path}")
    private Resource credentialsFolder;

    private GoogleAuthorizationCodeFlow flow;

    @Autowired
    ServletContext context;

    @PostConstruct
    public void init() throws IOException {
        File credentialFile = ResourceUtils.getFile("json/credentials.json");
        GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY,new InputStreamReader(SecretKeys.getInputStream()));
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,JSON_FACTORY,secrets,SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(credentialFile)).build();
    }

    // this function is called after admin enters correct username and password
    @RequestMapping(value = {"/driveLogin"} )
    public void doGoogleSignIn(HttpServletResponse response) throws Exception {
        GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        String redirectURL = url.setRedirectUri(CALLBACK_URI).setAccessType("offline").build();
        response.sendRedirect(redirectURL);
    }

    // this function is called after successfull google signup
    @GetMapping(value = {"/oauth"} )
    public String saveAuthorizationCode(HttpServletRequest request,Model model) {
        String code = request.getParameter("code");
        if(code!=null) {
            try {
                saveToken(code);
                // on successful signup , it will be redirected to edit page
                model.addAttribute("uploadPaper",new UploadPaper());
                return "edit";
            }
            catch(Exception e) {
                // if any exception occurs then we will redirect it to admin login page
                return "admin";
            }
        }
        else {
            // if code is null then , redirect to admin login page
            return "admin";
        }
    }

    // function to save response token of google signup
    private void saveToken(String code) throws Exception {
        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(CALLBACK_URI).execute();
        flow.createAndStoreCredential(response,USER_IDENTIFIER_KEY);
    }


    // FUNCTION TO UPLOAD FILE TO GOOGLE DRIVE FOLDER
    @RequestMapping(value = {"/upload"} , method = RequestMethod.POST)
    public String uploadFile(@Validated UploadPaper uploadPaper, HttpServletResponse response, Model model) {

        MultipartFile multipartFile = uploadPaper.getFile();
        if(alreadyPresent(uploadPaper.getSubject(),uploadPaper.getTerm(),uploadPaper.getYear())) {
            model.addAttribute("error","File already present");
            return "edit";
        }
        try {
            // converting multipart file to java.io.file
            File convFile = new File(multipartFile.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(multipartFile.getBytes());
            fos.close();

            // uploading java.io.file to google drive using google drive api
            Credential credential = flow.loadCredential(USER_IDENTIFIER_KEY);
            Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("QuickStart").build();
            com.google.api.services.drive.model.File drivefile = new com.google.api.services.drive.model.File();
            // calling function to get folderId where we have to upload file
            String folderId = getFolderID(uploadPaper.getTerm(), uploadPaper.getSubject());

            // file name of uploaded file
            String fileName = uploadPaper.getSubject() + "_" + uploadPaper.getTerm() + "_" + uploadPaper.getYear();
            drivefile.setParents(Collections.singletonList(folderId));
            drivefile.setName(fileName);
            FileContent content = new FileContent("application/pdf", convFile);
            com.google.api.services.drive.model.File uploadfile = drive.files().create(drivefile, content).setFields("id,webContentLink").execute();

            String fileref = String.format("{fileId : '%s' }", uploadfile.getId());
            response.getWriter().write(fileref);
            if(!addPaper(uploadPaper.getSubject(),uploadPaper.getTerm(),uploadPaper.getYear(),uploadfile.getWebContentLink())){
                model.addAttribute("error","File Entry not inserted to sql table!");
            }
            model.addAttribute("success","File Uploaded Successfully!");
        }
        catch (Exception e) {
            model.addAttribute("error","File not Uploaded.");
            return "edit";
        }

        return "edit";
    }

    // to check whether file paper is already present or not
    private boolean alreadyPresent(String subject,String term,String year) {

        String paperName = subject+"_"+term+"_"+year;
        Connection con;
        try {
            con = DBConnection.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from papers where paperName = '"+ paperName +"'");
            if(rs.next()) {
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


    // function to add paper to papers table
    private boolean addPaper(String subject, String term,String year, String paperLink) {
        String paperName = subject+"_"+term+"_"+year;
        Connection con;
        try {
            con = DBConnection.getConnection();
            Statement statement = con.createStatement();
            int rs = statement.executeUpdate("insert into papers values('"+paperName+"','"+paperLink+"')");
            if(rs>0) {
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // function to get folder id of the required folder
    public String getFolderID(String termName , String subjectName) throws Exception {

        String quesPaperId = "";
        String folderName = subjectName + "_" + termName + "_SEM";
        Credential credential = flow.loadCredential(USER_IDENTIFIER_KEY);

        Drive drive = new Drive.Builder(HTTP_TRANSPORT,JSON_FACTORY,credential).setApplicationName("QuickStart").build();
        String folderId = "1HKnqkH3tFVHAHrYQFZX6ztwo10m8eUrK";
        String pageToken = null;
        do {
            FileList result = drive.files().list()
                    .setQ("mimeType='application/vnd.google-apps.folder'")
                    .setSpaces("drive")
                    .setFields("nextPageToken, files(id, name, mimeType)")
                    .setPageToken(pageToken)
                    .execute();
            for (com.google.api.services.drive.model.File file : result.getFiles()) {
                if(file.getName().equalsIgnoreCase(folderName)) {
                    return file.getId();
                }
                if(file.getName().equalsIgnoreCase("question_papers")) {
                    quesPaperId = file.getId();
                }
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null);

        return createFolder(quesPaperId,subjectName,termName);
    }


    // function to create folder for the subject , if it is not present
    public String createFolder(String folderId, String subjectName,String termName) throws Exception{
        Credential credential = flow.loadCredential(USER_IDENTIFIER_KEY);
        Drive drive = new Drive.Builder(HTTP_TRANSPORT,JSON_FACTORY,credential).setApplicationName("QuickStart").build();

        String responseId = "";

        // main folder for subject
        com.google.api.services.drive.model.File folder = new com.google.api.services.drive.model.File();
        folder.setParents(Collections.singletonList(folderId));
        folder.setName(subjectName);
        folder.setMimeType("application/vnd.google-apps.folder");
        com.google.api.services.drive.model.File file;
        file = drive.files().create(folder).setFields("id").execute();

        String subjectFolderId = file.getId();
        // folder for mid term question papers
        folder.setParents(Collections.singletonList(subjectFolderId));
        folder.setName(subjectName+"_MID_SEM");
        folder.setMimeType("application/vnd.google-apps.folder");
        file = drive.files().create(folder).setFields("id").execute();
        if(termName.equalsIgnoreCase("MID")) {
            responseId = file.getId();
        }

        // folder for end term question papers
        folder.setParents(Collections.singletonList(subjectFolderId));
        folder.setName(subjectName+"_END_SEM");
        folder.setMimeType("application/vnd.google-apps.folder");
        file = drive.files().create(folder).setFields("id").execute();
        if(termName.equalsIgnoreCase("END")) {
            responseId = file.getId();
        }

        return responseId;
    }


    // PROGRAM TO LIST ALL FILES FOR DOWNLOADING
    @RequestMapping(value = "/showQuestionPapers")
    public String listAllFiles(@ModelAttribute("questionpaper") QuestionPaper questionPaper, Model model)  {
        String subject = questionPaper.getSubject();
        String term = questionPaper.getTerm();
        String q = subject+"_"+term;
        Connection con = null;
        List<DownloadFile> downloadFiles = new ArrayList<DownloadFile>();
        try {
            con = DBConnection.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from papers where paperName LIKE '"+q+"%'");
            while(rs.next()) {
                DownloadFile downloadFile = new DownloadFile();
                downloadFile.setFileName(rs.getString("paperName"));
                downloadFile.setFileLink(rs.getString("paperLink"));
                downloadFiles.add(downloadFile);
            }
            rs = statement.executeQuery("select subjectName from subjects where subjectCode = '"+subject+"'");
            String subjectName = null;
            if(rs.next()) {
                subjectName = rs.getString("subjectName");
            }
            model.addAttribute("subjectName",subjectName);
            model.addAttribute("downloadfiles",downloadFiles);
        }
        catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("error","Error while opening papers");
            return "home";
        }

        return "show";
    }

    private String getFilePath() {
        try {
            String path =  this.getClass().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "utf-8");
            String pathArr[] = fullPath.split("/WEB-INF/");
            String jsonFilepath = pathArr[0] + "/resources/json/paperLink.json";
            return jsonFilepath;
        }
        catch (Exception e) {
            return "error";
        }
    }

    @RequestMapping(value = "/downloadfile/{id}")
    public String downloadFile(@PathVariable String id) throws Exception {
        Credential credential = flow.loadCredential(USER_IDENTIFIER_KEY);
        Drive drive = new Drive.Builder(HTTP_TRANSPORT,JSON_FACTORY,credential).setApplicationName("QuickStart").build();

        OutputStream outputStream = new ByteArrayOutputStream();
        drive.files().get(id).executeMediaAndDownloadTo(outputStream);
        return "success";
    }
}
