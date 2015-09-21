package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.responce.appeals.WSResult;
import com.crmc.ourcity.utils.Image;
import com.crmc.ourcity.utils.SPManager;

import java.io.UnsupportedEncodingException;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * Created by podo on 04.09.15.
 */
public class AppealsLoader extends BaseLoader<WSResult> {

    private String json;
    private String route;


    public AppealsLoader(Context _context, Bundle _args) {
        super(_context);
        json = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_JSON);
        route = _args.getString(Constants.BUNDLE_CONSTANT_REQUEST_ROUTE);

        json = changeResidentIdInJson(json, SPManager.getInstance(_context).getResidentId());

    }

    @Override
    public WSResult loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        WSResult wsResult;

        try {
            wsResult = api.getAppeals(route, new TypedByteArray("application/json", json.getBytes("UTF-8")));
            if (wsResult.getResultObjects() != null) {
                for (int i = 0; i < wsResult.getResultObjects().size(); i++) {
                    if (!TextUtils.isEmpty(wsResult.getResultObjects().get(i).AttachedFiles)) {
                        wsResult.getResultObjects().get(i).AttachedCompressFile = Image.compressBitmap(Image
                                .convertBase64ToBitmap(wsResult.getResultObjects().get(i).AttachedFiles));

                    }
                }
            }
        } catch (RetrofitError | UnsupportedEncodingException _e) {
            wsResult = null;
        }
        return wsResult;
    }
}
