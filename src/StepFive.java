import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class StepFive {
	@SuppressWarnings("unchecked")
	
	public static void main(String[] args) throws ParseException, java.text.ParseException {
	// use try catch blocks handle exceptions
	try { 

		// Setting up HTTP Objects
		URL url = new URL("http://challenge.code2040.org/api/dating");
		HttpURLConnection apicall = (HttpURLConnection) url.openConnection();
		apicall.setRequestMethod("POST");
        apicall.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        apicall.setRequestProperty("Accept", "application/json");
        apicall.setDoInput(true);
        apicall.setDoOutput(true);
        
        // JSON object to be sent to end point
        JSONObject input = new JSONObject();
        input.put("token", "f736e65019698ece59249b39364ec100");
     
        // Check how formatted JSON looks
        System.out.println(input.toJSONString());
        
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
		String date = null;
		System.out.print("Server response: ");
		while ((output = br.readLine()) != null) {
			// Assign API response to string date for further processing
			date = output;
			System.out.println(output);
		}
		
		// Get JSON response parsed
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(date);
		
		// obtain the date from the response
		String dates = (String) obj.get("datestamp");
		
		//obtain the seconds interval from response
		Long time = (Long) obj.get("interval");
		String interval = String.valueOf(time);
		int times = Integer.parseInt(interval);
		
		// Build date object in ISO 8601 format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		apicall.disconnect();
		
		// Get current date time with Calendar()
	    Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(dates));
		System.out.println("Time received from API : " + sdf.format(cal.getTime()));
		
		// add interval to date object 
		cal.add(Calendar.SECOND, times);
		System.out.println("Date after interval added : " + sdf.format(cal.getTime()));
		String formattedTime = (String) sdf.format(cal.getTime());
		   
		// making API call to send formatted date object
		URL validationUrl = new URL("http://challenge.code2040.org/api/dating/validate");
		HttpURLConnection data = (HttpURLConnection) validationUrl.openConnection();
		data.setRequestMethod("POST");
		data.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		data.setRequestProperty("Accept", "application/json");
		data.setDoInput(true);
		data.setDoOutput(true);
       
		// Build JSON object to send to end point
		JSONObject params = new JSONObject();
        params.put("token", "f736e65019698ece59249b39364ec100");
        params.put("datestamp",  formattedTime);
		
        // Check how JSON object looks before dispatch
		System.out.println(params.toJSONString());
		
		// Dispatching API call
        OutputStream submission = data.getOutputStream();
        submission.write(params.toString().getBytes("UTF-8"));
        submission.close();
		     

        if (data.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "		
            + data.getResponseCode());
		}

		BufferedReader br2 = new BufferedReader(new InputStreamReader((data.getInputStream())));

		// Get response
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