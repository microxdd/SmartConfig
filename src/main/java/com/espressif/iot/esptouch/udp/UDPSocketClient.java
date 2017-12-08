package com.espressif.iot.esptouch.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * this class is used to help send UDP data according to length
 * 
 * @author afunx
 * 
 */
public class UDPSocketClient {

	private static final String TAG = "UDPSocketClient";
	private DatagramSocket mSocket;
	private volatile boolean mIsStop;
	private volatile boolean mIsClosed;

	public UDPSocketClient() {
		try {
			this.mSocket = new DatagramSocket();
			this.mIsStop = false;
			this.mIsClosed = false;
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	public void interrupt() {
		LogUtils.debug( "USPSocketClient is interrupt");
		this.mIsStop = true;
	}

	/**
	 * close the UDP socket
	 */
	public synchronized void close() {
		if (!this.mIsClosed) {
			this.mSocket.close();
			this.mIsClosed = true;
		}
	}

	/**
	 * send the data by UDP
	 * 
	 * @param data
	 *            the data to be sent
	 * @param targetHost
	 *            the host name of target, e.g. 192.168.1.101
	 * @param targetPort
	 *            the port of target
	 * @param interval
	 *            the milliseconds to between each UDP sent
	 */
	public void sendData(byte[][] data, String targetHostName, int targetPort,
			long interval) {
		sendData(data, 0, data.length, targetHostName, targetPort, interval);
	}
	
	
	/**
	 * send the data by UDP
	 * 
	 * @param data
	 *            the data to be sent
	 * @param offset
	 * 			  the offset which data to be sent
	 * @param count
	 * 			  the count of the data
	 * @param targetHost
	 *            the host name of target, e.g. 192.168.1.101
	 * @param targetPort
	 *            the port of target
	 * @param interval
	 *            the milliseconds to between each UDP sent
	 */
	public void sendData(byte[][] data, int offset, int count,
			String targetHostName, int targetPort, long interval) {
//		LogUtils.debug(targetHostName);
		if ((data == null) || (data.length <= 0)) {
				LogUtils.debug( "sendData(): data == null or length <= 0");
			return;
		}
		for (int i = offset; !mIsStop && i < offset + count; i++) {
			if (data[i].length == 0) {
				continue;
			}
			try {
				// Log.i(TAG, "data[" + i + " +].length = " + data[i].length);
				DatagramPacket localDatagramPacket = new DatagramPacket(
						data[i], data[i].length,
						InetAddress.getByName(targetHostName), targetPort);
				this.mSocket.send(localDatagramPacket);
			} catch (UnknownHostException e) {
				e.printStackTrace();
				mIsStop = true;
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				LogUtils.debug(  "sendData is Interrupted");
				mIsStop = true;
				break;
			}
		}
		if (mIsStop) {
			close();
		}
	}
}
