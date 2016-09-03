import java.io.*;
import java.net.*;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StepFour {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException {
	  // use try catch blocks handle exceptions
	  try { 
		// URL to make API call to get prefix 
		URL url = new URL("http://challenge.code2040.org/api/prefix");
		
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
        
        // Creating output stream
        OutputStream os = apicall.getOutputStream();
        os.write(input.toString().getBytes("UTF-8"));
        os.close();
     
        // Get error code if API call fails
		if (apicall.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ apicall.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((apicall.getInputStream())));
		String output;
		String word = null;
		System.out.print("Server response: \n");
		while ((output = br.readLine()) != null) {
			// Assign API response to string word for further processing
			word = output;
			System.out.println(output);
		}
		
		// Get JSON response parsed
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(word);
		
		// capture prefix string
		String prefix = (String) obj.get("prefix");
		
		// get array of strings
		JSONArray array = (JSONArray) obj.get("array");
		
		ArrayList<String> matches = new ArrayList<String>();
		for (int i =0; i< array.size(); i++){
			String currentWord = (String) array.get(i);
			// add word to matches if it does not have prefix string
			if (!prefix.equals(currentWord.substring(0, prefix.length()))){
				matches.add(""+currentWord+"");
			}
		}
		apicall.disconnect();
		
		// making API call to post array
		URL url2 = new URL("http://challenge.code2040.org/api/prefix/validate");
		HttpURLConnection data = (HttpURLConnection) url2.openConnection();
		data.setRequestMethod("POST");
		data.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		data.setRequestProperty("Accept", "application/json");
		data.setDoInput(true);
		data.setDoOutput(true);
        
		// JSON object to send to end point 
        JSONObject params = new JSONObject();
        params.put("token", "f736e65019698ece59249b39364ec100");
        params.put("array",  matches);
        
        // Print JSON string out to see how it is formatted
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
		System.out.print("Server response: ");
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
