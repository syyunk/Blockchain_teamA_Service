package com.ateam.popserver.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

public class JsonParsing {

	
	/** HttpURLConnection POST 방식 */
	public static String postRequest(String targetUrl, Map<String, Object> requestMap) {

		String response = "";
		String respAddress="";

		try {

			URL url = new URL(targetUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST"); // 전송 방식
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setConnectTimeout(5000); // 연결 타임아웃 설정(5초)
			conn.setReadTimeout(5000); // 읽기 타임아웃 설정(5초)
			conn.setDoOutput(true);	// URL 연결을 출력용으로 사용(true)

			String requestBody = getJsonStringFromMap(requestMap);
			System.out.println("requestBody:" + requestBody);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(requestBody);
			bw.flush();
			bw.close();

			System.out.println("getOutputStream() :: "+conn.getOutputStream());
			System.out.println("getContentType():" + conn.getContentType()); // 응답 콘텐츠 유형 구하기
	        System.out.println("getResponseCode():"    + conn.getResponseCode()); // 응답 코드 구하기
	        System.out.println("getResponseMessage():" + conn.getResponseMessage()); // 응답 메시지 구하기

			Charset charset = Charset.forName("UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

			String inputLine;
			StringBuffer sb = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			br.close();

			response = sb.toString();
			ObjectMapper objectMapper = new ObjectMapper();
			//jsonString to HashMap by ObjectMapper
			Map<String, Object> resMap = objectMapper.readValue(sb.toString(), new TypeReference<>() {});
			respAddress = (String) resMap.get("Address");
			System.out.println("resAddress :: "+respAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respAddress;
	}
	
	/** Map을 jsonString으로 변환 */
	@SuppressWarnings("unchecked")
	public static String getJsonStringFromMap(Map<String, Object> map) {
		String jsonInString= "";
		JSONObject json = new JSONObject();
		
		for(Map.Entry<String, Object> entry : map.entrySet()) {
			
			String key = entry.getKey();
            Object value = entry.getValue();
            
            json.put(key, value);
        }
        
        return json.toJSONString();

		
//	     ObjectMapper mapper = new ObjectMapper();
//	     try {
//	    	 jsonInString = mapper.writeValueAsString(map);
//	     } catch (JsonProcessingException e) {
//	    	e.printStackTrace();
//	     }
//	     return jsonInString;
	      
	}
	
	
	
}
