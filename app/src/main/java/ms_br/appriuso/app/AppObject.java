package ms_br.appriuso.app;

/**
 * Created by simone on 20/04/17.
 */

public class AppObject {

    private String category;
    private String title;
    private String description;
    private String latitude;
    private String longitude;
    private String name;
    private String email;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String image8;
    private String uid;
    private String created_at;


    public AppObject() {
        category = title = description = latitude = longitude = name = email = image1 =
        image2 = image3 = image4 = image5 = image6 = image7 = image8 = uid = created_at = "";
    }

    // Set item method
    public void setCategory(String category) {this.category = category;}
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setLatitude(String latitude) {this.latitude = latitude;}
    public void setLongitude(String longitude) {this.longitude = longitude;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setImage1(String image1) {this.image1 = image1;}
    public void setImage2(String image2) {this.image2 = image2;}
    public void setImage3(String image3) {this.image3 = image3;}
    public void setImage4(String image4) {this.image4 = image4;}
    public void setImage5(String image5) {this.image5 = image5;}
    public void setImage6(String image6) {this.image6 = image6;}
    public void setImage7(String image7) {this.image7 = image7;}
    public void setImage8(String image8) {this.image8 = image8;}
    public void setUID(String uid) {this.uid = uid;}
    public void setCreated_at(String created_at) {this.created_at = created_at;}

    //Get item method
    public String getCategory() {return this.category;}
    public String getTitle() {return this.title;}
    public String getDescription() {return this.description;}
    public String getLatitude() {return this.latitude;}
    public String getLongitude() {return this.longitude;}
    public String getName() {return this.name;}
    public String getEmail() {return this.email;}
    public String getImage1() {return this.image1;}
    public String getImage2() {return this.image2;}
    public String getImage3() {return this.image3;}
    public String getImage4() {return this.image4;}
    public String getImage5() {return this.image5;}
    public String getImage6() {return this.image6;}
    public String getImage7() {return this.image7;}
    public String getImage8() {return this.image8;}
    public String getUID() {return this.uid;}
    public String getCreated_at() {return this.created_at;}

    // Special method
    public double getLatitudeDouble() {return Double.parseDouble(this.latitude);}
    public double getLongitudeDouble() {return Double.parseDouble(this.longitude);}

}
