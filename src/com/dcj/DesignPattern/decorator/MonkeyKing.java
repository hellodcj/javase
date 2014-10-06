package com.dcj.DesignPattern.decorator;

import org.junit.Test;

/**
 * http://www.cnblogs.com/java-my-life/archive/2012/04/20/2455726.html的例子
 * @author chengjun
 *
 */
public class MonkeyKing {
	@Test
	 public void test(){
	        TheGreatestSage sage = new Monkey();
	        // 第一种写法
	        TheGreatestSage bird = new Bird(sage);
	        TheGreatestSage fish = new Fish(bird);
	        // 第二种写法
	        //TheGreatestSage fish = new Fish(new Bird(sage));
	        fish.move(); 
	    }
}

 interface TheGreatestSage {
    public void move();
}
 
  class Monkey implements TheGreatestSage {
	    @Override
	    public void move() {
	        System.out.println("Monkey Move");
	    }
	}
  
  class Change implements TheGreatestSage {
	    private TheGreatestSage sage;
	    
	    public Change(TheGreatestSage sage){
	        this.sage = sage;
	    }
	    @Override
	    public void move() {
	        sage.move();
	    }

	}
  
  class Fish extends Change {
	    
	    public Fish(TheGreatestSage sage) {
	        super(sage);
	    }

	    @Override
	    public void move() {
	        System.out.println("Fish Move");
	        super.move();
	    }
	}
  
  class Bird extends Change {
	    
	    public Bird(TheGreatestSage sage) {
	        super(sage);
	    }

	    @Override
	    public void move() {
	        // 代码
	        System.out.println("Bird Move");
	        super.move();
	    }
	}