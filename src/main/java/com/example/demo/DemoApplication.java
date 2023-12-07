package com.example.demo;

import com.example.demo.service.DefaultTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication  implements CommandLineRunner {
	@Autowired
	DefaultTokenService tokenService;

// https://developer.okta.com/blog/2021/05/05/client-credentials-spring-security
// https://stackoverflow.com/questions/71920933/how-do-i-get-the-access-token-via-the-oauth2authorizedclientservice-in-the-clien

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// The command line runner method, runs once application is fully started
	@Override
	public void run(String... args) throws Exception {

		System.out.println("run..");
		OAuth2AccessToken oAuth2AccessToken = tokenService.getOAuth2AccessToken();
		System.out.println(oAuth2AccessToken.getTokenValue());

		String accessToken = oAuth2AccessToken.getTokenValue();
//		String accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJJektWTDVNUk1iNFFXNkYzcjNjeXBLd2hqU2VKXzhnOEt6R01WcWtESFRZIn0.eyJleHAiOjE3MDE5ODk1NzAsImlhdCI6MTcwMTk4OTI3MCwianRpIjoiNzY1MzgxYjQtMzc1Mi00MTFiLThkMmItNzQ5NTZjYTcyODZlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9vYXV0aDItZGVtby1yZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJlODAwMGZmYi1jODZjLTQ1YzAtYTNjZi01N2QyMDljNzRiMWQiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJvYXV0aDItY2xpZW50LWNyZWRlbnRpYWxzIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIvKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtb2F1dGgyLWRlbW8tcmVhbG0iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImNsaWVudEhvc3QiOiIwOjA6MDowOjA6MDowOjEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtb2F1dGgyLWNsaWVudC1jcmVkZW50aWFscyIsImNsaWVudEFkZHJlc3MiOiIwOjA6MDowOjA6MDowOjEiLCJjbGllbnRfaWQiOiJvYXV0aDItY2xpZW50LWNyZWRlbnRpYWxzIn0.nefVGopyEc-tFXeN6z7ZrK6jaaavEhBbjCpUG9SxMnQvaUO3D2VA-EDIAst3UFXw8u_B8eRdarEA8WX3M6eoSDJBosB7MPIjWHaDgntVHqC1JuaaVHah8u9Fmo_td3i-cAsSPmnRJcg1oVzbjmeJZ3qdSHvWw_YImOId7BbqIU_5eZBJKxYGVTCOWpk-f9VZZFlcvNzOxG8_C8z1tjSvxbExS6i23sn3Br7wYIhDaEGry070drTUHcJZA33610LqXvMSNojrIBHaZeL6v-Ktq5DD1mvjM2MuYWNvbj3ivjT01IZL-4WtyMUFX7Y6GJ8E60d8pRW22jn_Pyv1XPbErg";

		////////////////////////////////////////////////////
		//  STEP 2: Use the JWT and call the service
		////////////////////////////////////////////////////

		// Add the JWT to the RestTemplate headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		HttpEntity request = new HttpEntity(headers);

		// Make the actual HTTP GET request
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:8083/microservice1/home",
				HttpMethod.GET,
				request,
				String.class
		);

		String result = response.getBody();
		System.out.println("Reply = " + result);

	}
}