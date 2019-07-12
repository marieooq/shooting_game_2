

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JApplet;

public class ShootGame extends JApplet implements KeyListener {
	public Gun gun = new Gun(87, 180);
	public Bullet bullet;
	public Enemy enemy;
	public static Thread t;
	BufferedImage bi;
	Graphics2D offs;
	int w, h;
	
	public void init() {
	   addKeyListener(this);
	   w = 700;
	   h = 700;
	   bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	   offs = (Graphics2D) bi.getGraphics();
	   offs.setBackground(Color.WHITE);
	   setFocusable(true);
	}
	
	public void start(){
	    t = new Thread(){
	    public void run(){
	      Thread ct = Thread.currentThread();
	       
	      while( t == ct ){
	        try {
	            Thread.sleep(10);
	            repaint();
	         } catch(Exception e) {
	            e.printStackTrace();
	         }
	      
	        }
	      }
	    };
	    t.start();
	  }
	
	public void paint(Graphics g) {
		//Graphics2D g2 = (Graphics2D) g;
		offs.clearRect(0,0,w,h);
		gun.move();
		gun.draw(offs);
		if ( bullet != null ) {
			if ( bullet.getBulletY() > -15 ) {
		        bullet.draw(offs);
		        bullet.move();
		      } else {
		        bullet = null;
		      }
	    }
		 if ( enemy != null ) {
	      if ( enemy.getEnemyX() > - 30) {
	        enemy.draw(offs);
	        enemy.move();
	      } else {
	        enemy = null;
	      }
	    } else {
	      enemy = new Enemy(200,10);
	    }
		g.drawImage(bi, 0, 0, null);
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	public void destroy() {
		t = null;
	 }
	
	
	
  public void keyReleased(KeyEvent e) {
	  if ( e.getKeyCode() == KeyEvent.VK_RIGHT || 
        e.getKeyCode() == KeyEvent.VK_LEFT ) {
      gun.setDirection(Gun.STAY_HERE);
    }

  }
  public void keyTyped(KeyEvent e) {
  }

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			gun.setDirection(Gun.RIGHT_MOVE);
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			gun.setDirection(Gun.LEFT_MOVE);
		}else if (e.getKeyCode() == KeyEvent.VK_SPACE ) {
			bullet = bullet == null ? new Bullet(gun.getGun().x, gun.getGun().y) : bullet;
	    }
		repaint();
	}
}


