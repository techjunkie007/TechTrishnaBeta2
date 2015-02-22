package in.silive.techtrishnabeta2;

import in.silive.techtrishnabeta2.network.JSONParser;
import in.silive.techtrishnabeta2.network.JSONParsing;

import java.io.InputStream;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

	public static String colleges[];
	public static int collegeId[];
	String branch[] = { "CS", "ECE", "EN", "ME", "IT", "EI", "CE", "MCA",
			"Other" };
	String year[] = { "1", "2", "3", "4" };
	String yearMCA[] = { "1", "2", "3" };
	InputStream in;
	String data;
	GetStringFromStream gsfs = new GetStringFromStream();
	private Spinner collegeSpinner, branchSpinner, yearSpinner;
	private View rootView;

	private static String url_all_collegesString = "http://www.silive.in/tt15.rest/api/college/getcolleges";

	private ProgressDialog progressDialog;
	JSONParser jsonParser = new JSONParser();
	JSONArray collegesArray = null;
	ArrayAdapter<String> yearAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.register_layout, container, false);

		// Get College List
		new LoadAllColleges().execute();
		// Setting up Spinner for branch
		// binding branch array to ArrayAdapter
		ArrayAdapter<String> branchAdapter = new ArrayAdapter<String>(
				getActivity(), R.layout.spinner_layout, branch);
		branchSpinner = (Spinner) rootView.findViewById(R.id.branch_list); // Selecting
																			// branch
																			// list
																			// spinner
		branchSpinner.setAdapter(branchAdapter);

		// Setting up Spinner for year
		// binding year array to ArrayAdapter
		yearAdapter = new ArrayAdapter<String>(getActivity(),
				R.layout.spinner_layout, year);
		yearSpinner = (Spinner) rootView.findViewById(R.id.year_list); // Selecting
																		// year
																		// list
																		// spinner
		yearSpinner.setAdapter(yearAdapter);
		yearSpinner.setDropDownWidth(300);
		// on click of register button
		Button registerButton = (Button) rootView.findViewById(R.id.reg_Button);
		registerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				ValidateRegistrartion obj = new ValidateRegistrartion();
				boolean validate = obj.validate(rootView);

				if (validate) {

					Register register = new Register(rootView,
							(ActionBarActivity) getActivity());
					register.newRegistration();

				} else {
					Toast.makeText(rootView.getContext(),
							"Please Fill Your Details Correctly",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		return rootView;
	}

	class LoadAllColleges extends AsyncTask<String, String, String> implements
			OnItemSelectedListener {

		int len;

		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog
					.setMessage("Loading list of colleges\n Please wait...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			new ArrayList<NameValuePair>();
			try {
				JSONParsing jsonparser = new JSONParsing();
				data = jsonparser.makeHttpRequestGET(url_all_collegesString);
				JSONArray get = new JSONArray(data);
				len = get.length();
				collegeId = new int[len + 1];
				colleges = new String[len + 1];
				for (int i = 0; i < len; i++) {
					JSONObject c = get.getJSONObject(i);
					String name = c.getString("CollegeName");
					String id = c.getString("CollegeId");
					colleges[i] = name;
					collegeId[i] = Integer.parseInt(id);
				}
				colleges[len] = "Other";

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Log.d("get json object", "Got Exception");

			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			ArrayAdapter<String> collegeAdapter = new ArrayAdapter<String>(
					getActivity(), R.layout.spinner_layout, colleges);

			collegeSpinner = (Spinner) rootView.findViewById(R.id.collge_list); // Selecting
																				// college

			branchSpinner.setDropDownWidth(300);
			collegeSpinner.setAdapter(collegeAdapter);// Binding data to adapter
			collegeSpinner.setOnItemSelectedListener(this);
			branchSpinner.setOnItemSelectedListener(this);
			collegeSpinner.setHorizontalScrollBarEnabled(true);
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			int position = collegeSpinner.getSelectedItemPosition();
			EditText studentNoEditText = (EditText) rootView
					.findViewById(R.id.reg_StudentNo);
			// View studentNoTextView = rootView
			// .findViewById(R.id.textview_StudentNo);
			EditText partnerIdEditText = (EditText) rootView
					.findViewById(R.id.reg_PartnerId);
			CheckBox accomodationCheckBox = (CheckBox) rootView
					.findViewById(R.id.reg_checkBox_Accomodation);
			// View otherCollegeTextBox = rootView
			// .findViewById(R.id.textview_OtherCollege);
			EditText otherCollegeEditText = (EditText) rootView
					.findViewById(R.id.reg_OtherCollege);

			// if selected college is not AKGEC
			if (position != 0) {
				accomodationCheckBox.setVisibility(View.VISIBLE);// Accomodation
																	// visibilty
																	// true

				// studentNoTextView.setVisibility(View.GONE);// Student Number
				// Visibility false
				studentNoEditText.setVisibility(View.GONE);
				studentNoEditText.setText("");
				partnerIdEditText.setVisibility(View.VISIBLE);
				// partnerIdTextView.setVisibility(View.VISIBLE);
			} else {
				accomodationCheckBox.setVisibility(View.GONE);// Accomodation
				accomodationCheckBox.setChecked(false); // visibilty
														// true
														// studentNoTextView.setVisibility(View.VISIBLE);//
														// Student Number
				// Visibility //
				// false
				studentNoEditText.setVisibility(View.VISIBLE);

				partnerIdEditText.setVisibility(View.GONE);
				partnerIdEditText.setText("");
				// partnerIdTextView.setVisibility(View.GONE);
			}

			int max = colleges.length;

			if (position == (max - 1)) // if "other" college is chosen
			{
				otherCollegeEditText.setVisibility(View.VISIBLE);
				// otherCollegeTextBox.setVisibility(View.VISIBLE);
			} else // if Other college is not chosen
			{
				otherCollegeEditText.setVisibility(View.GONE);
				otherCollegeEditText.setText("");
				// otherCollegeTextBox.setVisibility(View.GONE);
			}
			String branch = (String) branchSpinner.getSelectedItem();

			if (branch.equals("MCA")) {
				yearAdapter = new ArrayAdapter<String>(getActivity(),
						R.layout.spinner_layout, yearMCA);
			} else {
				yearAdapter = new ArrayAdapter<String>(getActivity(),
						R.layout.spinner_layout, year);
			}
			yearSpinner.setAdapter(yearAdapter);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}

	}
}
