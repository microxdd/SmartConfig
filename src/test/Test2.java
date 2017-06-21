package test;

import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.util.EspNetUtil;

public class Test2 {
	public static void main(String[] args) {
		String apSsid = "LEDE";

		//String apBssid = "8e:21:0a:d9:ab:b5";
		String apBssid = "00:00:00:00:00:00";
		
		
		String apPwd = "zwyysfsj";

		boolean isSsidHidden = false;// whether the Ap's ssid is hidden, it is
										// false usually

		
		System.out.println(EspNetUtil.getLocalInetAddress());
		IEsptouchTask task = new EsptouchTask(apSsid, apBssid, apPwd, isSsidHidden);
		
		IEsptouchResult rs = task.executeForResult();
		System.out.println(rs);
	}
}
