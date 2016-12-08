package noman.googleplaces;

import android.util.Log;

import java.util.List;
import java.util.Locale;

/**
 * Created by Noman on 8/25/2016.
 */
public class NRPlaces extends AbstractPlaces {

    /**
     * Required API key,
     * https://developers.google.com/places/web-service/get-api-key
     */
    private String key;
    /**
     * REQUIRED
     * location — The latitude/longitude around which to retrieve place information.
     * This must be specified as latitude,longitude.
     */
    private String location;
    /**
     * radius — Defines the distance (in meters) within which to return place results.
     * The maximum allowed radius is 50 000 meters.
     * Note that radius must not be included if rankby=distance
     * (described under Optional parameters below) is specified.
     */
    private int radius = -1;

    /**
     * prominence & distance
     * rankby=distance (described under Optional parameters below) is specified,
     * then one or more of keyword, name, or type is required.
     */
    private String rankby;
    /**
     * keyword — A term to be matched against all content that Google has indexed for this place,
     * including but not limited to name, type, and address,
     * as well as customer reviews and other third-party content.
     */
    private String keyword;

    /**
     * minprice and maxprice (optional) —
     * Restricts results to only those places within the specified range.
     * Valid values range between 0 (most affordable) to 4
     */
    private int minprice = -1;
    /**
     * minprice and maxprice (optional) —
     * Restricts results to only those places within the specified range.
     * Valid values range between 0 (most affordable) to 4
     */
    private int maxprice = -1;

    /**
     * name — One or more terms to be matched against the names of places,
     */
    private String[] name;
    /**
     * opennow — Returns only those places that are
     * open for business at the time the query is sent.
     */
    private boolean opennow;
    /**
     * type — Restricts the results to places matching the specified type.
     * Only one type may be specified (if more than one type is provided,
     * all types following the first entry are ignored).
     */
    private String type;
    /**
     * the language code, indicating in which language the results should be returned,
     */
    private String language;
    /**
     * Country code e.g. US
     */
    private String countryCode;
    private PlacesListener listener;

