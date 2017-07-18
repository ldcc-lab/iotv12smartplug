package kr.co.ldcc.iot.onem2m.iwf.example;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comus.wp.onem2m.iwf.common.M2MException;
import comus.wp.onem2m.iwf.nch.NotifyResponse;
import comus.wp.onem2m.iwf.run.CmdListener;
import comus.wp.onem2m.iwf.run.IWF;

public class SmartPlug {
	private static final Logger logger = LoggerFactory.getLogger(SmartPlug.class);
	
	
	
	public static void main(String[] args) throws IOException, M2MException, InterruptedException {
		// TODO Auto-generated method stub
		IWF smartPlug = new IWF("0001000100010003_fan");
		smartPlug.register();
		Runtime rt= Runtime.getRuntime();
		smartPlug.addCmdListener(new CmdListener() {
			@Override
			public void excute(Map<String, String> cmd, NotifyResponse resp) {
				// TODO Auto-generated method stub
				String value = null;
				value = (String)cmd.get("switch");
				if(value.equals("1")){
					logger.debug("Received [1] : TURN ON THE SWTICH");
					try {
						rt.exec("gpio write 0 1");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(value.equals("0")){
					logger.debug("Received [0] : TRUN OFF THE SWITCH");
					try {
						rt.exec("gpio write 0 0");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					logger.error("1 or 0 not exists");
				}
				smartPlug.putContent("controller-switch", "text/plain:0", value);
	        }
		});
	}
	
	
	
}