package com.fxlc.zklm.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fxlc.zklm.MyApplication;
import com.fxlc.zklm.bean.MediaStoreData;
import com.fxlc.zklm.bean.Truck;
import com.fxlc.zklm.net.HttpResult;
import com.fxlc.zklm.net.SimpleCallback;
import com.fxlc.zklm.net.service.CarService;
import com.fxlc.zklm.net.service.ContactService;
import com.fxlc.zklm.net.service.PayService;
import com.fxlc.zklm.util.BitmapUtil;
import com.fxlc.zklm.util.UriUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UploadService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_ADD_CAR = "com.fxlc.zklm.service.action.addcar";
    public static final String ACTION_ADD_BILL = "com.fxlc.zklm.service.action.addbill";

    // TODO: Rename parameters
    public static final String EXTRA_TRUCK = "com.fxlc.zklm.service.extra.truck";
    public static final String EXTRA_BILL_IMG = "com.fxlc.zklm.service.extra.billimg";
    public static final String EXTRA_BILL_ID = "com.fxlc.zklm.service.extra.billid";

    public UploadService() {
        super("UploadService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionAddCar(Context context, Truck truck) {
        Intent intent = new Intent(context, UploadService.class);
        intent.setAction(ACTION_ADD_CAR);
        intent.putExtra(EXTRA_TRUCK, truck);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void actionAddBillImg(Context context, String id, ArrayList<MediaStoreData> imgs) {
        Intent intent = new Intent(context, UploadService.class);
        intent.setAction(ACTION_ADD_BILL);
        intent.putExtra(EXTRA_BILL_ID, id);
        intent.putParcelableArrayListExtra(EXTRA_BILL_IMG, imgs);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ADD_CAR.equals(action)) {
                final Truck truck = (Truck) intent.getSerializableExtra(EXTRA_TRUCK);
                handleActionAddCar(truck);
            } else if (ACTION_ADD_BILL.equals(action)) {
                Log.d("uploadService",action);
                String id = intent.getStringExtra(EXTRA_BILL_ID);
                List<MediaStoreData> imgs = intent.getParcelableArrayListExtra(EXTRA_BILL_IMG);
                handleActionBillImgs(id, imgs);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionAddCar(Truck truck) {
        // TODO: Handle action Foo

        Retrofit retrofit = MyApplication.getRetrofit();

        CarService service = retrofit.create(CarService.class);
        Map<String, String> param = new HashMap<>();
        param.put("brand", truck.getBrand());
        param.put("style", truck.getStyle());
        param.put("drive", truck.getDrive());
        param.put("soup", truck.getSoup());
        param.put("carNo", truck.getCarNo());
        param.put("handcarNo", truck.getHandcarNo());
        param.put("type", truck.getType());
        param.put("length", truck.getLength());
        param.put("height", truck.getHeight());


        MediaType type = MediaType.parse("application/otcet-stream");
        RequestBody driveBody1 =
                RequestBody.create(type, BitmapUtil.cpPicToByte(truck.getDriveImg1(), 100));
        MultipartBody.Part drivePart1 =
                MultipartBody.Part.createFormData("driveImg", truck.getDriveImg1(), driveBody1);
        RequestBody driveBody2 =
                RequestBody.create(type, BitmapUtil.cpPicToByte(truck.getDriveImg2(), 100));
        MultipartBody.Part drivePart2 =
                MultipartBody.Part.createFormData("driveImg", truck.getDriveImg2(), driveBody2);
        RequestBody driveBody3 =
                RequestBody.create(type, BitmapUtil.cpPicToByte(truck.getDriveImg3(), 100));
        MultipartBody.Part drivePart3 =
                MultipartBody.Part.createFormData("driveImg", truck.getDriveImg3(), driveBody3);

        RequestBody manageBody =
                RequestBody.create(type, BitmapUtil.cpPicToByte(truck.getManageImg(), 100));
        MultipartBody.Part managePart =
                MultipartBody.Part.createFormData("manageImg", truck.getManageImg(), manageBody);

        RequestBody handdriveBody1 =
                RequestBody.create(type, BitmapUtil.cpPicToByte(truck.getHanddriveImg1(), 100));
        MultipartBody.Part handdrivePart1 =
                MultipartBody.Part.createFormData("handdriveImg", truck.getHanddriveImg1(), handdriveBody1);
        RequestBody handdriveBody2 =
                RequestBody.create(type, BitmapUtil.cpPicToByte(truck.getHanddriveImg2(), 100));
        MultipartBody.Part handdrivePart2 =
                MultipartBody.Part.createFormData("handdriveImg", truck.getHanddriveImg2(), handdriveBody2);
        RequestBody handdriveBody3 =
                RequestBody.create(type, BitmapUtil.cpPicToByte(truck.getHanddriveImg3(), 100));
        MultipartBody.Part handdrivePart3 =
                MultipartBody.Part.createFormData("handdriveImg", truck.getHanddriveImg3(), handdriveBody3);

        RequestBody handmanageBody =
                RequestBody.create(type, BitmapUtil.cpPicToByte(truck.getHandmanageImg(), 100));
        MultipartBody.Part handmanagePart =
                MultipartBody.Part.createFormData("handmanageImg", truck.getHandmanageImg(), handmanageBody);

        Call<HttpResult> call = service.saveTruck(param, drivePart1, drivePart2, drivePart3, managePart, handdrivePart1, handdrivePart2, handdrivePart3, handmanagePart);
        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {

            }
        });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBillImgs(String id, List<MediaStoreData> imgs) {
        // TODO: Handle action Baz

        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
        MediaType type = MediaType.parse("application/otcet-stream");
        Map<String, RequestBody> imgMap = new HashMap<>();
        if (imgs.size() > 0) {
            for (int i = 0; i < imgs.size(); i++) {
                String path = UriUtil.getRealFilePath(getApplicationContext(), imgs.get(i).uri);
                String substring = path.substring(path.lastIndexOf("/") + 1, path.length());
                Log.d("uploadService",substring);
                RequestBody body = RequestBody.create(type, BitmapUtil.cpPicToByte(path, 100));

                imgMap.put("billImg\"; filename=\"" + substring , body);

            }
        }
        Retrofit retrofit = MyApplication.getRetrofit();
        PayService service = retrofit.create(PayService.class);
        Call<HttpResult> call = service.saveBillImg(id, imgMap);
        call.enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(HttpResult result) {

            }
        });

    }
}
