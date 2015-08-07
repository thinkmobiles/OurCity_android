package com.crmc.ourcity.loader;

import android.content.Context;
import android.os.Bundle;

import com.crmc.ourcity.global.Constants;
import com.crmc.ourcity.rest.RestClientApi;
import com.crmc.ourcity.rest.api.CityApi;
import com.crmc.ourcity.rest.request.base.BaseCity;
import com.crmc.ourcity.rest.request.base.BaseModel;

import retrofit.RetrofitError;

/**
 * Created by SetKrul on 30.07.2015.
 */
public class ImageLoader extends BaseLoader<String> {

    private int cityNumber;
    private int loadImageType;

    public ImageLoader(Context _context, Bundle _args) {
        super(_context);
        cityNumber = _args.getInt(Constants.BUNDLE_CONSTANT_CITY_NUMBER);
        loadImageType = _args.getInt(Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE);
    }

    @Override
    public String loadInBackground() {
        CityApi api = RestClientApi.getCityApi();
        String result;
        try {
            switch (loadImageType) {
                case Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_LOGO:
                    result = api.getLogoImage(new BaseModel(new BaseCity(cityNumber)));
                    break;
                case Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_CITY:
                    result = api.getCityImage(new BaseModel(new BaseCity(cityNumber)));
                    break;
                case Constants.BUNDLE_CONSTANT_LOAD_IMAGE_TYPE_MAYOR:
                    result = api.getMayorImage(new BaseModel(new BaseCity(cityNumber)));
                    break;
                default:
                    result = null;
                    break;
            }
        } catch (RetrofitError _e) {
            result = null;
        }
        return result;
    }
}