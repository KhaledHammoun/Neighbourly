package com.android.neighbourly.persistence.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.neighbourly.model.classes.CartItem;
import com.android.neighbourly.persistence.ShoppingCartDao;

@Database(entities = {CartItem.class}, version = 1)
public abstract class ShoppingCartDatabase extends RoomDatabase {

    private static ShoppingCartDatabase instance;
    public abstract ShoppingCartDao shoppingCartDao();

    public static synchronized ShoppingCartDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ShoppingCartDatabase.class, "shopping_cart")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
