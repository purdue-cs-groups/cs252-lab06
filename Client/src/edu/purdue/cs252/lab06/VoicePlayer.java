package edu.purdue.cs252.lab06;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class VoicePlayer implements  Runnable{
	
	private int sampleRate = 8000;
	private int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
	private DatagramSocket socket;
	private AudioTrack speaker;
	
	VoicePlayer(String server) {
		try {
			this.socket = new DatagramSocket(7901);

		} catch (SocketException e) {
			System.out.printf("Socket Exception!");
		}  
	}
	
	public void run() {
		//android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
		int minBuf = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat);
		speaker = new AudioTrack(AudioManager.STREAM_VOICE_CALL, sampleRate, channelConfig, audioFormat, minBuf, AudioTrack.MODE_STREAM);
		speaker.play();
		byte[] buffer = new byte[50];
		DatagramPacket dummyPacket = null;
		DatagramPacket packet;
		
		while(true) {
			try {
				final InetAddress serverAddr = InetAddress.getByName("lore.cs.purdue.edu");
				
				byte[] dummyBuf = new byte[1];
				dummyBuf[0] = 0;
				
				dummyPacket = new DatagramPacket(dummyBuf, dummyBuf.length, serverAddr, 7901);
				packet = new DatagramPacket(buffer, buffer.length);

				socket.send(dummyPacket);
				socket.receive(packet);

				buffer = packet.getData();
				speaker.write(buffer,0,buffer.length);
			} catch (Exception e) {
			}
		}
	}
}