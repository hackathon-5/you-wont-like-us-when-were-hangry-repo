package com.r0adkll.hackathon.api;

import com.r0adkll.hackathon.api.model.FindPetsResponse;
import com.r0adkll.hackathon.api.model.ScheduleRequest;
import com.r0adkll.hackathon.api.model.ScheduleResponse;
import com.r0adkll.hackathon.api.model.SuccessResponse;
import com.r0adkll.hackathon.data.model.Pet;
import com.r0adkll.hackathon.data.model.Shelter;
import com.r0adkll.hackathon.data.model.User;

import java.util.concurrent.TimeUnit;

import retrofit.http.Body;
import retrofit.http.Field;
import rx.Observable;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.api
 * Created by drew.heavner on 8/29/15.
 */
public class OllieApiService implements ApiService {

    public OllieApiService(){

    }

    @Override
    public Observable<User> login(@Field("email") String email, @Field("password") String pswd) {
        User user = new User();
        user.email = email;
        user.name = "Drew H.";
        user.phone = "(555) 555-5555";
        user.access_token = "12345123";
        return Observable.just(user).delay(200, TimeUnit.MILLISECONDS);
    }

    @Override
    public Observable<User> signup(@Field("email") String email, @Field("password") String pswd, @Field("name") String name, @Field("phone") String phone) {
        User user = new User();
        user.email = email;
        user.name = name;
        user.phone = phone;
        user.access_token = "12345123";
        return Observable.just(user)
                .delay(150, TimeUnit.MILLISECONDS);
    }

    @Override
    public Observable<FindPetsResponse> findPets(@Field("lat") double latitude, @Field("lon") double longitude) {
        return null;
    }

    @Override
    public Observable<SuccessResponse> schedule(@Body ScheduleRequest request) {
        return null;
    }

    @Override
    public Observable<ScheduleResponse> getMySchedule() {
        return null;
    }

    /***********************************************************************************************
     *
     * FUCKK
     *
     */

    public static void populateDatabase(){

        // 1) Creat shelters
        Shelter s1 = new Shelter();
        s1.name = "";
        s1.description = "";
        s1.address = "";
        s1.distance = 0;
        s1.latitude = 0.0f;
        s1.longitude = 0.0f;
        s1.save();

        /**********************************************************
         *
         */

        // Create this shelters animals
        Pet p1 = new Pet();
        p1.name = "Ethereal Parable";
        p1.description = "Ethereal Parable will be arriving in Charleston on August 30th from Orlando, Fl. He is a handsome black boy who raced at 78 lbs. He is thought to be kitty friendly. He is 3 years old with a birth date of May 14, 2012. We will update his info once he arrives and as we get to know him.";
        p1.type = "dog";
        p1.age = 3f;
        p1.avgScore = 4.5f;
        p1.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/33101919/1/?bust=1440508985&width=632&no_scale_up=1";
        p1.shelter = s1;
        p1.save();

        Pet p2 = new Pet();
        p2.name = "Mohican Ely";
        p2.description = "Ely is a handsome white and fawn boy who raced at 73 lbs. He is thought to not be kitty friendly.He is 2 1/2 years old with a birth date of March1, 2013. We will update his information once he arrives in Charleston and as we get to know him.";
        p2.type = "dog";
        p2.age = 2.5f;
        p2.avgScore = 4.1f;
        p2.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/33101892/1/?bust=1440508668&width=632&no_scale_up=1";
        p2.shelter = s1;
        p2.save();

        Pet p3 = new Pet();
        p3.name = "Woohoo";
        p3.description = "Woohoo (nicknamed Tina by her foster family) arrived in the lowcountry on July 24th from Jacksonville. She is a pretty red girl who raced at 52 lbs. She was previously in a home but the owner died so she was returned to the kennel.";
        p3.type = "dog";
        p3.age = 3f;
        p3.avgScore = 4.1f;
        p3.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/32783321/1/?bust=1437418053&width=632&no_scale_up=1";
        p3.shelter = s1;
        p3.save();

        Pet p4 = new Pet();
        p4.name = "Mike";
        p4.description = "Mike will be arriving in the lowcountry on July 24th from Jacksonville, Fl. He is a handsome brindle boy who raced at 75 lbs. He is 2 years old with a birth date of April 24, 2013. He is kitty friendly. We will update his information as we get to know him.";
        p4.type = "dog";
        p4.age = 2f;
        p4.avgScore = 4.5f;
        p4.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/32783282/1/?bust=1437417671&width=632&no_scale_up=1";
        p4.shelter = s1;
        p4.save();

        Pet p5 = new Pet();
        p5.name = "Wilbur";
        p5.description = "Wilbur joined our group earlier this year after being in a home for several years. He turned 8 on June 12. He has excellent leash manners and is completely housebroken. He seems to enjoy the company of other greyhounds and will do best with an experienced greyhound owner.";
        p5.type = "dog";
        p5.age = 8f;
        p5.avgScore = 3.8f;
        p5.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/31753425/3/?bust=1433432406&width=632&no_scale_up=1";
        p5.shelter = s1;
        p5.save();

        Pet p6 = new Pet();
        p6.name = "Ballys Bill Mac";
        p6.description = "Billy has recently been returned to the group for no fault of his own. He originally arrived in Charleston on February 20th. He is a handsome red boy with dark eyeliner and muzzle who raced at 70 lbs. He is kitty friendly and gets along well with dogs of all sizes. He is 3 years old with a birth date of April 7, 2012. He has a happy laidback personality.";
        p6.type = "dog";
        p6.age = 3f;
        p6.avgScore = 4.8f;
        p6.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/31445916/1/?bust=1437529148&width=632&no_scale_up=1";
        p6.shelter = s1;
        p6.save();

        /**********************************************************
         *
         */

        Shelter s2 = new Shelter();
        s2.name = "";
        s2.description = "";
        s2.address = "";
        s2.distance = 0;
        s2.latitude = 0.0f;
        s2.longitude = 0.0f;
        s2.save();

        Pet p21 = new Pet();
        p21.name = "";
        p21.description = "";
        p21.type = "";
        p21.age = 0f;
        p21.avgScore = 0f;
        p21.photoUrl = "";
        p21.shelter = s2;
        p21.save();

        Pet p22 = new Pet();
        p21.name = "";
        p21.description = "";
        p21.type = "";
        p21.age = 0f;
        p21.avgScore = 0f;
        p21.photoUrl = "";
        p21.shelter = s2;
        p21.save();

        Shelter s3 = new Shelter();
        s3.name = "";
        s3.description = "";
        s3.address = "";
        s3.distance = 0;
        s3.latitude = 0.0f;
        s3.longitude = 0.0f;
        s3.save();

    }

}
