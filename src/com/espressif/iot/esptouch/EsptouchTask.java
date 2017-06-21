package com.espressif.iot.esptouch;

import java.util.List;

import com.espressif.iot.esptouch.task.EsptouchTaskParameter;
import com.espressif.iot.esptouch.task.IEsptouchTaskParameter;
import com.espressif.iot.esptouch.task.__EsptouchTask;

public class EsptouchTask implements IEsptouchTask {

	public __EsptouchTask _mEsptouchTask;
	private IEsptouchTaskParameter _mParameter;

	/**
	 * Constructor of EsptouchTask
	 * 
	 * @param apSsid
	 *            the Ap's ssid
	 * @param apBssid
	 *            the Ap's bssid
	 * @param apPassword
	 *            the Ap's password
	 * @param isSsidHidden
	 *            whether the Ap's ssid is hidden
	 * @param context
	 *            the Context of the Application
	 */
	public EsptouchTask(String apSsid, String apBssid, String apPassword,boolean isSsidHidden) {
		_mParameter = new EsptouchTaskParameter();
		_mEsptouchTask = new __EsptouchTask(apSsid, apBssid, apPassword, _mParameter, isSsidHidden);
	}
	/**
	 * Constructor of EsptouchTask
	 * 
	 * @param apSsid
	 *            the Ap's ssid
	 * @param apBssid
	 *            the Ap's bssid
	 * @param apPassword
	 *            the Ap's password
	 * @param timeoutMillisecond
	 *            (it should be >= 15000+6000) millisecond of total timeout
	 * @param context
	 *            the Context of the Application
	 */
	public EsptouchTask(String apSsid, String apBssid, String apPassword, int timeoutMillisecond) {
		_mParameter = new EsptouchTaskParameter();
		_mParameter.setWaitUdpTotalMillisecond(timeoutMillisecond);
		_mEsptouchTask = new __EsptouchTask(apSsid, apBssid, apPassword, _mParameter, true);
	}

	@Override
	public void interrupt() {
		_mEsptouchTask.interrupt();
	}

	@Override
	public IEsptouchResult executeForResult() throws RuntimeException {
		return _mEsptouchTask.executeForResult();
	}

	@Override
	public boolean isCancelled() {
		return _mEsptouchTask.isCancelled();
	}

	@Override
	public List<IEsptouchResult> executeForResults(int expectTaskResultCount)
			throws RuntimeException {
		if (expectTaskResultCount <= 0) {
			expectTaskResultCount = Integer.MAX_VALUE;
		}
		return _mEsptouchTask.executeForResults(expectTaskResultCount);
	}

	@Override
	public void setEsptouchListener(IEsptouchListener esptouchListener) {
		_mEsptouchTask.setEsptouchListener(esptouchListener);
	}
}
