package in.silive.techtrishnabeta2;

import in.silive.techtrishnabeta2.network.JSONParser;
import in.silive.techtrishnabeta2.network.JSONParsing;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class QueryFragment extends Fragment {

	private static String queyUrl = "http://www.silive.in/tt15.rest/api/Student/SendQuery";
	private ProgressDialog progressDialog;
	// private static String TAG_VALUE = "value";
	// private static String TAG_MESSAGE = "message";
	// private String message = "";
	private String emailText;
	private String queryText;
	private String queryTag;
	EditText emailEditText;
	EditText queryEditText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.query_layout,
				container, false);
		getActivity().setTitle("QUERY US"); // set the title of the fragment
		Button submitButton = (Button) rootView.findViewById(R.id.queryButton);
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				emailEditText = (EditText) rootView
						.findViewById(R.id.emailText);
				queryEditText = (EditText) rootView
						.findViewById(R.id.queryText);
				RadioGroup queryRadioGroup = (RadioGroup) rootView
						.findViewById(R.id.radioQuery);
				int selectedId = queryRadioGroup.getCheckedRadioButtonId();
				RadioButton queryRadioButton = (RadioButton) rootView
						.findViewById(selectedId);
				queryTag = queryRadioButton.getTag().toString();
				emailText = emailEditText.getText().toString().trim();
				queryText = queryEditText.getText().toString().trim();
				Log.d("queryTag", queryTag);
				new SubmitQuery().execute();
			}
		});
		return rootView;
	}

	private class SubmitQuery extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("Sendig Query. Please wait...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			new JSONParser();
			JSONObject jsonSend = new JSONObject();
			// list to send the values
			// List<NameValuePair> args = new ArrayList<NameValuePair>();
			// args.add(new BasicNameValuePair("To", emailText));
			// args.add(new BasicNameValuePair("Body", queryText));
			// args.add(new BasicNameValuePair("tag", queryTag));
			// add the JSON object
			// JSONObject json = jsonParser.makeHttpRequest(queyUrl, "POST",
			// args);
			// try {
			// Log.d("tag", json.toString());
			// message = json.getString(TAG_MESSAGE);
			// } catch (JSONException e) {
			// e.printStackTrace();
			// }

			try {

				jsonSend.put("To", emailText);
				jsonSend.put("Body", queryText);
				JSONParsing jsonParse = new JSONParsing();
				jsonParse.makeHttpRequestPOST(queyUrl, jsonSend);
				Log.d("Sent", "Successfully Send");

			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
		}
	}
}
