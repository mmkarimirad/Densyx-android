package com.bs.dental;

import android.content.Context;
import android.provider.Settings.Secure;

public class DeviceUniqueIdentifier {
	
	Context context;
	
	public DeviceUniqueIdentifier(Context context)
	{
		this.context=context;
	}
	
	public static String getDeviceID(Context context)
	{
		
		String deviceId;

		/*final TelephonyManager mTelephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		if (mTelephony.getDeviceId() != null && !mTelephony.getDeviceId().equals("000000000000000")) {
		    deviceId = mTelephony.getDeviceId();
		}
		else {
		    deviceId = Secure.getString(
		    		context.getContentResolver(),
		                   Secure.ANDROID_ID);
		}*/
		deviceId = Secure.getString(
				context.getContentResolver(),
				Secure.ANDROID_ID);
		return deviceId;
		
		
	}
	
	public  String getDeviceID()
	{
		
    getDeviceID(context);		
	return null;
	}

}
