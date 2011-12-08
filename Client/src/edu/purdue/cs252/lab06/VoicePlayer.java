package edu.purdue.cs252.lab06;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class VoicePlayer implements  Runnable{
	
	private int sampleRate = 8000;
	private int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
	private DatagramSocket socket;
	private AudioTrack speaker;
	private int port;
	
	public static boolean play = true; 
	
	VoicePlayer(String server, int _port) {
		try {
			port = _port;
			this.socket = new DatagramSocket(port);

		} catch (SocketException e) {
			System.out.printf("Socket Exception!");
		}  
	}
	
	public void run() {
		//android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
		int minBuf = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat);
		speaker = new AudioTrack(AudioManager.STREAM_VOICE_CALL, sampleRate, channelConfig, audioFormat, minBuf, AudioTrack.MODE_STREAM);
		speaker.play();
		byte[] buffer = new byte[1024];
		DatagramPacket dummyPacket = null;
		DatagramPacket packet;
		InetAddress serverAddr = null;
		try {
			serverAddr = InetAddress.getByName("lore.cs.purdue.edu");
		} catch (UnknownHostException e1) {
		}
		byte[] dummyBuf = new byte[1];
		dummyBuf[0] = 0;
		dummyPacket = new DatagramPacket(dummyBuf, dummyBuf.length, serverAddr, port);
		
		while(play) {
			try {
				socket.send(dummyPacket);
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);

				buffer = packet.getData();
				speaker.write(buffer,0,buffer.length);
			} catch (Exception e) {
			}
		}
		
		speaker.stop();
		speaker.release();
		socket.close();
	}
}