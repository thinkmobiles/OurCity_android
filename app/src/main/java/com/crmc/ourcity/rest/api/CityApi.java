package com.crmc.ourcity.rest.api;

import com.crmc.ourcity.rest.request.base.BaseModel;
import com.crmc.ourcity.rest.request.events.EventsModel;
import com.crmc.ourcity.rest.request.menu.CityModel;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.rest.responce.events.Site;
import com.crmc.ourcity.rest.responce.map.MapCategory;
import com.crmc.ourcity.rest.responce.menu.MenuNodes;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by SetKrul on 28.07.2015.
 */
public interface CityApi {
    /**
     * @param cityModel number city and language
     * @return tree-like structure for client app menu
     */
    @POST("/GetMobileMenu")
    MenuNodes getMenu(@Body CityModel cityModel);

    /**
     * @param baseModel number city
     * @return map marker category and markers with data
     */
    @POST("/GetInterestPointToCity")
    List<MapCategory> getMapDetails(@Body BaseModel baseModel);

    /**
     * @param eventsModel type get data
     * @return data for listView city sites
     */
    @POST("/GetEventsByCityAndTypeId")
    List<Site> getSites(@Body EventsModel eventsModel);

    /**
     * @param baseModel city number
     * @return phones number with others data
     */
    @POST("/GetCityEntities")
    List<Phones> getPhones(@Body BaseModel baseModel);
}