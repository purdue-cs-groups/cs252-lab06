package edu.purdue.cs252.lab06;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;


public class VoiceRecorder implements Runnable {
	private int sampleRate = 8000;
	private int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
	private DatagramSocket socket;
	static boolean onCall = false;
	int port;
	
	public static boolean record = true; 

	VoiceRecorder(String server, int _port) {
		try {
			port = _port;
			this.socket = new DatagramSocket(port);
	 
			
		} catch (SocketException e) { System.out.println("constructor " + e.getMessage());	}  
	}
	
	public void run(){
		try {
			//android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
			
			DatagramPacket packet;
			int minBuf = AudioRecord.getMinBufferSize(sampleRate,channelConfig,audioFormat);
			AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,channelConfig,audioFormat,minBuf); 
			recorder.startRecording();
			
			while(record) {
				try {
					
					byte[] buffer = new byte[1024] ;
					recorder.read(buffer,0,buffer.length);
					packet = new DatagramPacket(buffer,buffer.length,InetAddress.getByName("lore.cs.purdue.edu"),port);
	
					socket.send(packet);
				} catch (Exception e) {
					System.out.println("in voice recorder run() " + e.getMessage());
					
				}
			}
			
			recorder.stop();
			recorder.release();
			socket.close();
		} catch (Exception e1) {
			System.out.println("voice recorder: " + e1.getMessage());
		}
	}
}