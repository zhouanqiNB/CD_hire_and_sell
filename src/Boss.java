import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Boss {
	private UserBook ub = new UserBook();
	private DiskBook db = new DiskBook();
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	//老板的钱，这里指的是真正的收入，而不是用户充值的钱。设定某碟片的租借价格为出售价格的1/2
	private int money=0;
	private ArrayList<String> log=new ArrayList<String>();
	
	public static void main(String[] args) {
		Boss boss = new Boss();
		boss.begin();
	}
	public void begin() {
		while(true) {
			printMainMenu();
			int choice = readUserInput();
			switch (choice) {
			case 1:userManage();break;
			case 2:diskManage();break;
			case 3:userReturn();break;
			case 4:userBorrow();break;
			case 5:userBuy();break;
			case 6:seeMoney();break;
			case 7:seeLog();break;
			case 8:return;
			}
		}
	}
	private void userManage() {
		while(true) {
			printUserMenu();
			int choice = readUserInput();
			switch(choice) {
			case 1:	addUser();		break;
			case 2:	removeUser();	break;
			case 3:	findUser();		break;
			case 4:	showUserList();		break;
			case 6:	return;
			}
		}
	}
	private void diskManage() {
		// TODO Auto-generated method stub
		while(true) {
			printDiskMenu();
			int choice = readUserInput();
			switch(choice) {
			case 1:	addDisk();		break;
			case 2:	removeDisk();	break;
			case 3:	findDisk();		break;
			case 4:	showDiskList();		break;
			case 6:	return;
			}
		}
		
	}
	

	private void showDiskList() {
		System.out.println(db);
		
	}
	private void findDisk() {
		try {
			System.out.println("please tell me disk's id");
			int id = Integer.parseInt(in.readLine());
			Disk disk = db.findDisk(id);
			System.out.println(disk);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void removeDisk() {
		try {
			System.out.println("please tell me disk's id");
			int id = Integer.parseInt(in.readLine());
			if(db.findDisk(id)!=null) {
				db.removeDisk(id);
				log.add(" remove disk "+db.findDisk(id));
			}else {
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void addDisk() {
		try {
			System.out.println("please tell me disk's name");
			String name = in.readLine();
			System.out.println("please tell me disk's id");
			int id = Integer.parseInt(in.readLine());
			System.out.println("please tell me disk's price");
			int money = Integer.parseInt(in.readLine());
			System.out.println("please tell me disk's num");
			int num = Integer.parseInt(in.readLine());
			Disk disk = new Disk(id, name, money,num);
			db.addDisk(disk);
			
			log.add("add disk "+disk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void userReturn() {
		// TODO Auto-generated method stub
		try {
			System.out.println("please tell me user's id");
			int id = Integer.parseInt(in.readLine());
			User user = ub.findUser(id);
			if(user==null) {
				System.out.println("This User does not exist.");
				return;
			}
			System.out.println(user);
			
			//肯定是一起还同一个专辑
			System.out.println("please tell me disk's id");
			int id2 = Integer.parseInt(in.readLine());
			if(db.findDisk(id2)!=null) {
				int num=ub.findUser(id).getNote().findDisk(id2).getNum();
				int price=db.findDisk(id2).getPrice();
				Disk disk = new Disk(id2, db.findDisk(id2).getName(), price,num);
				db.addDisk(disk);
				DiskBook userDisk=user.getNote();
				userDisk.removeDisk(id2);
				
				log.add(user.getName()+" return "+num+" "+disk.getName());
			}
			else {
				System.out.println("This Disk does not exist.");
			}
			

			//加在交易记录里
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private void userBorrow() {
		// TODO Auto-generated method stub
		try {
			System.out.println("please tell me user's id");
			int id = Integer.parseInt(in.readLine());
			User user = ub.findUser(id);
			if(user==null) {
				System.out.println("This User does not exist.");
				return;
			}
			System.out.println(user);
			
			System.out.println("please tell me disk's id");
			int id2 = Integer.parseInt(in.readLine());
			Disk disk=db.findDisk(id2);
			System.out.println("please tell me disk's num");
			int num = Integer.parseInt(in.readLine());
			if(num==0)
				return;
			
			if(disk==null) {
				System.out.println("we don't have this kind of disk");
				return;
			}
			else if(disk.getNum()<num) {
				System.out.println("we don't have so many disks");
				return;
			}
			
			else {
				int n=disk.getNum();
				n-=num;//借出去num个CD
				disk.setNum(n);
				
				//我收钱
				money+=disk.getPrice()/2 * num;
				int um=user.getMoney();
				um-=disk.getPrice()/2 *num;
				user.setMoney(um);
				
				//用户的本子里加CD
				Disk userBorrow=new Disk(disk.getId(),disk.getName(),disk.getPrice(),num);
				DiskBook userDisk=user.getNote();
				userDisk.addDisk(userBorrow);
				
				System.out.println("ok");
				log.add(user.name+" borrow "+num+" "+disk.getName()+"; got money ￥"+disk.getPrice()/2*num);
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void userBuy() {
		//用户购买不写进去
		// TODO Auto-generated method stub
		try {
			System.out.println("please tell me user's id");
			int id = Integer.parseInt(in.readLine());
			User user = ub.findUser(id);
			if(user==null) {
				System.out.println("This User does not exist.");
				return;
			}
			System.out.println(user);
			
			System.out.println("please tell me disk's id");
			int id2 = Integer.parseInt(in.readLine());
			Disk disk=db.findDisk(id2);
			System.out.println("please tell me disk's num");
			int num = Integer.parseInt(in.readLine());
			if(num==0)
				return;
			
			if(disk==null) {
				System.out.println("we don't have this kind of disk");
				return;
			}
			else if(disk.getNum()<num) {
				System.out.println("we don't have so many disks");
				return;
			}
			else {
				int n=disk.getNum();
				n=n-num;//卖出去
				disk.setNum(n);
				
				money+=disk.getPrice()*num;
				int um=user.getMoney();
				um-=disk.getPrice()*num;
				user.setMoney(um);
				
				System.out.println("ok");
				log.add(user.getName()+" buy "+num+" "+disk.getName()+"; got money ￥"+disk.getPrice()*num);
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void seeMoney() {
		// TODO Auto-generated method stub
		System.out.println("your money"+money);
	}

	private void seeLog() {
		// TODO Auto-generated method stub
		for(String a:log) {
			System.out.println(a);
		}
	}
	
	
	private void addUser() {
		try {
			System.out.println("please tell me user's name");
			String name = in.readLine();
			System.out.println("please tell me user's id");
			int id = Integer.parseInt(in.readLine());
			System.out.println("please tell me user's money");
			int money = Integer.parseInt(in.readLine());
			User user = new User(id, name, money);
			ub.addUser(user);
			
			log.add("add user "+user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void removeUser() {
		// TODO Auto-generated method stub
		try {
			System.out.println("please tell me user's id");
			int id = Integer.parseInt(in.readLine());
			
			if(ub.findUser(id)!=null) {
				ub.removeUser(id);
				log.add("remove user "+ub.findUser(id));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void findUser() {
		try {
			System.out.println("please tell me user's id");
			int id = Integer.parseInt(in.readLine());
			User user = ub.findUser(id);
			System.out.println(user);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void showUserList() {
		System.out.println(ub);
	}
	
	
	private int readUserInput() {
		try {
			String line;
			line = in.readLine();
			return Integer.parseInt(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	

	private void printMainMenu() {
		// TODO Auto-generated method stub
		System.out.println("---------------------------");
		System.out.println("1:User Manage");
		System.out.println("2:Disk Manage");
		System.out.println("3:return");//用户来还
		System.out.println("4:borrow");//用户来借
		System.out.println("5:buy");//用户来买
		System.out.println("6:see my money");
		System.out.println("7:see my log");
		System.out.println("8:quit");
		
	}
	private void printUserMenu() {

		System.out.println("---------------------------");
		System.out.println("1:add user");
		System.out.println("2:remove user");
		System.out.println("3:find user");
		System.out.println("4:show user list");
		
		System.out.println("6:quit");
	}
	private void printDiskMenu() {
		System.out.println("---------------------------");
		System.out.println("1:add disk");
		System.out.println("2:remove disk");
		System.out.println("3:find disk");//用户来还
		System.out.println("4:show disk list");//用户来借
		System.out.println("6:quit");
		
	}
}
