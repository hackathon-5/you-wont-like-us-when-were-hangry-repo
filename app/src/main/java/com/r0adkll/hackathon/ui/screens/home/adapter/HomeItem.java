package com.r0adkll.hackathon.ui.screens.home.adapter;

import com.r0adkll.hackathon.data.model.Pet;
import com.r0adkll.hackathon.data.model.Shelter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Project: Hackathon2015
 * Package: com.r0adkll.hackathon.ui.screens.home.adapter
 * Created by drew.heavner on 8/29/15.
 */
public class HomeItem {

    public static List<HomeItem> convert(List<Pet> pets){
        List<HomeItem> items = new ArrayList<>();

        Map<Shelter, ArrayList<Pet>> petMap = new HashMap<>();

        for (int i = 0; i < pets.size(); i++) {
            Pet pet = pets.get(i);
            Shelter shelter = pet.shelter;

            ArrayList<Pet> group = petMap.get(shelter);
            if(group == null){
                group = new ArrayList<>();
            }

            group.add(pet);
            petMap.put(shelter, new ArrayList<>(group));
        }

        Set<Shelter> keys = petMap.keySet();
        for (Shelter key : keys) {
            ArrayList<Pet> values = petMap.get(key);
            items.add(header(key));
            for (Pet value : values) {
                items.add(pet(value));
            }
        }

        return items;
    }

    public static HomeItem pet(Pet pet){
        HomeItem item = new HomeItem();
        item.type = HomeRecyclerAdapter.TYPE_PET;
        item.item = pet;
        return item;
    }

    public static HomeItem header(Shelter shelter){
        HomeItem item = new HomeItem();
        item.type = HomeRecyclerAdapter.TYPE_HEADER;
        item.header = shelter;
        return item;
    }

    public int type;
    public Pet item;
    public Shelter header;

    private HomeItem(){}

}
