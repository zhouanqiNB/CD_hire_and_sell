
public class UserBook {
	private User[] data = new User[10000];
	
	public void addUser(User u) {
		if(data[u.id]!=null)
			System.out.println("This id has been occupied.");
		data[u.id] = u;
	}
	public User findUser(int id) {
		return data[id];
	}
	public boolean removeUser(int id) {
		if(data[id]!=null) {
			data[id]=null;
			return true;
		}
		else {
			System.out.println("This id does not exist.");
			return false;
		}
	}
	public void print() {
		for (User user : data) {
			System.out.println(user);
		}
	}
	@Override
	public String toString() {
		String result = "";
		for (User user : data) {
			if(user!=null) {
				result += user+"\n\n";
			}
		}
		return result;
	}

}
