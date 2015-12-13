package clock;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;

public class clock {
	public static void main(String[] args) {

		JFrame jf = new JFrame("计数器");
		JButton pause = new JButton("Pause");
		JLabel clock = new JLabel("Timer");

		clock.setBackground(Color.GREEN);
		clock.setOpaque(true);
		clock.setHorizontalAlignment(JLabel.CENTER);
		jf.add(clock, "Center");
		jf.add(pause, "North");
		jf.setSize(140, 80);
		jf.setLocation(500, 300);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);

		MyThread mt = new MyThread(clock, 100000);
		mt.start();
		MyListener ml = new MyListener(clock, mt);
		pause.addActionListener(ml);
	}
}

class MyThread extends Thread {
	private JLabel clock;
	private long time;
	private long end;

	public MyThread(JLabel clock, long time) {
		this.clock = clock;
		this.time = time;
	}

	public void init() {
		long start = new Date().getTime();
		end = start + time;
	}

	public void run() {
		init();
		while (true) {
			long now = new Date().getTime();
			time = end - now;
			if (time > 0) {
				String s = this.convert(time);
				clock.setText(s);
			} else {
				break;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		clock.setText("时间到！");
		clock.setBackground(Color.RED);
	}

	public String convert(long time) {
		long h = time / 3600000;
		long m = (time % 3600000) / 60000;
		long s = (time % 60000) / 1000;
		long ms = (time % 1000) / 10;

		String ph = h < 10 ? "0" : "";
		String pm = m < 10 ? "0" : "";
		String ps = s < 10 ? "0" : "";
		String pms = ms < 10 ? "0" : "";

		String txt = ph + h + ":" + pm + m + ":" + ps + s + "." + pms + ms;
		return txt;
	}
}

class MyListener implements ActionListener {
	private JLabel clock;
	private MyThread mt;
	private boolean running = true;

	public MyListener(JLabel clock, MyThread mt) {
		this.clock = clock;
		this.mt = mt;
	}

	public void actionPerformed(ActionEvent e) {
		if (!mt.isAlive()) {
			return;
		}
		JButton jb = (JButton) e.getSource();
		if (running) {
			jb.setText("Replay");
			clock.setBackground(Color.YELLOW);
			mt.suspend();
		} else {
			jb.setText("Pause");
			clock.setBackground(Color.GREEN);
			mt.init();
			mt.resume();
		}
		running = !running;
	}
}