package com.crmc.ourcity.rest.api;

import com.crmc.ourcity.rest.request.base.BaseModel;
import com.crmc.ourcity.rest.request.menu.CityModel;
import com.crmc.ourcity.rest.request.resident.ResidentModel;
import com.crmc.ourcity.rest.request.vote.VoteModel;
import com.crmc.ourcity.rest.responce.address.StreetsFull;
import com.crmc.ourcity.rest.responce.events.Events;
import com.crmc.ourcity.rest.responce.events.MassageToResident;
import com.crmc.ourcity.rest.responce.events.News;
import com.crmc.ourcity.rest.responce.events.Phones;
import com.crmc.ourcity.rest.responce.map.MapCategory;
import com.crmc.ourcity.rest.responce.map.MapTrips;
import com.crmc.ourcity.rest.responce.menu.MenuFull;
import com.crmc.ourcity.rest.responce.vote.VoteFull;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

/**
 * Created by SetKrul on 28.07.2015.
 */
public interface CityApi {

    /**
     * @param cityModel number city and language
     * @return tree-like structure for client app menu
     */
    @POST("/GetMobileMenu")
    MenuFull getMenu(@Body CityModel cityModel);

    /**
     * @param baseModel number city
     * @return map marker category and markers with data
     */
    @POST("/GetInterestPointToCity")
    List<MapCategory> getMapDetails(@Body BaseModel baseModel);

    /**
     * @param baseModel city number
     * @return phones number with others data
     */
    @POST("/GetCityEntities")
    List<Phones> getPhones(@Body BaseModel baseModel);

    /**
     * @return city events
     */
    @POST("/{path}")
    List<Events> getEvents(@Path ("path") String q, @Body TypedInput body);

    /**
     * @param baseModel city number
     * @return news??????
     */
    @POST("/GetInterestAreaToCity")
    List<News> getNews(@Body BaseModel baseModel);

    /**
     * @param residentModel resident id
     * @return messages from mailing
     */
    @POST("/GetMessagesToResident")
    List<MassageToResident> getMessagesToResident(@Body ResidentModel residentModel);

    /**
     * @param body json request
     * @return Survey vote
     */
    @POST("/{path}")
    List<VoteFull> getVote(@Path ("path") String q, @Body TypedInput body);

    /**
     * @param voteModel selected point int, age int, gender int
     * @return true : false
     */
    @POST("/AddSurveyVote")
    String replyVote(@Body VoteModel voteModel);

    /**
     * @param baseModel city number
     * @return string base64 image Logo
     */
    @POST("/GetLogoImage")
    String getLogoImage(@Body BaseModel baseModel);

    /**
     * @param baseModel city namber
     * @return string base64 image City
     */
    @POST("/GetCityImage")
    String getCityImage(@Body BaseModel baseModel);

    /**
     * @param baseModel city number
     * @return string base64 image Mayor
     */
    @POST("/GetMayorImage")
    String getMayorImage(@Body BaseModel baseModel);

    /**
     * @param baseModel city number
     * @return point for the construction of the route on the map
     */
    @POST("/GetTripsByCityId")
    List<MapTrips> getMapTrips(@Body BaseModel baseModel);

    @POST("/{path}")
    StreetsFull getStreets(@Path ("path") String q, @Body TypedInput body);
}