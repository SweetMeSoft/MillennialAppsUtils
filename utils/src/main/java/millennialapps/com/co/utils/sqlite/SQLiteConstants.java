package millennialapps.com.co.utils.sqlite;

import android.provider.BaseColumns;

/**
 * Created by erick on 22/7/2017.
 */

public interface SQLiteConstants extends BaseColumns {

    String T_MALLS = "Malls";
    String T_NODES = "Nodes";
    String T_FLOORS = "Floors";
    String T_NODES_NODES = "NodesHasNodes";

    String F_NAME = "Name";
    String F_COUNTRY = "Country";
    String F_STATE = "State";
    String F_CITY = "City";
    String F_ADDRESS = "Address";
    String F_EMAIL = "Email";
    String F_DESCRIPTION = "Description";
    String F_PHONE = "Phone";
    String F_LATITUDE = "Latitude";
    String F_LONGITUDE = "Longitude";
    String F_SLOGAN = "Slogan";
    String F_URL_MAP = "UrlMap";
    String F_WEB = "Web";
    String F_SEARCH_DIRECTION = "SearchDirection";
    String F_NUMBER = "Number";
    String F_TYPE = "Type";
    String F_CATEGORY = "Category";
    String F_ID_NODE_FROM = "IdNodeFrom";
    String F_ID_NODE_TO = "IdNodeTo";
    String F_ID_MALL = "IdMall";
    String F_ID_FLOOR = "IdFloor";

    String CREATE_TABLE_MALLS = "CREATE TABLE " + T_MALLS
            + " (" + _ID + " TEXT NOT NULL PRIMARY KEY, "
            + F_NAME + " TEXT NOT NULL, "
            + F_ADDRESS + " TEXT NOT NULL, "
            + F_DESCRIPTION + " TEXT NOT NULL, "
            + F_EMAIL + " TEXT NULL, "
            + F_PHONE + " TEXT NULL, "
            + F_LATITUDE + " REAL NOT NULL, "
            + F_LONGITUDE + " REAL NOT NULL, "
            + F_SLOGAN + " TEXT NOT NULL, "
            + F_URL_MAP + " TEXT NOT NULL, "
            + F_WEB + " TEXT NULL);";

    String CREATE_TABLE_NODES = "CREATE TABLE " + T_NODES
            + " (" + _ID + " TEXT NOT NULL PRIMARY KEY, "
            + F_DESCRIPTION + " TEXT NULL, "
            + F_LATITUDE + " REAL NOT NULL, "
            + F_LONGITUDE + " REAL NOT NULL, "
            + F_NAME + " TEXT NULL, "
            + F_NUMBER + " INT NULL, "
            + F_TYPE + " TEXT NOT NULL, "
            + F_CATEGORY + " TEXT NULL, "
            + F_EMAIL + " TEXT NULL, "
            + F_PHONE + " TEXT NULL, "
            + F_SEARCH_DIRECTION + " INT NULL, "
            + F_ID_MALL + " TEXT NOT NULL, "
            + F_ID_FLOOR + " TEXT NOT NULL);";

    String CREATE_TABLE_FLOORS = "CREATE TABLE " + T_FLOORS
            + " (" + _ID + " TEXT NOT NULL PRIMARY KEY, "
            + F_ID_MALL + " TEXT NOT NULL, "
            + F_DESCRIPTION + " TEXT NOT NULL, "
            + F_NAME + " TEXT NOT NULL, "
            + F_NUMBER + " TEXT NOT NULL, "
            + F_URL_MAP + " TEXT NOT NULL);";

    String CREATE_TABLE_NODES_NODES = "CREATE TABLE " + T_NODES_NODES
            + " (" + F_ID_NODE_FROM + " TEXT NOT NULL, "
            + F_ID_NODE_TO + " TEXT NOT NULL," +
            "PRIMARY KEY(" + F_ID_NODE_FROM + ", " + F_ID_NODE_TO + "));";

    String DROP_TABLE_MALLS = "DROP TABLE " + T_MALLS + ";";
    String DROP_TABLE_NODES = "DROP TABLE " + T_NODES + ";";
    String DROP_TABLE_FLOORS = "DROP TABLE " + T_FLOORS + ";";
    String DROP_TABLE_NODES_NODES = "DROP TABLE " + T_NODES_NODES + ";";

    String[] C_MALL = {
            _ID,
            F_NAME,
            F_ADDRESS,
            F_DESCRIPTION,
            F_EMAIL,
            F_PHONE,
            F_LATITUDE,
            F_LONGITUDE,
            F_SLOGAN,
            F_URL_MAP,
            F_WEB
    };

    String[] C_NODE = {
            _ID,
            F_DESCRIPTION,
            F_LATITUDE,
            F_LONGITUDE,
            F_NAME,
            F_NUMBER,
            F_TYPE,
            F_CATEGORY,
            F_EMAIL,
            F_PHONE,
            F_SEARCH_DIRECTION,
            F_ID_MALL,
            F_ID_FLOOR
    };

    String[] C_FLOOR = {
            _ID,
            F_DESCRIPTION,
            F_NAME,
            F_NUMBER,
            F_URL_MAP,
            F_ID_MALL
    };

    String[] C_NODES_NODES = {
            F_ID_NODE_FROM,
            F_ID_NODE_TO
    };
    String DB_USERS = "Users";
}
