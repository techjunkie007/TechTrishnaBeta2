package in.silive.techtrishnabeta2.network;

import in.silive.techtrishnabeta2.GetStringFromStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CheckNumberAndEmail {
	public int checknumer(String s) {
		URL httpUrl;
		try {
			httpUrl = new URL(
					"http://www.silive.in/tt15.rest/api/Student/IsPhoneNoAlreadyRegistered?phoneno="
							+ s);
			HttpURLConnection connection = (HttpURLConnection) httpUrl
					.openConnection();
			connection.connect();
			InputStream in = connection.getInputStream();

			GetStringFromStream gsfs = new GetStringFromStream();
			String data = gsfs.getString(in);
			Log.d("Reached Check number", "Check number");

			// JSONArray get = new JSONArray(data);
			JSONObject obj = new JSONObject(data);

			String message = obj.getString("IsPhoneNoAlreadyRegistered");
			if (message.contentEquals("true"))
				return 1;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int checkemail(String s) {
		URL httpUrl;
		try {
			httpUrl = new URL(
					"http://www.silive.in/tt15.rest/api/Student/IsEmailAlreadyRegistered?email="
							+ s);
			HttpURLConnection connection = (HttpURLConnection) httpUrl
					.openConnection();
			connection.connect();
			connection.setConnectTimeout(5000);
			InputStream in = connection.getInputStream();
			GetStringFromStream gsfs = new GetStringFromStream();
			String data = gsfs.getString(in);

			// JSONArray get = new JSONArray(data);
			JSONObject obj = new JSONObject(data);
			Log.d("Recaheched finaly check email", "Check email finished");
			String message = obj.getString("IsEmailAlreadyRegistered");
			Log.d("Email Already Registered",
					"Email Already Registere + message" + message);

			if (message.contentEquals("true"))
				return 1;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
