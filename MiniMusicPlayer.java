import javax.sound.midi.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class MiniMusicPlayer{
	static JFrame f=new JFrame("Music Player");
	static MyDrawPanel m1;
	public static void main(String[] args){
		MiniMusicPlayer mini =new MiniMusicPlayer();
		mini.go();
	}
	public void SetUpGUI(){
		m1 = new MyDrawPanel();
		f.setContentPane(m1);
		f.setBounds(30,30,300,300);
		f.setVisible(true);
	}
	public void go(){
		SetUpGUI();

		try{
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addControllerEventListener(m1,new int[]{127});
			Sequence seq = new Sequence(Sequence.PPQ,4);
			Track track = seq.createTrack();
			int r=0;
			for(int i=50;i<400;i+=4){
				track.add(makeEvent(144,1,i,100,i));
				track.add(makeEvent(128,1,i,100,i+2));
				r =(int)((Math.random()*50)+10);
				track.add(makeEvent(144,1,r,100,i));
				track.add(makeEvent(176,1,127,0,i));
				track.add(makeEvent(128,1,r,100,i+2));
			}
			sequencer.setSequence(seq);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		}catch(Exception ex){ex.printStackTrace();}
	}
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
			}catch(Exception e) { }
		return event;
	} // close method
	class MyDrawPanel extends JPanel implements ControllerEventListener {
		boolean msg = false;
		public void controlChange(ShortMessage event) {
			msg = true;
			repaint();
		}
		public void paintComponent(Graphics g) {
		if (msg) {
			Graphics2D g2 = (Graphics2D) g;
			int r = (int) (Math.random() * 250);
			int gr = (int) (Math.random() * 250);
			int b = (int) (Math.random() * 250);
			g.setColor(new Color(r,gr,b));
			int ht = (int) ((Math.random() * 120) + 10);
			int width = (int) ((Math.random() * 120) + 10);
			int x = (int) ((Math.random() * 40) + 10);
			int y = (int) ((Math.random() * 40) + 10);
			g.fillRect(x,y,ht, width);
			g.fillOval(x,y,ht,width);
			msg = false;
			} // close if
		} // close method
	} // close inner class
}