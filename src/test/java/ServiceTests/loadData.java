package ServiceTests;

public class loadData {
    String load = "{\n" +
            "  \"users\":[\n" +
            "    {\n" +
            "      \"username\":\"aaronstarky\",\n" +
            "      \"password\":\"yikers\",\n" +
            "      \"email\":\"aaronstarky@outlook.com\",\n" +
            "      \"firstName\":\"aaron\",\n" +
            "      \"lastName\":\"starkweather\",\n" +
            "      \"gender\":\"m\",\n" +
            "      \"personID\":\"p2\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"persons\":[\n" +
            "    {\n" +
            "      \"personID\":\"p8fkYiotyop86970\",\n" +
            "      \"associatedUsername\":\"aaronstarky\",\n" +
            "      \"firstName\":\"Laurel\",\n" +
            "      \"lastName\":\"Starkweather\",\n" +
            "      \"gender\":\"f\",\n" +
            "      \"fatherID\":\"null\",\n" +
            "      \"motherID\":\"null\",\n" +
            "      \"spouseID\":\"null\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"events\":[\n" +
            "    {\n" +
            "      \"eventID\":\"aaronBirthID\",\n" +
            "      \"associatedUsername\":\"aaronstarky\",\n" +
            "      \"personID\":\"p68ovjgh2875\",\n" +
            "      \"latitude\":\"47.803481F\",\n" +
            "      \"longitude\":\"-122.334011F\",\n" +
            "      \"country\":\"USA\",\n" +
            "      \"city\":\"Edmonds\",\n" +
            "      \"eventType\":\"Birth\",\n" +
            "      \"year\":2000\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public String getLoad() {
        return load;
    }
}
