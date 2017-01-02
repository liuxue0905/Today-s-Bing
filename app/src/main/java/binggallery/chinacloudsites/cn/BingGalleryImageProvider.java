package binggallery.chinacloudsites.cn;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import binggallery.chinacloudsites.cn.DaoSession;
import binggallery.chinacloudsites.cn.BingGalleryImageDao;

/* Copy this code snippet into your AndroidManifest.xml inside the
<application> element:

    <provider
            android:name="binggallery.chinacloudsites.cn.BingGalleryImageProvider"
            android:authorities="binggallery.chinacloudsites.cn.BingGalleryImage"/>
    */

public class BingGalleryImageProvider extends ContentProvider {

    private static final String TAG = "DB";

    public static final String AUTHORITY = "binggallery.chinacloudsites.cn.BingGalleryImage";
    public static final String BASE_PATH = "BingGalleryImage";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + BASE_PATH;

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + BASE_PATH;
    private static final String TABLENAME = BingGalleryImageDao.TABLENAME;

    private static final String PK = BingGalleryImageDao.Properties.Id
            .columnName;
    private static final int BINGGALLERYIMAGE_DIR = 0;

    private static final int BINGGALLERYIMAGE_ID = 1;
    private static final UriMatcher sURIMatcher;

    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, BINGGALLERYIMAGE_DIR);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", BINGGALLERYIMAGE_ID);
    }

    /**
     * This must be set from outside, it's recommended to do this inside your Application object.
     * Subject to change (static isn't nice).
     */
    public static DaoSession daoSession;

    @Override
    public boolean onCreate() {
        // if(daoSession == null) {
        // throw new IllegalStateException("DaoSession must be set before content provider is created");
        // }
        Log.d(TAG, "Content Provider started: " + CONTENT_URI);
        return true;
    }

    protected SQLiteDatabase getDatabase() {
        if (daoSession == null) {
            throw new IllegalStateException("DaoSession must be set during content provider is active");
        }
        return (SQLiteDatabase) daoSession.getDatabase().getRawDatabase();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        long id = 0;
        String path = "";
        switch (uriType) {
            case BINGGALLERYIMAGE_DIR:
                id = getDatabase().insert(TABLENAME, null, values);
                path = BASE_PATH + "/" + id;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(path);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = getDatabase();
        int rowsDeleted = 0;
        String id;
        switch (uriType) {
            case BINGGALLERYIMAGE_DIR:
                rowsDeleted = db.delete(TABLENAME, selection, selectionArgs);
                break;
            case BINGGALLERYIMAGE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(TABLENAME, PK + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(TABLENAME, PK + "=" + id + " and "
                            + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = getDatabase();
        int rowsUpdated = 0;
        String id;
        switch (uriType) {
            case BINGGALLERYIMAGE_DIR:
                rowsUpdated = db.update(TABLENAME, values, selection, selectionArgs);
                break;
            case BINGGALLERYIMAGE_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(TABLENAME, values, PK + "=" + id, null);
                } else {
                    rowsUpdated = db.update(TABLENAME, values, PK + "=" + id
                            + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case BINGGALLERYIMAGE_DIR:
                queryBuilder.setTables(TABLENAME);
                break;
            case BINGGALLERYIMAGE_ID:
                queryBuilder.setTables(TABLENAME);
                queryBuilder.appendWhere(PK + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = getDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public final String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case BINGGALLERYIMAGE_DIR:
                return CONTENT_TYPE;
            case BINGGALLERYIMAGE_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
