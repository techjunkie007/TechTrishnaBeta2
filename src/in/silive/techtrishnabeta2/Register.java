package in.silive.techtrishnabeta2;

import in.silive.techtrishnabeta2.network.CheckNumberAndEmail;
import in.silive.techtrishnabeta2.network.DialogBox;
import in.silive.techtrishnabeta2.network.JSONParser;
import in.silive.techtrishnabeta2.network.JSONParsing;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Register {
	EditText otherCollegeEditText;
	int collegePosition;
	View rootView;
	int e = 0, n = 0;
	private ActionBarActivity activity;
	private DialogBox alertBox;

	public static final String URL_NEW_COLLEGE_STRING = "http://192.168.56.1/techtrishna/get_new_college_id.php";
	public static final String URL_NEW_REGISTRATION_STRING = "http://www.silive.in/tt15.rest/api/Student/CreateStudent";

	String nameString, emailIdString, contactNoString, partnerIdString,
			studentNoString, passwordString, genderString, branchString,
			yearString, tShirtString;
	String accomodationString;

	JSONParser jsonParser = new JSONParser();
	List<NameValuePair> register = new ArrayList<NameValuePair>();
	public ProgressDialog progressDialog;

	public Register(View rootView, ActionBarActivity activity) {
		this.rootView = rootView;
		this.activity = activity;

	}

	public void newRegistration() {
		Spinner collegeSpinner = (Spinner) rootView
				.findViewById(R.id.collge_list);
		otherCollegeEditText = (EditText) rootView
				.findViewById(R.id.reg_OtherCollege);
		EditText nameEditText = (EditText) rootView.findViewById(R.id.reg_Name);
		EditText studentNoEditText = (EditText) rootView
				.findViewById(R.id.reg_StudentNo);
		EditText emailEditText = (EditText) rootView
				.findViewById(R.id.reg_Email);
		EditText contactNoEditText = (EditText) rootView
				.findViewById(R.id.reg_ContactNo);
		EditText partnerIdEditText = (EditText) rootView
				.findViewById(R.id.reg_PartnerId);
		EditText passwordEditText = (EditText) rootView
				.findViewById(R.id.reg_Password);
		// EditText confirmpasswordEditText = (EditText) rootView
		// .findViewById(R.id.reg_ConfirmPassword);
		RadioGroup genderRadioGroup = (RadioGroup) rootView
				.findViewById(R.id.reg_GenderGroup);
		Spinner branchSpinner = (Spinner) rootView
				.findViewById(R.id.branch_list);
		Spinner yearSpinner = (Spinner) rootView.findViewById(R.id.year_list);
		CheckBox tShirtCheckBox = (CheckBox) rootView
				.findViewById(R.id.reg_checkBox_TShirt);
		CheckBox accomodationCheckBox = (CheckBox) rootView
				.findViewById(R.id.reg_checkBox_Accomodation);

		nameString = nameEditText.getText().toString().trim();
		emailIdString = emailEditText.getText().toString().trim();
		contactNoString = contactNoEditText.getText().toString().trim();
		partnerIdString = partnerIdEditText.getText().toString().trim();
		if (!partnerIdString.equals("")) {
			partnerIdString = partnerIdString.substring(2);
		}

		studentNoString = studentNoEditText.getText().toString().trim();
		passwordString = passwordEditText.getText().toString().trim();

		int genderRadioButtonId = genderRadioGroup.getCheckedRadioButtonId();
		View radioButton = genderRadioGroup.findViewById(genderRadioButtonId);

		genderString = Integer.toString(genderRadioGroup
				.indexOfChild(radioButton) + 1);
		branchString = Integer
				.toString(branchSpinner.getSelectedItemPosition() + 1);
		yearString = Integer
				.toString(yearSpinner.getSelectedItemPosition() + 1);

		boolean tShirt = tShirtCheckBox.isChecked();
		Log.d("tshirt", "" + tShirt);

		if (tShirt) {
			tShirtString = "1";
		} else {
			tShirtString = "0";
		}

		boolean accomodation = accomodationCheckBox.isChecked();

		if (accomodation) {
			accomodationString = "1";
		} else {
			accomodationString = "0";
		}

		int len = RegisterFragment.colleges.length;
		collegePosition = collegeSpinner.getSelectedItemPosition();
		if (collegePosition == (len - 1)) {
			new GetNewCollegeId().execute();
		} else {
			collegePosition = RegisterFragment.collegeId[collegePosition];
			Log.d("Create Response", "" + collegePosition);
			new RegisterParticipant().execute();

		}

	}

	class GetNewCollegeId extends AsyncTask<String, String, String> {

		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(rootView.getContext());
			progressDialog.setMessage("Loading colleges. Please wait...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String otherCollege = otherCollegeEditText.getText().toString();
			params.add(new BasicNameValuePair("name", otherCollege));
			JSONObject json = jsonParser.makeHttpRequest(
					URL_NEW_COLLEGE_STRING, "POST", params);
			try {
				collegePosition = Integer.parseInt(json.getString("id"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			new RegisterParticipant().execute();
		}
	}

	class RegisterParticipant extends AsyncTask<String, String, String> {
		JSONObject json;
		JSONObject jsonsend = new JSONObject();

		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(rootView.getContext());
			progressDialog.setMessage("Registering You. Please wait...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			CheckNumberAndEmail cnae = new CheckNumberAndEmail();

			if (cnae.checkemail(emailIdString) == 1) {
				e = 1;
			} else if (cnae.checknumer(contactNoString) == 1) {
				n = 1;
			} else {
				try {

					jsonsend.put("Name", nameString);
					jsonsend.put("Email", emailIdString);
					jsonsend.put("ContactNo", contactNoString);
					jsonsend.put("PartnerId", "0");
					jsonsend.put("StudentNo", studentNoString);
					jsonsend.put("Password", passwordString);
					jsonsend.put("GenderId", genderString);
					jsonsend.put("BranchId", branchString);
					jsonsend.put("YearId", yearString);
					jsonsend.put("CollegeId", Integer.toString(collegePosition));
					jsonsend.put("IsT_ShirtRequired", tShirtString);
					jsonsend.put("IsAccomodationRequired", accomodationString);

					JSONParsing jsonParse = new JSONParsing();
					json = jsonParse.makeHttpRequestPOST(
							URL_NEW_REGISTRATION_STRING, jsonsend);
					Log.d("Registered", "Registered" + json.getString("TTId"));

				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Log.d("Registered", "Registered" + json);
			}
			return null;
		}

		protected void onPostExecute(String result) {
			progressDialog.dismiss();

			if (e == 1) {
				Log.d("Got ", "Got e=1");
				alertBox = new DialogBox(activity);
				alertBox.setTitle("          Error In Registration");
				alertBox.setBody("\n		Email You are using is already registered");
				alertBox.setButtonText("Cancel");
				alertBox.onCreateAlertBox();
			} else if (n == 1) {
				Log.d("Got ", "Got n=1");
				alertBox = new DialogBox(activity);
				alertBox.setTitle("          Error In Registration");
				alertBox.setBody("\n		Contact Number You are using is already registered");
				alertBox.setButtonText("Cancel");
				alertBox.onCreateAlertBox();

			} else {
				String tTIdString = "0000";
				try {
					Log.d("Registered", "Registered");

					tTIdString = json.getString("TTId");
					Log.d("Registered", "Registered");

				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (tTIdString.equals("0000")) {
					alertBox = new DialogBox(activity);
					alertBox.setTitle("          TECHTRISHNA ID");
					alertBox.setBody("\n	Your TechTrishna ID is TT"
							+ tTIdString + ".\n	Try to save it somewhere.");
					alertBox.setButtonText("Yes Go");
					alertBox.onCreateAlertBox();
				} else {
					alertBox = new DialogBox(activity);
					alertBox.setTitle("          TECHTRISHNA ID");
					alertBox.setBody("\n	Your TechTrishna ID is TT"
							+ tTIdString + ".\n	Try to save it somewhere.");
					alertBox.setButtonText("Cancel");
					alertBox.onCreateAlertBox();
				}
			}
		}

	}

}
