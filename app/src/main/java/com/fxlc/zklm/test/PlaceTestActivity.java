package com.fxlc.zklm.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.R;
import com.fxlc.zklm.bean.City;
import com.fxlc.zklm.bean.CityDao;
import com.fxlc.zklm.bean.WeatherCity;
import com.fxlc.zklm.net.service.BDPlaceService;
import com.fxlc.zklm.net.service.WeatherCityService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceTestActivity extends AppCompatActivity {

    GridView gridView;
    private CityDao cityDao;
    private List<City> cityList;
    private String placeStr = "",pstr = "",cstr ="",dstr = "";
    private TextView placeTxt;
    private int level = 0;
    private CityAdapter cityAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityDao = ((MyApplication) getApplication()).getDaoSession().getCityDao();

        setContentView(R.layout.activity_dbtest);
        placeTxt = (TextView) findViewById(R.id.place);
        gridView = (GridView) findViewById(R.id.grid);
        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityList = cityDao.queryBuilder().where(CityDao.Properties.Parentid.eq("0")).list();
                Log.d("city", cityList.size() + "");
                level = 0;
                gridView.setVisibility(View.VISIBLE);
                cityAdapter = new CityAdapter();
                gridView.setAdapter(cityAdapter);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(level == 0) {
                    City city = cityList.get(i);
                    pstr = city.getCity();
                    Log.d("city",city.getCity() + city.getId());
                    cityList = cityDao.queryBuilder().where(CityDao.Properties.Parentid.eq(city.getCityid())).list();
                    Log.d("city",cityList.get(0).getCity() + cityList.get(0).getParentid());
                    cityAdapter.notifyDataSetChanged();
                    level++;
                }else if (level == 1){
                    City city = cityList.get(i);
                    cstr = city.getCity();
                    Log.d("city",city.getCity() + city.getId());
                    cityList = cityDao.queryBuilder().where(CityDao.Properties.Parentid.eq(city.getCityid())).list();
                    Log.d("city",cityList.get(0).getCity() + cityList.get(0).getParentid());
                    cityAdapter.notifyDataSetChanged();
                    level++;
                }else if (level ==2){
                    City city = cityList.get(i);
                    dstr = city.getCity();
                    gridView.setVisibility(View.GONE);
                }
                placeStr = pstr + "-" + cstr + "-" + dstr;
                placeTxt.setText(placeStr);

            }
        });
    }

    class CityAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return cityList.size();
        }

        @Override
        public Object getItem(int i) {
            return cityList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null)
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_list_item, viewGroup, false);
            TextView txt = (TextView) view;
            txt.setBackgroundColor(getResources().getColor(R.color.white));
            txt.setText(cityList.get(i).getCity());

            return view;
        }
    }
    public void getAllCity() {
        String URL_WeatherCity = "http://jisutqybmf.market.alicloudapi.com/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_WeatherCity)
                .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory( StringConverterFactory.create())

                .build();

        WeatherCityService service = retrofit.create(WeatherCityService.class);
        final String auth = "APPCODE " + "60091d0fb1a648459827272c0abf7909";

        Call<WeatherCity> citys = service.listCity(auth);

        citys.enqueue(new Callback<WeatherCity>() {
            @Override
            public void onResponse(Call<WeatherCity> call, Response<WeatherCity> response) {
                WeatherCity weatherCity = response.body();

                List<City> cityList = weatherCity.getResult();

                cityDao.insertInTx(cityList);

            }

            @Override
            public void onFailure(Call<WeatherCity> call, Throwable t) {

            }
        });

    }
    public void place() {

        String url = "http://api.map.baidu.com/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
//             .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory( StringConverterFactory.create())
                .build();

        BDPlaceService placeService = retrofit.create(BDPlaceService.class);
        final String ak = "3SsBr80QjeaM56fwv8Q7FnAfouDm7GXI";
        final String mcode = "13:FE:86:52:59:CD:DC:02:AD:54:1F:E7:D3:CC:DC:62:54:19:14:2C;com.fxlc.zklm";
        Map<String,String> map = new HashMap<>();
        map.put("ak",ak);
        map.put("mcode",mcode);
        map.put("query","天安门");
        map.put("region","北京市");
        map.put("output","json");
        map.put("city_limit","true");

        Call<ResponseBody> call = placeService.suggest(map);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("response",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        try {
            URLEncoder.encode("", Xml.Encoding.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
