package com.r0adkll.hackathon.api;

import com.ftinc.kit.util.Utils;
import com.r0adkll.hackathon.api.model.FindPetsResponse;
import com.r0adkll.hackathon.data.model.Pet;
import com.r0adkll.hackathon.data.model.Shelter;
import com.r0adkll.hackathon.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.http.Field;
import rx.Observable;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.api
 * Created by drew.heavner on 8/28/15.
 */
public class MockApiService implements ApiService{

    static final String[] mockPetNames = new String[]{
            "Fluffy",
            "Fido",
            "Buddy",
            "Tiki",
            "Liesel",
            "Charlie",
            "Booster",
            "Butters",
            "Harley",
            "Ice Cube",
            "Snoop Lion"
    };

    static final List<Shelter> mockShelters = new ArrayList<Shelter>(){
        {
            Shelter s1 = new Shelter();
            s1.name = "Pet's Inc.";
            s1.address = "1000 NoPetKill Ln. Columbia, SC 29205";
            s1.description = "We love pets, and won't kill them. Pinky Promise!!!";
            s1.latitude = 0.222f;
            s1.longitude = 0.342341f;
            s1.id = 0;
            add(s1);

            Shelter s2 = new Shelter();
            s2.name = "City of Columbia Animal Shelter";
            s2.address = "1000 MightPetKill Ln. Columbia, SC 29205";
            s2.description = "We love pets, but if you don't adopt in time, they will be euthanized";
            s2.latitude = 0.123341234f;
            s2.longitude = 0.398098234f;
            s2.id = 1;
            add(s2);

            Shelter s3 = new Shelter();
            s3.name = "Your Aunt's Farm";
            s3.address = "1000 MiddleOfNowhere Ln. Kansas City, KS 12345";
            s3.description = "Send your pets here where they will live, play, and romp for eternity!!!";
            s3.latitude = 0.098765f;
            s3.longitude = 0.765432f;
            s3.id = 2;
            add(s3);
        }
    };

    static String description = "Fatback t-bone biltong beef ribs, brisket sirloin corned beef rump. " +
            "Brisket ham meatball, shank pastrami strip steak ball tip turducken. Sirloin pastrami beef, " +
            "picanha filet mignon short ribs pork loin biltong bacon alcatra tenderloin kevin. Tri-tip " +
            "andouille pork fatback ham hock spare ribs swine porchetta ball tip. Meatloaf jerky porchetta " +
            "flank, alcatra filet mignon bacon turducken.";


    @Override
    public Observable<User> login(@Field("email") String email, @Field("password") String pswd) {
        User user = new User();
        user.email = "veedubusc@gmail.com";
        user.name = "Drew H.";
        user.phone = "(555) 555-5555";
        user.token = "12345123";
        return Observable.just(user).delay(150, TimeUnit.MILLISECONDS);
    }

    @Override
    public Observable<User> signup(@Field("email") String email, @Field("password") String pswd, @Field("name") String name, @Field("phone") String phone) {
        User user = new User();
        user.email = "veedubusc@gmail.com";
        user.name = "Drew H.";
        user.phone = "(555) 555-5555";
        user.token = "12345123";
        return Observable.just(user).delay(150, TimeUnit.MILLISECONDS);
    }

    @Override
    public Observable<FindPetsResponse> findPets(@Field("lat") double latitude, @Field("lon") double longitude) {
        FindPetsResponse response = new FindPetsResponse();

        response.pets = new ArrayList<>();
        response.shelters = new ArrayList<>(mockShelters);

        // Generate test data
        for (int i = 0; i < 20; i++) {
            Pet pet = new Pet();

            // Generate random pets
            int index = Utils.getRandom().nextInt(mockPetNames.length);
            pet.name = mockPetNames[index];
            pet.age = Utils.getRandom().nextFloat() * 15;
            pet.avgScore = Utils.getRandom().nextFloat() * 5;
            pet.type = Utils.getRandom().nextBoolean() ? Pet.TYPE_CAT : Pet.TYPE_DOG;
            pet.description = description;

            String url = "http://www.placecage.com/%d/%d";
            int width = Utils.getRandom().nextInt(100) + 300;
            int height = Utils.getRandom().nextInt(100) + 300;
            pet.photoUrl = String.format(url, width, height);

            // Generate Shelter
            index = Utils.getRandom().nextInt(mockShelters.size());
            pet.shelter = mockShelters.get(index);
            response.pets.add(pet);
        }

        return Observable.just(response).delay(150, TimeUnit.MILLISECONDS);
    }


}
