import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Wishlist {
	List<Item> items;
	int userID;
	
	public Wishlist(int userID) {
		this.userID = userID;
		this.items = new ArrayList<Item>();
		try {
			Database db = new Database();
			fillList(db.query("SELET * FROM UserItems AS ui INNER JOIN Items AS i ON ui.item_id = i.id WHERE user_id = " + userID));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void fillList(ResultSet rs) throws SQLException{
		while(rs.next()) {
			items.add(new Item(rs.getInt("i.id"), rs.getString("i.name"), rs.getString("i.url")));
		}
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void addItem(String name, String url) {
		int id;
		try {
			Database db = new Database();
			List<String> cols = new ArrayList<>();
			cols.add("name");
			cols.add("url");
			List<String> vals = new ArrayList<>();
			vals.add(name);
			vals.add(url);
			id = db.find("Items", cols, vals);
			if(id == 0) {
				Statement st = db.getStatement();
				st.executeUpdate("INSERT INTO Items (name, url) VALUES (\"" + name + "\", \"" + url + "\")", Statement.RETURN_GENERATED_KEYS);
				ResultSet genKeys = st.getGeneratedKeys();
				genKeys.next();
				id = genKeys.getInt("id");
			}
			
			Item item = new Item(id, name, url);
			items.add(item);
			db.query("INSERT INTO UserItems (user_id, item_id) VALUES (\"" + userID + "\", \"" + id + "\")");
		}
		catch(Exception error) {
			error.printStackTrace();
		}
	}
}
