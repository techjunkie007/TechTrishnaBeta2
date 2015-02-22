package in.silive.techtrishnabeta2;

import in.silive.techtrishnabeta2.network.JSONParsing;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewsFeed extends Fragment {
	ListView list;
	private ProgressDialog progressDialog;
	JSONArray collegesArray = null;
	private static String news_feed = "http://www.silive.in/tt15.rest/api/newsfeed/getnewsfeeds/1";
	String data, test[];
	public static String colleges[];
	String name;
	ArrayAdapter<String> adapter;
	private CardArrayAdapter cardArrayAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		getActivity().setTitle("NEWS FEED");
		View view = inflater.inflate(R.layout.listview, container, false);
		list = (ListView) view.findViewById(R.id.card_listView);
		list.addHeaderView(new View(getActivity()));
		list.addFooterView(new View(getActivity()));
		cardArrayAdapter = new CardArrayAdapter(getActivity()
				.getApplicationContext(), R.layout.list_item_card);

		// feed = new ArrayList<String>();
		Log.d("Log exception", "message :");
		try {
			new LoadAllColleges().execute();
		} catch (Exception e) {
			Log.d("Log exception", "message :" + e);
		}
		return view;
	}

	class LoadAllColleges extends AsyncTask<String, String, String> implements
			OnItemSelectedListener {
		int len;

		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("Loading news. Please wait...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			new ArrayList<NameValuePair>();
			JSONArray get;
			String title, time, event, feed;
			try {
				JSONParsing jsonparser = new JSONParsing();
				data = jsonparser.makeHttpRequestGET(news_feed);

				get = new JSONArray(data);
				Log.d("Json", "message " + get);
				len = get.length();
				for (int i = 0; i < len; i++) {
					JSONObject c = get.getJSONObject(i);
					event = c.getString("EventName");
					title = c.getString("Title");
					feed = c.getString("Feed");
					time = c.getString("TimeStamp");
					Card card = new Card(event, title, feed, time);
					cardArrayAdapter.add(card);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			progressDialog.dismiss();
			list.setAdapter(cardArrayAdapter);
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	}
}