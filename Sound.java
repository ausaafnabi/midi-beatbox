import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class SimpleGui implements ActionListener{
	JButton button1;
	public void GUI1(){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
	   button1 = new JButton("sequence Starter!");
		button1.addActionListener(this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.add(button1);
		frame.getContentPane().add(BorderLayout.EAST,panel);
		frame.setSize(500,500);
		frame.setVisible(true);
		//button1.setVisible(true);
	}
	public void actionPerformed(ActionEvent event){
		button1.setText("I've been clicked");
	}
}
public class Sound{
	public void play(){
		try{
			Sequencer sequencer = MidiSystem.getSequencer();
			System.out.println("succesully got a sequencer");		
			sequencer.open();
			Sequence seq = new Sequence(Sequence.PPQ,4);
			Track track = seq.createTrack();
			for(int i=5;i<61;i+=4){
				track.add(makeEvent(144,1,i,100,i));
				track.add(makeEvent(128,1,i,100,i+2));
			}
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(220);
			sequencer.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}
	public static MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
		MidiEvent event = null;
		try{
			ShortMessage a= new ShortMessage();
			a.setMessage(comd,chan,one,two);
			event = new MidiEvent(a,tick);
		}catch(Exception e){ }
		return event;
	}

	public static void main(String[] argv)
	{	SimpleGui frame = new SimpleGui();
		frame.GUI1();
		Sound mt =new Sound();
		mt.play();
	}
}