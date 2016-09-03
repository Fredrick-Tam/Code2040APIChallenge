import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;


import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StepThree {

	public static void main(String[] args) throws ParseException {
		// use try catch blocks handle exceptions
		try { 
			
			// URL to make API call to get haystack
			URL url = new URL("http://challenge.code2040.org/api/haystack");
			
			// Setting up HTTP Objects
			HttpURLConnection apicall = (HttpURLConnection) url.openConnection();
			apicall.setRequestMethod("POST");
	        apicall.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        apicall.setRequestProperty("Accept", "application/json");
	        apicall.setDoInput(true);
	        apicall.setDoOutput(true);
	        
	        // JSON object to be sent to end point
	        JSONObject input = new JSONObject();
	        input.put("token", "f736e65019698ece59249b39364ec100");
	       
	        // Creating Output Stream of api call
	        System.out.println(input.toJSONString());
	        OutputStream os = apicall.getOutputStream();
	        os.write(input.toString().getBytes("UTF-8"));
	        os.close();
	     
	        // If call is not successful, get error code for easy debugging
			if (apicall.getResponseCode() != 200) {
				throw new RuntimeException("Your Request failed : HTTP error code : "
				+ apicall.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((apicall.getInputStream())));
			String output;
			String response = null; 
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				// Assign API response to string for further processing
				response = output;
				System.out.println(output);
			}

			// Get JSON response parsed
			int positionOfWord = 0;
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(response);
			
			// Get value of needle and haystack array
			String needle = (String) obj.get("needle");
			JSONArray haystack = (JSONArray) obj.get("haystack");
			
			// search for location of needle string in haystack
			for (int i =0; i< haystack.size(); i++){
				if(needle.equals(haystack.get(i)))
					positionOfWord = i;
			}
			System.out.println("needle: " + needle + " |" +" position in haystack: " + positionOfWord);
			apicall.disconnect();
			
			// Setting API call to send position of needle
			URL url2 = new URL("http://challenge.code2040.org/api/haystack/validate");
			HttpURLConnection data = (HttpURLConnection) url2.openConnection();
			data.setRequestMethod("POST");
			data.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			data.setRequestProperty("Accept", "application/json");
			data.setDoInput(true);
			data.setDoOutput(true);
	        
			// Making the JSON object to send
	        JSONObject params = new JSONObject();
	        params.put("token", "f736e65019698ece59249b39364ec100");
	        params.put("needle", String.valueOf(positionOfWord));
	        
	        // Printing out JSON to check formatting
	        System.out.println(params.toJSONString());
	        
	        // Send data to end point
	        OutputStream submission = data.getOutputStream();
	        submission.write(params.toString().getBytes("UTF-8"));
	        submission.close();
	     
	        // Get error code if API call fails 
			if (data.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ data.getResponseCode());
			}

			BufferedReader br2 = new BufferedReader(new InputStreamReader((data.getInputStream())));

			String output2;
			System.out.print("Server Response: ");
			while ((output2 = br2.readLine()) != null) {
				System.out.println(output2);
			}
			data.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }

		}
	}
