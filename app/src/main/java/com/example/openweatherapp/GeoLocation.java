//package com.example.openweatherapp;
//
//import android.content.Context;
//import android.location.Address;
//import android.location.Geocoder;
//import android.os.Bundle;
//import android.os.Message;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//import java.util.logging.Handler;
//
//public class GeoLocation {
//    public static void getAddress(String locationAddress, Context context , Handler handler){
//        Thread thread = new Thread(){
//            public void run(){
//                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//                String result = null;
//                try {
//                    List addressList = geocoder.getFromLocationName(locationAddress,1);
//                    if(addressList != null && addressList.size()>0){
//                        Address address = (Address) addressList.get(0);
//                        StringBuilder sb = new StringBuilder();
//                        sb.append(address.getLatitude()).append("\n");
//                        sb.append(address.getLongitude()).append("\n");
//                        result = sb.toString();
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    Message message = Message.obtain();
//                    message.setTarget(handler);
//                    if (result != null){
//                        message.what = 1;
//                        Bundle bundle = new Bundle();
//                        result = "Address :"+locationAddress + "\nLatitude & Longitude \n"+ result;
//                        bundle.putString("address",result);
//                        message.setData(bundle);
//
//                    }
//                    message.sendToTarget();
//
//                }
//
//            }
//        };
//        thread.start();
//    }
//}
