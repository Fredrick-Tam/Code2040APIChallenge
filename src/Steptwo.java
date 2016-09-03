import java.io.*;
import java.net.*;
import org.json.simple.JSONObject;

public class StepTwo {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		// use try catch blocks handle exceptions
	  	try { 
		  
			// URL to make API call to get string to be reversed
			URL url = new URL("http://challenge.code2040.org/api/reverse");
			
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
	        OutputStream os = apicall.getOutputStream();
	        os.write(input.toString().getBytes("UTF-8"));
	        os.close();
	     
	        // If call is not successful, get error code for easy debugging
			if (apicall.getResponseCode() != 200) {
				throw new RuntimeException("Your API Call Failed : HTTP error code : "
				+ apicall.getResponseCode());
			}

			BufferedReader resp = new BufferedReader(new InputStreamReader((apicall.getInputStream())));

			String output;
			String word = null;
			System.out.print("Response from Server: word to reverse is: ");
			while ((output = resp.readLine()) != null) {
				// Assign API response to string word for further processing
				word = output;
				System.out.println(output);
			}
			
			// End API call
			apicall.disconnect();
			
			// reversing the String
			StringBuilder reversedWord = new StringBuilder();
			
			// Reversing each letter of the word gotten from Server 
			for (int i = word.length() -1 ; i >= 0;i--){
				reversedWord.append(String.valueOf(word.charAt(i)));
			}
			
			// Convert the String Builder object back to a string
			String revword = reversedWord.toString();
			
			// Setting API call to send reversed word
			URL validationUrl = new URL("http://challenge.code2040.org/api/reverse/validate");
			HttpURLConnection revapicall = (HttpURLConnection) validationUrl.openConnection();
			revapicall.setRequestMethod("POST");
	        revapicall.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        revapicall.setRequestProperty("Accept", "application/json");
	        revapicall.setDoInput(true);
	        revapicall.setDoOutput(true);
	        
	        // Building the JSON Object to send
	        JSONObject revinput = new JSONObject();
	        revinput.put("token", "f736e65019698ece59249b39364ec100");
	        revinput.put("string", revword);
	        
	        // Looking at JSON format before sending
	        System.out.println(revinput.toJSONString());
	        
	        // Sending response
	        OutputStream revcall = revapicall.getOutputStream();
	        revcall.write(revinput.toString().getBytes("UTF-8"));
	        revcall.close();
	     
	        // Get error code for easy debugging if API call fails
			if (revapicall.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ revapicall.getResponseCode());
			}
			
			// Getting API response and printing the response
			BufferedReader br = new BufferedReader(new InputStreamReader((revapicall.getInputStream())));
			String formattedWord = null;
			System.out.print("Server response:");
			while ((formattedWord = br.readLine()) != null) {
				System.out.println(formattedWord);
			}
			revapicall.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();
		}
	}
}
