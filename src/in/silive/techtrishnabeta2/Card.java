package in.silive.techtrishnabeta2;

public class Card {
	private String title, feed, timeStamp, eventName;

	public Card(String eventName, String title, String feed, String timeStamp) {

		this.eventName = eventName;
		this.title = title;
		this.feed = feed;
		this.timeStamp = timeStamp;
	}

	public String title() {
		return title;
	}

	public String eventName() {
		return eventName;
	}

	public String feed() {
		return feed;
	}

	public String timeStamp() {
		return timeStamp;
	}

}