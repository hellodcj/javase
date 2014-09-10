package com.dcj.teacher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 戴成骏，陈新光
 * @date 2014.09.10
 */
public class TheachersDay {
	public static void main(String[] args) {
		Advisor advisor = new Advisor("伍星");
		Student s1 = new Student("大师兄--陈新光");
		Student s2 = new Student("二师兄--戴成骏");
		//开山大弟子
		RelationShip r1 = new RelationShip(advisor,
		s1, new Date("2009年的那个秋天"){}, "在虎溪的校园内，生命中多了一位良师益友！");
		//开山二弟子
		RelationShip r2 = new RelationShip(advisor,
		s2, new Date("2013年的那个春天") {}, "在邱钊老师的引荐下，我们的人生从此有了交集！");
		Wish wish = Wish.getInstance();
		//祝福太多，只能列举一二
		wish.addWish("祝老师身体健康，万事如意！");
		wish.addWish("祝老师事业顺利，桃李满天下！");
		wish.addWish("老师辛苦了，中秋节、教师节双节快乐哟！");
		//祝福就要大声说出来
		wish.sayWish();
	}
}

class Advisor {
	public Advisor(String string) {
		// TODO Auto-generated constructor stub
	}

	private String name;
	//各种属性
	//setter和getter方法
}

class Student {
	public Student(String string) {
		// TODO Auto-generated constructor stub
	}

	private String name;
	//各种属性
	//setter和getter方法
}

class RelationShip{
	private Advisor advisor;
	private Student student;
	private Date date;	//相识时间
	private String info; //备注
	public RelationShip(Advisor advisor, Student student
			, Date date, String info) {
		super();
		this.advisor = advisor;
		this.student = student;
		this.date = date;
		this.info = info;
	}
}

/**
 * 单例模式，学生们共同的祝福！
 * 
 */
class Wish {
	private List<String> wishlist = new ArrayList<String>();
	private Wish() {} //私有的构造方法
	private static Wish instance = new Wish();
	public static Wish getInstance() {
		return instance;
	}
	public void addWish(String wish){
		wishlist.add(wish);
	}
	public void sayWish(){
		for (String wish:wishlist){
			System.out.println(wish);
		}
	}
}