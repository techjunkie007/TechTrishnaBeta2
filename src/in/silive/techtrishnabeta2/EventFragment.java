package in.silive.techtrishnabeta2;

import in.silive.techtrishnabeta2.adapter.ExpListAdapter;
import in.silive.techtrishnabeta2.control.Control;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class EventFragment extends Fragment {
	private String[] categories;
	int lastGroup = -1;
	private String[][] categoryItems;
	private ExpandableListView expandableListView;
	private ExpandableListAdapter listAdpater;

	public EventFragment() {
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// set the title of the fragment
		getActivity().setTitle("EVENTS");
		Control control = new Control(getActivity());
		categories = control.getCategory(R.array.eventCategory);
		categoryItems = control.getCategoryItems(R.array.onEvents,
				R.array.csEvents, R.array.ecEvents, R.array.rbEvents);
		View rootView = inflater.inflate(R.layout.event_layout1, container,
				false);
		// get the expandable list view
		expandableListView = (ExpandableListView) rootView
				.findViewById(R.id.eventListExp);
		listAdpater = new ExpListAdapter(categories, categoryItems,
				getActivity());
		expandableListView.setAdapter(listAdpater);
		expandableListView.setGroupIndicator(null);

		expandableListView
				.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {

						if (lastGroup != -1) {
							expandableListView.collapseGroup(lastGroup);
						}
						expandableListView.expandGroup(groupPosition);
						lastGroup = groupPosition;
						return true;

					}
				});
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Fragment fragment = new EventDetails();
				Bundle b = new Bundle();
				b.putString("eventName",
						categoryItems[groupPosition][childPosition]);
				fragment.setArguments(b);
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.content_frame, fragment).commit();
				return false;
			}
		});
		return rootView;
	}
}