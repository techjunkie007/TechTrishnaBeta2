package in.silive.techtrishnabeta2;

import in.silive.techtrishnabeta2.control.Control;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ContactUsFragment extends Fragment {
	ListView list;
	List<String> feed;
	private CardArrayAdapter cardArrayAdapter;
	String[] category;
	String[] description;
	String[] icon;
	Fragment fragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Control control = new Control(getActivity());
		category = control.getCategory(R.array.category);
		description = control.getCategory(R.array.description);
		icon = control.getCategory(R.array.pIcons);
		View rootView = inflater.inflate(R.layout.listview, container, false);
		list = (ListView) rootView.findViewById(R.id.card_listView);
		list.addHeaderView(new View(getActivity()));
		list.addFooterView(new View(getActivity()));
		cardArrayAdapter = new CardArrayAdapter(getActivity()
				.getApplicationContext(), R.layout.list_item_card);
		for (int i = 0; i < 3; i++) {
			Card card = new Card(category[i].toString(), "",
					description[i].toString(), "");
			cardArrayAdapter.add(card);
		}
		list.setAdapter(cardArrayAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				switch (arg2) {
				case 1:
					fragment = new Organizors();
					break;
				case 2:
					fragment = new Credits();
					break;
				case 3:
					fragment = new QueryFragment();
					break;

				}

				if (fragment != null) {
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager
							.beginTransaction()
							.setCustomAnimations(android.R.anim.slide_in_left,
									android.R.anim.slide_out_right)
							.replace(R.id.content_frame, fragment).commit();

				}
			}
		});

		return rootView;
	}
}
