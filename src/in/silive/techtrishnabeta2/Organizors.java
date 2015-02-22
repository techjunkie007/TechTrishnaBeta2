package in.silive.techtrishnabeta2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Organizors extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().setTitle("Team");
		View view = inflater.inflate(R.layout.organizors_layout, container,
				false);
		final Button bAshwin = (Button) view.findViewById(R.id.buttonAshwin);
		final Button bShalabh = (Button) view.findViewById(R.id.buttonTyagi);

		bAshwin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub.
				try {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"
							+ bAshwin.getContentDescription().toString()));
					callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(callIntent);
				} catch (Exception e) {
					Log.e("Error", e.toString());
				}
			}
		});

		bShalabh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"
							+ bShalabh.getContentDescription().toString()));
					callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(callIntent);
				} catch (Exception e) {
					Log.e("Error", e.toString());
				}
			}
		});
		return view;

	}
}
