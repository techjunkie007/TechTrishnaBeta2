package in.silive.techtrishnabeta2;

import in.silive.techtrishnabeta2.network.DialogBox;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IdFragment extends Fragment {
	private ProgressDialog progressDialog;
	private TextView setIdText;

	private DialogBox dialogBox;

	private static String idUrl = "http://www.silive.in/tt15.rest/api/Student/GetTTIdByEmail?email=";
	private String message="";
	private String emailText;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.id_layout, container,
				false);
		getActivity().setTitle("GET YOUR TT-ID");
		setIdText = (TextView) rootView.findViewById(R.id.setIdText);
		setIdText.setVisibility(View.GONE);
		
		Button getIdButton = (Button) rootView.findViewById(R.id.getIdButton);
		getIdButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText getEmailIdText = (EditText) rootView
						.findViewById(R.id.getEmailIdText);
				emailText = getEmailIdText.getText().toString();
				new GetId().execute();
			}
		});
		return rootView;
	}

	private class GetId extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("Sendig Email Id. Please wait...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			Log.d("Final URL","URK is  "+(idUrl+emailText));
			try {URL httpUrl = new URL(idUrl+emailText);
			HttpURLConnection connection = (HttpURLConnection) httpUrl
					.openConnection();
			connection.connect();
			connection.setConnectTimeout(5000);
			InputStream in = connection.getInputStream();
			GetStringFromStream gsfs=new GetStringFromStream();
			String data = gsfs.getString(in);

			
				//JSONArray get = new JSONArray(data);
				JSONObject obj=new JSONObject(data);
			
				message=obj.getString("TTId");
				Log.d("Final String","String is  "+message);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return message;
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			if (message.contentEquals("0")) {
				dialogBox = new DialogBox((ActionBarActivity) getActivity(), null, null,1);
				dialogBox.setTitle("REGISTER FIRST");
				dialogBox
						.setBody("It seems that you are not registered for TechTrishna. Do you want to register for TechTrishna 2014?");
				dialogBox.setButtonText("Yes Let's Go");
				dialogBox.onCreateDialogBox();
			} else {
				setIdText.setText(result);
				dialogBox = new DialogBox((ActionBarActivity) getActivity(), null, null,1);
				dialogBox.setTitle("TTID");
				dialogBox
						.setBody("      	Your TTID ID is \n           TT"+message);
				dialogBox.onCreateDialogBox();
	dialogBox.setButtonText("OK");
			}
		}
	}

}
