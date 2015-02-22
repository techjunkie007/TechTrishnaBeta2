package in.silive.techtrishnabeta2;

import in.silive.techtrishnabeta2.network.GPSTracker;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class NavFragment extends Fragment {
	private LatLng AKGEC = new LatLng(28.67656, 77.50024);
	private GoogleMap map;
	private LatLng loc;
	private GMapV2Direction md;
	private Document doc;
	private GPSTracker gps;
	double lat, lng;
	View rootView;
	SupportMapFragment fragment;
	android.support.v4.app.FragmentTransaction fragTransaction;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.nav_layout, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		android.support.v4.app.FragmentManager fManager = getChildFragmentManager();
		fragment = (SupportMapFragment) fManager.findFragmentById(R.id.map);
		if (fragment == null) {
			fragment = SupportMapFragment.newInstance();
			fManager.beginTransaction().replace(R.id.map, fragment).commit();
		}

	}

	/*
	 * method to draw the map
	 */
	private void initializeMap() {
		if (map == null)
			map = ((SupportMapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

		gps = new GPSTracker(getActivity());

		// get the latitude and longitude co-ordinates
		lat = gps.getLatitude();
		lng = gps.getLongitude();
		loc = new LatLng(lat, lng);

		map.addMarker(new MarkerOptions().position(AKGEC).title("AKGEC"));
		map.addMarker(new MarkerOptions().position(loc).title("Starting Point"));

		// Move the camera instantly to akgec with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(AKGEC, 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

		map.setMyLocationEnabled(true);

		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			new MapDraw().execute();
		} else {
			map.addPolyline(new PolylineOptions().add(AKGEC, loc).width(4)
					.color(Color.BLACK));
		}
	}

	@Override
	public void onPause() {
		// SupportMapFragment f = (SupportMapFragment) getFragmentManager()
		// .findFragmentById(R.id.map);
		// if (f != null) {
		// getFragmentManager().beginTransaction().remove(f)
		// .commitAllowingStateLoss();
		// }
		super.onPause();
	}

	@Override
	public void onResume() {
		if (map == null) {
			map = fragment.getMap();
			map.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
		}
		initializeMap();
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		// SupportMapFragment f = (SupportMapFragment) getFragmentManager()
		// .findFragmentById(R.id.map);
		// if (f != null) {
		// getFragmentManager().beginTransaction().remove(f)
		// .commitAllowingStateLoss();
		// }
		super.onSaveInstanceState(bundle);
	}

	class MapDraw extends AsyncTask<String, String, String> {

		public MapDraw() {
		}

		@Override
		protected String doInBackground(String... params) {
			md = new GMapV2Direction();
			doc = md.getDocument(loc, AKGEC, GMapV2Direction.MODE_DRIVING);
			return null;
		}

		@Override
		protected void onPostExecute(String url) {
			ArrayList<LatLng> dPoint = md.getDirection(doc);
			PolylineOptions rectLine = new PolylineOptions().width(3).color(
					Color.BLUE);
			for (int i = 0; i < dPoint.size(); i++) {
				rectLine.add(dPoint.get(i));
			}
			map.addPolyline(rectLine);
		}
	}
}
