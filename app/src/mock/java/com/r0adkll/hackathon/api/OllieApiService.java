package com.r0adkll.hackathon.api;

import com.r0adkll.hackathon.api.model.FindPetsResponse;
import com.r0adkll.hackathon.api.model.ScheduleRequest;
import com.r0adkll.hackathon.api.model.ScheduleResponse;
import com.r0adkll.hackathon.api.model.SuccessResponse;
import com.r0adkll.hackathon.data.model.Pet;
import com.r0adkll.hackathon.data.model.Reservation;
import com.r0adkll.hackathon.data.model.ReservationTime;
import com.r0adkll.hackathon.data.model.Shelter;
import com.r0adkll.hackathon.data.model.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ollie.Ollie;
import ollie.query.Select;
import retrofit.http.Body;
import retrofit.http.Field;
import rx.Observable;
import rx.functions.Func1;

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
        populateDatabase();
        return Observable.just(user).delay(200, TimeUnit.MILLISECONDS);
    }

    @Override
    public Observable<User> signup(@Field("email") String email, @Field("password") String pswd, @Field("name") String name, @Field("phone") String phone) {
        User user = new User();
        user.email = email;
        user.name = name;
        user.phone = phone;
        user.access_token = "12345123";
        populateDatabase();
        return Observable.just(user)
                .delay(150, TimeUnit.MILLISECONDS);
    }

    @Override
    public Observable<FindPetsResponse> findPets(@Field("lat") double latitude, @Field("lon") double longitude) {
        return Select.from(Pet.class)
                .observable()
                .map(pets -> {
                    FindPetsResponse response = new FindPetsResponse();
                    response.pets = pets;
                    return response;
                });
    }

    @Override
    public Observable<SuccessResponse> schedule(@Body ScheduleRequest request) {
        return Select.from(User.class)
                .observableSingle()
                .flatMap(user -> {
                    Ollie.getDatabase().beginTransaction();
                    try {
                        for (Reservation reservation : request.reservations) {
                            reservation.user = user;
                            reservation.save();

                            // Save all it's time objects
                            for (Integer time : reservation.times) {
                                ReservationTime newTime = new ReservationTime();
                                newTime.time = time;
                                newTime.reservation = reservation;
                                newTime.save();
                            }
                        }
                        Ollie.getDatabase().setTransactionSuccessful();
                    }finally {
                        Ollie.getDatabase().endTransaction();
                    }

                    return Observable.just(new SuccessResponse());
                });

    }

    @Override
    public Observable<ScheduleResponse> getMySchedule() {
        return Select.from(User.class)
                .observableSingle()
                .flatMap(user -> user.getReservations())
                .map(reservations -> {
                    ScheduleResponse response = new ScheduleResponse();
                    response.reservations = reservations;
                    return response;
                });
    }

    /***********************************************************************************************
     *
     * FUCKK
     *
     */

    public static void populateDatabase(){

        // 1) Creat shelters
        Shelter s1 = new Shelter();
        s1.name = "Greyhoud Pets of America - Charleston";
        s1.description = "Greyhound Pets of America - Charleston is an all-volunteer, nonprofit organization dedicated to finding caring, permanent homes for retired racing greyhounds, and to providing support and guidance for the adopted dogs and their families.";
        s1.address = "PO Box 14533 Charleston, SC 29422";
        s1.distance = 10278;
        s1.latitude = 32.897561f;
        s1.longitude = -80.026065f;
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
        s2.name = "Daisy's Place Retriever Rescue";
        s2.description = "Daisy's Place Retriever Rescue is dedicated to saving and finding loving homes for Retrievers (and Retriever mixes) six years and older.";
        s2.address = "PO Box 20729 Charleston, SC 29413";
        s2.distance = 21291;
        s2.latitude = 32.712772f;
        s2.longitude = -79.965249f;
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
        s3.name = "Advanced Animal Care";
        s3.description = "Since 2001, Advanced Animal Care has been serving the Mount Pleasant and surrounding areas. We are a full-service veterinary practice offering general veterinary medicine and surgery.";
        s3.address = "3373 S. Morgan's Point Rd. Mt. Pleasant, SC 29466";
        s3.distance = 15734;
        s3.latitude = 32.996595f;
        s3.longitude = -80.039764f;
        s3.save();

        Pet p28 = new Pet();
        p28.name = "Cricket";
        p28.description = "Cricket is a 3 yr. old female cat that was adopted from us about 2 years ago. Unfortunately, her family could no longer care for her and had to make the heartbreaking decision to return her back to us. She is still incredibly sweet and affectionate, despite having to be relocated back.";
        p28.type = "cat";
        p28.age = 2.0f;
        p28.avgScore = 4.3f;
        p28.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/32919246/2/?bust=1438702789&width=632&no_scale_up=1";
        p28.shelter = s3;
        p28.save();

        Pet p29 = new Pet();
        p29.name = "Fiona";
        p29.description = "Fiona is the SWEETEST, SMALLEST, CUTEST, thing you've ever seen! She is about 6 weeks old and was bottle raised by one of our technicians here. She is healthy, happy, and well-adjusted, and just wants someone to cuddle up with at night.";
        p29.type = "cat";
        p29.age = 0.1f;
        p29.avgScore = 5.0f;
        p29.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/32950890/1/?bust=1438968703&width=632&no_scale_up=1";
        p29.shelter = s3;
        p29.save();

        Pet p30 = new Pet();
        p30.name = "Lolly";
        p30.description = "Lolly is 2 years old and weighs about 7 pounds. She is waiting patiently for a loving home. She tested negative for feline leukemia, has been dewormed, received the FVRCP and Rabies Vaccines, and has been spayed.";
        p30.type = "cat";
        p30.age = 2.0f;
        p30.avgScore = 3.2f;
        p30.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/33011543/1/?bust=1439569551&width=632&no_scale_up=1";
        p30.shelter = s3;
        p30.save();

        Pet p31 = new Pet();
        p31.name = "Moby";
        p31.description = "About 3 years old. Good with cats and dogs. 'Talks' to you for attention and is very sweet. Come spend some time with him if you have a chance!";
        p31.type = "cat";
        p31.age = 3.0f;
        p31.avgScore = 4.0f;
        p31.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/32506347/1/?bust=1434913182&width=632&no_scale_up=1";
        p31.shelter = s3;
        p31.save();

        Pet p32 = new Pet();
        p32.name = "Milo";
        p32.description = "My name is Milo. I was named after a hobbit from Lord of the Rings. I am a brown tabby cat but not an ordinary tabby. I have the biggest eyes, the most handsome face and a white bib and feet. I am easy going and grateful for any attention. I purr, reach for your touch and love to sit in your lap. I am a very good boy who deserves a loving stable forever home.";
        p32.type = "cat";
        p32.age = 4.5f;
        p32.avgScore = 2.8f;
        p32.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/27024593/2/?bust=1384104128&width=632&no_scale_up=1";
        p32.shelter = s3;
        p32.save();

        Pet p33 = new Pet();
        p33.name = "Sweden & Norway";
        p33.description = "Sweden and Norway are so close they wanted to have their picture taken together. They are almost 3 months old.";
        p33.type = "cat";
        p33.age = 0.3f;
        p33.avgScore = 2.5f;
        p33.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/33061353/1/?bust=1440081467&width=632&no_scale_up=1";
        p33.shelter = s3;
        p33.save();

        Pet p34 = new Pet();
        p34.name = "Chive";
        p34.description = "Chive is a very alert, funny, and playful 10 month old kitty. She is fun to watch and will take a break to give you some affection while she catches her breath";
        p34.type = "cat";
        p34.age = 0.8f;
        p34.avgScore = 1.5f;
        p34.photoUrl = "https://drpem3xzef3kf.cloudfront.net/photos/pets/16088332/2/?bust=1300631273&width=632&no_scale_up=1";
        p34.shelter = s3;
        p34.save();


    }

}
