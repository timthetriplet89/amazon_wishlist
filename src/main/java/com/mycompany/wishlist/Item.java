
public class Item {

	private int id;
	private String title;
	private String link;
	
	public Item(int id, String title, String link) {
		this.setId(id);
		this.setTitle(title);
		this.setLink(link);
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	private void setLink(String link) {
		this.link = link;
	}

	public String toString() {
		return "<a href=\"" + link + "\">" + title + "</a>";
	}
	
}
