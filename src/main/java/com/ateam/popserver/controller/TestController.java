package com.ateam.popserver.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ateam.popserver.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class TestController {
	
	@GetMapping("/product/uploadTest")
	public void uploadTest() {
		
	}
//	
//	@ResponseBody
//	@GetMapping("/images/{filename}")
//	public Resource showImage(@PathVariable String filename) throws
//		MalformedURLException {
//		 	return new UrlResource("file:" + file.getFullPath(filename));
//	 }
	 @PostMapping(value="/product/uploadTest")
	    public String fileUploadTest(MultipartHttpServletRequest request
	            ,Model model) {
		 System.out.println(request.getMultiFileMap());
	        
	        String rootUploadDir = "C:"+File.separator+"Upload"; // C:/Upload
	        
	        File dir = new File(rootUploadDir + File.separator + "testfile"); 
	        
	        if(!dir.exists()) { //업로드 디렉토리가 존재하지 않으면 생성
	            dir.mkdirs();
	        }
	        
	        Iterator<String> iterator = request.getFileNames(); //업로드된 파일정보 수집(2개 - file1,file2)
	        
	        int fileLoop = 0;
	        String uploadFileName;
	        MultipartFile mFile = null;
	        String orgFileName = ""; //진짜 파일명
	        String sysFileName = ""; //변환된 파일명
	        
	        ArrayList<String> list = new ArrayList<String>();
	        
	        while(iterator.hasNext()) {
	            fileLoop++;
	            
	            uploadFileName = iterator.next();
	            mFile = request.getFile(uploadFileName);
	            System.out.println("uploadFileName :"+uploadFileName );
	            orgFileName = mFile.getOriginalFilename();    
	            System.out.println("orgFileName:"+orgFileName);
	            
	            if(orgFileName != null && orgFileName.length() != 0) { //sysFileName 생성
	                System.out.println("if문 진입");
	                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMDDHHmmss-" + fileLoop);
	                Calendar calendar = Calendar.getInstance();
	                sysFileName = simpleDateFormat.format(calendar.getTime()); //sysFileName: 날짜-fileLoop번호
	                
	                
	                try {
	                    System.out.println("try 진입");
	                    mFile.transferTo(new File(dir + File.separator + sysFileName)); // C:/Upload/testfile/sysFileName
	                    list.add("원본파일명: " + orgFileName + ", 시스템파일명: " + sysFileName);
	                    
	                }catch(Exception e){
	                    list.add("파일 업로드 중 에러발생!!!");
	                }
	            }//if
	        }//while
	        
	        model.addAttribute("list", list);
	        
	        return "redirect:/product/uploadTest";
	    }
	@GetMapping("/uploadex")
	public void uploadex() {
		
	}
	 @PostMapping(value="/uploadex")
		public void uploadFile(MultipartFile[] uploadFiles) {
			//log.info("uploadPath:" + uploadPath);
			for(MultipartFile uploadFile : uploadFiles) {
				//이미지 파일이 아닌 파일이 있으면 업로드 중지
				if(uploadFile.getContentType().startsWith("image") == false) {
					log.warn("이미지 파일만 업로드 하세요");
					return;
				}
				
				String originalName = uploadFile.getOriginalFilename();
				String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
				log.info("fileName:" + fileName);
				
				//파일을 저장할 디렉토리 생성
				String realUploadPath = makeFolder();
				
				//UUID 생성
				String uuid = UUID.randomUUID().toString();
				//실제 저장할 파일 경로를 생성
				String saveName = uploadPath + File.separator + realUploadPath + 
						File.separator + uuid + fileName;
				Path savePath = Paths.get(saveName);
				try {
					//파일 업로드
					uploadFile.transferTo(savePath);
				}catch(Exception e) {
					System.out.println(e.getLocalizedMessage());
					e.printStackTrace();
				}
			}
	 }
	  	private String uploadPath = "C:/Users/tjoeun304/Documents/data";//업로드 경로 지정
	  	private String makeFolder() {
			//오늘 날짜를 문자열로 가져옴
			String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			// /문자열을 파일의 경로 구분자로 변경
			String realUploadPath = str.replace("//", File.separator);
			//uploadPath 와  realUploadPath를 합쳐서 파일 객체를 생성
			File uploadPathDir = new File(uploadPath, realUploadPath);
			//이 파일이 존재하지 않는다면 디렉토리를 생성
			if(uploadPathDir.exists() == false) {
				uploadPathDir.mkdirs();
			}
			//디렉토리 이름을 리턴
			return realUploadPath;
		}
}