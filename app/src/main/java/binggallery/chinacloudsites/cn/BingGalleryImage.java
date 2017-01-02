package binggallery.chinacloudsites.cn;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class BingGalleryImage {

    @Id
    private Long id;

    @NotNull
    @Index(unique = true)
    private String uid;

    private String minpix;

    private String maxpix;

    private String copyright;

    private String desc;

    @Generated(hash = 1229582877)
    public BingGalleryImage(Long id, @NotNull String uid, String minpix, String maxpix,
            String copyright, String desc) {
        this.id = id;
        this.uid = uid;
        this.minpix = minpix;
        this.maxpix = maxpix;
        this.copyright = copyright;
        this.desc = desc;
    }
    @Generated(hash = 773906797)
    public BingGalleryImage() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUid() {
        return this.uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getMinpix() {
        return this.minpix;
    }
    public void setMinpix(String minpix) {
        this.minpix = minpix;
    }
    public String getMaxpix() {
        return this.maxpix;
    }
    public void setMaxpix(String maxpix) {
        this.maxpix = maxpix;
    }
    public String getCopyright() {
        return this.copyright;
    }
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

//    private static void addBingGalleryImage(Schema schema) {
//        Entity customer = schema.addEntity("BingGalleryImage");
//        customer.addIdProperty().autoincrement();
//        Property uidProperty =  customer.addStringProperty("uid").notNull().getProperty();
//        customer.addStringProperty("minpix");
//        customer.addStringProperty("maxpix");
//        customer.addStringProperty("copyright");
//        customer.addStringProperty("desc");
//
//        Index index = new Index();
//        index.addPropertyAsc(uidProperty);
//        index.makeUnique();
//        customer.addIndex(index);
//
//        ContentProvider cp = customer.addContentProvider();
//        cp.setJavaPackage("binggallery.chinacloudsites.cn");
//        cp.setClassName("BingGalleryImageProvider");
//        cp.setAuthority("binggallery.chinacloudsites.cn.BingGalleryImage");
//        cp.setBasePath("BingGalleryImage");
//    }
}