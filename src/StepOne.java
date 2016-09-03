import java.io.*;
import java.net.*;
import org.json.simple.JSONObject;

public class StepOne {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
	// Use try catch blocks handle exceptions
	try { 
		
		// URL to make API call to
		URL url = new URL("http://challenge.code2040.org/api/register");
		
		// Setting up HTTP Objects 
		HttpURLConnection apicall = (HttpURLConnection) url.openConnection();
		apicall.setRequestMethod("POST");
        apicall.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        apicall.setRequestProperty("Accept", "application/json");
        apicall.setDoInput(true);
        apicall.setDoOutput(true);
        
        // URL of my repository
        URL link = new URL("http://github.com/Fredrick-Tam/Code2040APIChallenge");
        
        // Building JSON object
        JSONObject input = new JSONObject();
        input.put("token", "f736e65019698ece59249b39364ec100");
        input.put("github", "http://github.com/Fredrick-Tam/Code2040APIChallenge" );
        
        // Print out JSON to see if it is formatted well
        System.out.println(input.toJSONString());
        
        // Creating Output Stream
        OutputStream os = apicall.getOutputStream();
        os.write(input.get("github").toString().getBytes("UTF-8"));
        os.close();
     
        // if call is not successful, get error code for easy debugging
		if (apicall.getResponseCode() != 200) {
			throw new RuntimeException("Your API request Failed : HTTP error code : "
			+ apicall.getResponseCode());
		}

		// Receiving response from API and printing it out
		BufferedReader resp = new BufferedReader(new InputStreamReader((apicall.getInputStream())));
		String output;
		System.out.println("Server response .... \n");
		while ((output = resp.readLine()) != null) {
			System.out.println(output);
		}

		apicall.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
	}
}
