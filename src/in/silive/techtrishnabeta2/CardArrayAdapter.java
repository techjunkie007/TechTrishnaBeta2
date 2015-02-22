package in.silive.techtrishnabeta2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardArrayAdapter extends ArrayAdapter<Card> {
	private List<Card> cardList = new ArrayList<Card>();

	static class CardViewHolder {
		TextView title, feed, timeStamp, eventName;
	}

	public CardArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	@Override
	public void add(Card object) {
		cardList.add(object);
		super.add(object);
	}

	@Override
	public int getCount() {
		return this.cardList.size();
	}

	@Override
	public Card getItem(int index) {
		return this.cardList.get(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CardViewHolder viewHolder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			row = inflater.inflate(R.layout.list_item_card, parent, false);
			viewHolder = new CardViewHolder();
			viewHolder.title = (TextView) row.findViewById(R.id.title);
			viewHolder.eventName = (TextView) row.findViewById(R.id.eventName);
			viewHolder.feed = (TextView) row.findViewById(R.id.feed);
			viewHolder.timeStamp = (TextView) row.findViewById(R.id.timeStamp);
			row.setTag(viewHolder);
		} else {
			viewHolder = (CardViewHolder) row.getTag();
		}
		Card card = getItem(position);
		viewHolder.title.setText(card.title());
		viewHolder.eventName.setText(card.eventName());
		viewHolder.feed.setText(card.feed());
		viewHolder.timeStamp.setText(card.timeStamp());

		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory
				.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
}