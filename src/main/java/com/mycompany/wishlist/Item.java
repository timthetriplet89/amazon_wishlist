package com.mycompany.wishlist;

public class Item {

	private int id;
        private int index;
        private String title;
	private String link;
        
        public Item(String title, String link) {
            this.setTitle(title);
            this.setLink(link);
        }
	
	public Item(int index, String title, String link) {
            this.setIndex(index);
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
        
        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
	
}
