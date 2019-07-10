package noman.googleplaces;

import android.location.Location;

import java.util.Arrays;

/**
 * Created by nor on 8/25/2016.
 */
public class Place {
    /**
     * coordinates of place
     */
    private Location location;
    /**
     * icon of place, not all places have icon.
     */
    private String icon;
    /**
     * name of place
     */
    private String name;
    /**
     * place_id, can get place details using this id
     */
    private String placeId;
    /**
     * types of place
     */
    private String[] types;
    /**
     * near area of place
     */
    private String vicinity;
    /**
     * rating of Restaurant
     */
    private Double rating;
    /**
     * Number of people who gave rating
     */
    private int user_ratings_total;

    public Place() {
    }

    private Place(Builder builder) {
        setLocation(builder.location);
        setIcon(builder.icon);
        setName(builder.name);
        setPlaceId(builder.placeId);
        setTypes(builder.types);
        setVicinity(builder.vicinity);
        setRating(builder.rating);
        setUser_ratings_total(builder.user_ratings_total);
    }

    @Override
    public String toString() {
        return "Place{" +
                "location=" + location +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", placeId='" + placeId + '\'' +
                ", types=" + Arrays.toString(types) +
                ", vicinity='" + vicinity + '\'' +
                ", rating=" + rating + '\'' +
                ", user_ratings_total=" + user_ratings_total + '\'' +
                '}';
    }

    /**
     * Get lat,lat - location of the place
     *
     * @return Location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * set location of place
     *
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Get icon of the place
     * Note : Not all places have icons.
     *
     * @return url of icon e.g. https://maps.gstatic.com/mapfiles/place_api/icons/cafe-71.png
     */
    public String getIcon() {
        return icon;
    }

    /**
     * set icon of location
     *
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * returns name of location
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set name of location
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get placeid of location.
     * placeid is string id and can be used to get details of a place
     *
     * @return
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * set place id
     *
     * @param placeId
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     * returns types of location e.g. types ["cafe","food","point_of_interest","establishment"]
     *
     * @return String array types
     */
    public String[] getTypes() {
        return types;
    }

    /**
     * set location types
     *
     * @param types
     */
    public void setTypes(String[] types) {
        this.types = types;
    }

    /**
     * returns address/surrounding of place
     *
     * @return vicinity of place
     */
    public String getVicinity() {
        return vicinity;
    }

    /**
     * set vicinity
     *
     * @param vicinity
     */
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
    /**
     * returns rating of restaurant
     *
     * @return Double type
     */
    public Double getRating(){return rating;}

    /**
     * set rating
     *
     * @param rating
     */
    public void setRating(Double rating) { this.rating = rating;}

    /**
     * returns Number of people who gave rating
     *
     * @return int type
     */
    public int getUser_ratings_total(){ return user_ratings_total;}
    /**
     * set rating
     *
     * @param user_ratings_total
     */
    public void setUser_ratings_total(int user_ratings_total) { this.user_ratings_total = user_ratings_total;}


    public double getLatitude(){
        return location.getLatitude();
    }

    public double getLongitude(){
        return location.getLongitude();
    }
    public static final class Builder {
        private Location location;
        private String icon;
        private String name;
        private String placeId;
        private String[] types;
        private String vicinity;
        private Double rating;
        private int user_ratings_total;

        public Builder() {
        }

        public Builder location(Location val) {
            location = val;
            return this;
        }

        public Builder icon(String val) {
            icon = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder placeId(String val) {
            placeId = val;
            return this;
        }

        public Builder types(String[] val) {
            types = val;
            return this;
        }

        public Builder vicinity(String val) {
            vicinity = val;
            return this;
        }
        
        public Builder rating(Double val)
        {
            rating = val;
            return this;
        }

        public Builder user_ratings_total(int val)
        {
            user_ratings_total = val;
            return this;
        }

        public Place build() {
            return new Place(this);
        }
    }
}
