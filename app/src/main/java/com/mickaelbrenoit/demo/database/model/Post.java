package com.mickaelbrenoit.demo.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import static com.mickaelbrenoit.demo.database.Names.FIELD_BODY_POST;
import static com.mickaelbrenoit.demo.database.Names.FIELD_TITLE_POST;
import static com.mickaelbrenoit.demo.database.Names.FOREIGN_KEY_USERID_POST;
import static com.mickaelbrenoit.demo.database.Names.PRIMARY_KEY_POST;
import static com.mickaelbrenoit.demo.database.Names.PRIMARY_KEY_USER;
import static com.mickaelbrenoit.demo.database.Names.TABLE_NAME_POST;

@Entity(tableName = TABLE_NAME_POST,
        foreignKeys = @ForeignKey(  entity = User.class,
                                    parentColumns = PRIMARY_KEY_USER,
                                    childColumns = FOREIGN_KEY_USERID_POST))
public class Post implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = PRIMARY_KEY_POST)
    private int id;

    @NonNull
    @ColumnInfo(name = FIELD_TITLE_POST)
    private String title;

    @NonNull
    @ColumnInfo(name = FIELD_BODY_POST)
    private String body;

    @NonNull
    @ColumnInfo(name = FOREIGN_KEY_USERID_POST)
    private int userId;

    public Post() {
    }

    @Ignore
    public Post(@NonNull int id, @NonNull String title, @NonNull String body, @NonNull int userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getBody() {
        return body;
    }

    public void setBody(@NonNull String body) {
        this.body = body;
    }

    @NonNull
    public int getUserId() {
        return userId;
    }

    public void setUserId(@NonNull int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "--> " +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeInt(userId);
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public Post(Parcel in) {
        id = in.readInt();
        title = in.readString();
        body = in.readString();
        userId = in.readInt();
    }
}
