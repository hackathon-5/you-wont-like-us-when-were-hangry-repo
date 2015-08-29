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
        p21.name = "Rose";
        p21.description = "Hi there! As you cans see, I'm a beautiful black Labby girl and I'm so happy! I'm about 7-8 years young, and I love meeting new people - I've never met a stranger! I get along with everyone including the other dogs and kitties at my foster home - and I really love the little 2 legged types! I also love going for car rides - I'm always up for an adventure!";
        p21.type = "dog";
        p21.age = 7f;
        p21.avgScore = 4.8f;
        p21.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/32949670/1/?bust=1438961895&width=632&no_scale_up=1";
        p21.shelter = s2;
        p21.save();

        Pet p22 = new Pet();
        p22.name = "Jonah";
        p22.description = "Hi! I'm Jonah and as you can see, I'm a handsome yellow Lab! I've got a very thick longer coat, so I might have a little husky or shepherd in my bloodline, too! I'm about 10 years young and I love to run outside - I'll do best in a house with another easy going dog for company";
        p22.type = "dog";
        p22.age = 10f;
        p22.avgScore = 4.9f;
        p22.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/32058198/1/?bust=1430575299&width=632&no_scale_up=1";
        p22.shelter = s2;
        p22.save();

        Pet p23 = new Pet();
        p23.name = "Artie";
        p23.description = "Artie is great with dogs, cats, kids, everyone! He's great on walks (up to 1/2 mile at a time). Artie also likes to sit by your side and relax. Apply for Artie today!";
        p23.type = "dog";
        p23.age = 5.5f;
        p23.avgScore = 5f;
        p23.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/32858722/1/?bust=1438116232&width=632&no_scale_up=1";
        p23.shelter = s2;
        p23.save();

        Pet p24 = new Pet();
        p24.name = "Toby";
        p24.description = "Meet Toby. Toby is a 9 month old. This guy is great with dogs, but no cats please. Toby is a young and exuberant fellow and would likely be better in a home with older kids. Toby has a love of life and is learning to understand his size and improve his manners. More updates on his training coming soon.";
        p24.type = "dog";
        p24.age = 1f;
        p24.avgScore = 4.2f;
        p24.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/32406554/1/?bust=1433975398&width=632&no_scale_up=1";
        p24.shelter = s2;
        p24.save();

        Pet p25 = new Pet();
        p25.name = "Jeb";
        p25.description = "Meet Jeb. Jeb is 8 years old and working to lose a little weight. He is about 90 pounds. This fellow is good with dogs and cats and older kids. While he loves them all he is looking for a relatively quiet life. Jeb is well mannered. Although he has not been crated he is house trained, good on leash and knows basic commands.";
        p25.type = "dog";
        p25.age = 8f;
        p25.avgScore = 4.9f;
        p25.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/31279172/2/?bust=1421795663&width=632&no_scale_up=1";
        p25.shelter = s2;
        p25.save();

        Pet p26 = new Pet();
        p26.name = "Lilly";
        p26.description = "Meet Lilly. Lilly is about 4 years old and around 80 pounds. This girl is VERY shy. Lilly is well mannered but a quiet, shy girl learning to trust again. She is house, crate and leash trained. In fact she loves to go on walks. She knows some basic commands. Lilly's coat seems to be darkening as she gets fitter.";
        p26.type = "dog";
        p26.age = 4f;
        p26.avgScore = 4.8f;
        p26.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/31204757/2/?bust=1420810298&width=632&no_scale_up=1";
        p26.shelter = s2;
        p26.save();

        Pet p27 = new Pet();
        p27.name = "Mattie";
        p27.description = "Meet Mattie. Mattie is an energetic 8 year old. This girl is great with other dogs and kids, but her view on cats is not yet known. She is house trained and does not need a crate. Mattie is working to improve her leash and basic command skills. Mattie leaps with joy when she sees the leash and loves taking long walks.";
        p27.type = "dog";
        p27.age = 8f;
        p27.avgScore = 3.6f;
        p27.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/31928729/3/?bust=1429137574&width=632&no_scale_up=1";
        p27.shelter = s2;
        p27.save();

        /**********************************************************
         *
         */

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