    private NRPlaces(Builder builder) {
        super(builder.listener);
        setKey(builder.key);
        if (builder.lat == -1 || builder.lng == -1) {
            setLocation(null);
        } else {
            setLocation(builder.lat + "," + builder.lng);
        }
        setRadius(builder.radius);
        setRankby(builder.rankby);
        setKeyword(builder.keyword);
        setMinprice(builder.minprice);
        setMaxprice(builder.maxprice);
        setName(builder.name);
        setOpennow(builder.opennow);
        setType(builder.type);
        setListener(builder.listener);
        setLanguage(builder.language);
        setCountryCode(builder.countryCode);
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getRankby() {
        return rankby;
    }

    public void setRankby(String rankby) {
        this.rankby = rankby;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getMinprice() {
        return minprice;
    }

    public void setMinprice(int minprice) {
        this.minprice = minprice;
    }

    public int getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(int maxprice) {
        this.maxprice = maxprice;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public boolean isOpennow() {
        return opennow;
    }

    public void setOpennow(boolean opennow) {
        this.opennow = opennow;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PlacesListener getListener() {
        return listener;
    }

    public void setListener(PlacesListener listener) {
        this.listener = listener;
    }

    @Override
    protected String constructURL(String nexPageToken) {
        StringBuilder builder = new StringBuilder(PLACES_URL);

        if (key == null) {
            throw new IllegalArgumentException("API key is required");
        }
        builder.append(PARAM_KEY).append(key);
        if (nexPageToken != null) {
            builder.append("&").append(PARAM_PAGETOKEN).append(nexPageToken);
            Log.e("Places", builder.toString());
            return builder.toString();

        }

        if (location == null) {
            throw new IllegalArgumentException("Location lat/lng is required");
        }
        builder.append("&").append(PARAM_LOCATION).append(location);

        if (radius != -1 && rankby != null) {
            throw new IllegalArgumentException("'radius' and 'rankby' cannot work together");
        } else if (radius == -1 && rankby == null) {
            throw new IllegalArgumentException("'radius' is required");
        } else if (radius != -1) {
            if (radius > 50000) {
                throw new IllegalArgumentException("'radius' cannot be > 50000");
            }
            builder.append("&").append(PARAM_RADIUS).append(String.valueOf(radius));
        } else {
            if (rankby.equals("distance") || rankby.equals("prominence")) {
                builder.append("&").append(PARAM_RANK_BY).append(rankby);
            } else {
                throw new IllegalArgumentException(
                        "'rankby' can only be 'distance' or 'prominence' ");
            }
        }

        if (keyword != null) {
            builder.append("&").append(PARAM_KEYWORD).append(keyword);
        }

        if (language != null) {
            Locale locale = new Locale(language);
            if (locale.getISO3Language() == null || locale.getISO3Country().isEmpty()) {
                if (countryCode != null) {
                    locale = new Locale(language, countryCode);
                } else {
                    locale = new Locale(language, language);
                }
                if (locale.getISO3Language() == null || locale.getISO3Country().isEmpty()) {
                    throw new IllegalArgumentException(
                            "Invalid language code");
                }
            }
            builder.append("&").append(PARAM_LANGUAGE).append(language);
        }
        if (minprice != -1 & maxprice != -1) {
            if (minprice > maxprice) {
                throw new IllegalArgumentException("'minprice' should be less than 'maxprice'");
            } else if (minprice > 4 || maxprice > 4) {
                throw new IllegalArgumentException(
                        "'minprice' and 'maxprice' should be less equal to 4");
            } else if (minprice < 0 || maxprice < 0) {
                throw new IllegalArgumentException(
                        "'minprice' and 'maxprice' should be greater equal to 0");
            } else {
                builder.append("&").append(PARAM_MINPRICE).append(String.valueOf(minprice));
                builder.append("&").append(PARAM_MAXPRICE).append(String.valueOf(maxprice));
            }
        }
        if (name != null) {
            String names = "";
            for (int i = 0; i < name.length; i++) {
                if (!names.isEmpty()) {
                    names += "|";
                }
                names += name[i];
            }
            if (!names.isEmpty()) {
                builder.append("&").append(PARAM_NAME).append(names);
            }

        }

        if (type != null) {
            Class<?> someClass = PlaceType.class;
            try {
                someClass.getField(type.toUpperCase());
                builder.append("&").append(PARAM_TYPE).append(type);

            } catch (Exception e) {

                throw new IllegalArgumentException(
                        "'" + type + "' is invalid. All types are given in PlaceType class");
            }
        }

        Log.e("Places", builder.toString());
        return builder.toString();
    }

    public static final class Builder {
        private String key;
        private double lat = -1;
        private double lng = -1;
        private int radius = -1;
        private String rankby;
        private String keyword;
        private int minprice = -1;
        private int maxprice = -1;
        private String[] name;
        private boolean opennow;
        private String type;
        private PlacesListener listener;
        private String language;
        private String countryCode;

        public Builder() {
        }


        public Builder key(String val) {
            key = val;
            return this;
        }

        public Builder latlng(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
            return this;
        }

        public Builder radius(int val) {
            radius = val;
            return this;
        }

        public Builder rankby(String val) {
            rankby = val;
            return this;
        }

        public Builder keyword(String val) {
            keyword = val;
            return this;
        }

        public Builder minprice(int min, int max) {
            minprice = min;
            maxprice = max;
            return this;
        }


        public Builder name(String... val) {
            name = val;
            return this;
        }

        public Builder name(List<String> val) {
            name = new String[val.size()];
            name = val.toArray(name);
            return this;
        }

        public Builder opennow(boolean val) {
            opennow = val;
            return this;
        }

        public Builder type(String val) {
            type = val;
            return this;
        }

        public Builder listener(PlacesListener val) {
            listener = val;
            return this;
        }

        public Builder language(String val) {
            language = val;
            return this;
        }

        /**
         * For example Locale is English, US (en_US)
         *
         * @param language    will be en
         * @param countryCode will be US
         */
        public Builder language(String language, String countryCode) {
            this.language = language;
            this.countryCode = countryCode;
            return this;
        }

        public NRPlaces build() {
            return new NRPlaces(this);
        }
    }
}
