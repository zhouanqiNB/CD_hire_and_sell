import java.util.Arrays;
import java.util.LinkedList;

public class DiskBook {
	// list of disk
	private Disk[] data = new Disk[1000];
	
	public void addDisk(Disk d) {
		int id = d.getId();
		Disk disk = findDisk(id);
		if(disk==null) {
			// first disk of this kind
			data[id]=d;
		}else {
			// if exist, this kind disk_num++
			int num = disk.getNum();
			num = num+d.getNum();
			disk.setNum(num);
		}
		
	}
	public void print() {
		for (Disk disk : data) {
			System.out.println(disk);
		}
	}
	public boolean removeDisk(int id) {
		Disk disk = findDisk(id);
		// did not find this id
		if(disk==null) {
			return false;
		}else {
			disk=null;
			return true;
		}
	}
	public Disk findDisk(int id) {
		return data[id];
	}
	@Override
	public String toString() {
		String result = "";
		for (Disk disk : data) {
			if(disk!=null) {
				result += disk+"\n";
			}
		}
		return result;
//		return "DiskBook [data=" + Arrays.toString(data) + "]";
	}

}
