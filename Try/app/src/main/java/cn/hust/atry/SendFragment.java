package cn.hust.atry;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 71084 on 2018/12/8.
 */

public class SendFragment extends Fragment {

    private EditText mEditText;
    private Button mButton;
    private TextView mCityView;
    private TextView mJsonView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3,container,false);
        mEditText = (EditText) view.findViewById(R.id.tab3_input);
        mButton = (Button) view.findViewById(R.id.tab3_button);
        mCityView = (TextView) view.findViewById(R.id.tab3_city_info);
        mJsonView = (TextView) view.findViewById(R.id.tab3_json_info);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mEditText.getText().toString();
                if(number.length()==0){
                    Toast.makeText(getActivity(),
                            "the number is null", Toast.LENGTH_SHORT)
                            .show();
                    Log.d("communication","empty number");
                }
                else if(number.length() != 11){
                    Toast.makeText(getActivity(),
                            "bad number format", Toast.LENGTH_SHORT)
                            .show();
                    Log.d("communication","bad number:"+number);
                }
                else{
                    //new a thread to do http things...

                    new Fetch().execute(number);

                    Toast.makeText(getActivity(),
                            "searching for "+number, Toast.LENGTH_SHORT)
                            .show();
                    Log.d("communication","searching number :  "+number);
                }
            }
        });
        return view;

    }

    private class Fetch extends AsyncTask<String,Void,Map<String,String>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCityView.setText(R.string.loading);
            mJsonView.setText(R.string.loading);
        }

        @Override
        protected Map<String,String> doInBackground(String... phoneNum) {
            try {
                String cityinfo = SendThread.getRemoteInfo(phoneNum[0],"");
                int pos = cityinfo.indexOf(phoneNum[0]);
                String subcityinfo = cityinfo.substring(pos,cityinfo.length()-9);//截取汉字部分

                String location = subcityinfo.split(" ")[1];
                GetWeather weather = new GetWeather();
                String url = weather.generateURL(location);
                String jsonInfo = weather.getJsonFromUrl(url);

                Map<String,String> result = new HashMap<>();
                result.put("cityinfo",subcityinfo);
                result.put("jsoninfo",jsonInfo);

                return result;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Map<String,String> result) {
            super.onPostExecute(result);
            if(result.containsKey("cityinfo"))mCityView.setText(result.get("cityinfo"));
            if(result.containsKey("jsoninfo"))mJsonView.setText(result.get("jsoninfo"));
        }
    }
}
