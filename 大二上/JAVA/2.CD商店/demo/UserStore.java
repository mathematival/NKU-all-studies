package demo;

public class UserStore {
	private User[] users = new User[100];
	
	public void print(int id){
		System.out.println(users[id]);
	}
	public void print(){
		for (User u : users) {
			if(u!=null){
				System.out.println(u);
			}
		}
	}
	public User find(int id){
		return users[id];
	}
	public void addMoney(int userId,int money){
		
	}
	public void remove(User u){
		
	}
	public void add(User u){
		//��Disk��һ����Disk���Ե��ӣ��û�ID������
	}
}
