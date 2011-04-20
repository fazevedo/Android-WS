package com.example.webservice;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

public class WebService extends Activity implements OnClickListener {
	private static final String SOAP_ACTION = "getPatientList";
	private static final String METHOD_NAME = "getPatientList";
	private static final String NAMESPACE = "http://ws/";
	private static final String URL = "http://192.168.1.7:35458/PermedWS/pertmendService?wsdl";
	private SoapObject resultRequestSOAP = null;
	
	TextView tv = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button =  (Button)findViewById(R.id.btnSend);
        button.setOnClickListener(this);
        tv = (TextView)findViewById(R.id.tvResult);
    }
    
    public void onClick(View v){
    	AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(URL);
    	
    	try{
    		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
    		request.addProperty("login", "Dr. Gregory House");
    		
    		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    		envelope.setOutputSoapObject(request);
    		
    		androidHttpTransport.call(SOAP_ACTION, envelope);
    		
    		resultRequestSOAP = (SoapObject)envelope.bodyIn;
    		
    		SoapPrimitive result = (SoapPrimitive)envelope.getResponse();
    		
    		String tmp = result.toString();
    		
    		tv.setText(tmp);
    	} catch(Exception e){
    		e.printStackTrace();
    		tv.setText(e.toString());
    	}
    }
}